package com.example.allu.hrm_superviser.UI.Activities.SubActivities.Requests;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.hrm_superviser.Adapter.Recy_Adapter_worker_atten;
import com.example.allu.hrm_superviser.Pojo.SupVis_Requirements;
import com.example.allu.hrm_superviser.Pojo.Worker;
import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.UI.Activities.HomeActivity;
import com.example.allu.hrm_superviser.interfaces.AttendanceInterface;
import com.example.allu.hrm_superviser.utils.DataAttributes;
import com.example.allu.hrm_superviser.utils.RespAttr;
import com.example.allu.hrm_superviser.utils.URL;
import com.example.allu.hrm_superviser.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {
    String TAG = AttendanceActivity.class.getSimpleName();
    Utils utils;
    RequestQueue queue;

    RecyclerView workerList;
    SupVis_Requirements requirements;

    ArrayList<Worker> workers;

    Recy_Adapter_worker_atten ListAdapter;

    AttendanceInterface interface_atten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        utils = new Utils(this);
        queue = Volley.newRequestQueue(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        requirements = getIntent().getParcelableExtra(DataAttributes.INTENT_Su_req);

        interface_atten = new AttendanceInterface() {
            @Override
            public void putAttendance(Worker worker, boolean option) {
                postattendance(worker.AID,option);
            }
        };

        workerList = (RecyclerView)findViewById(R.id.recy_workerlist);
        workerList.setHasFixedSize(false);
        workerList.setItemAnimator(new DefaultItemAnimator());
        workerList.setLayoutManager(new GridLayoutManager(this,1));
        workers = new ArrayList<>();
        ListAdapter = new Recy_Adapter_worker_atten(this,workers,interface_atten);
        workerList.setAdapter(ListAdapter);
        GetWorkers();
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra(DataAttributes.INTENT_FragmentId,R.id.nav_requests);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void postattendance(int aid , boolean atten){
        utils.ShowDialog();
        JSONObject param = new JSONObject();
        try {
            param.put(DataAttributes.REQUEST_OPTION,"update_atten");
            param.put("aid",aid);
            if(atten){
                param.put("atten",1);
            }else{
                param.put("atten",0);
            }
            Log.e(TAG,param.toString()+" "+ URL.URL_Contractors);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Supervisor, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    utils.CloseDialog();
                    Log.e(TAG,response.toString());
                    if(response.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                        utils.Toast(response.getString(DataAttributes.RESPONSE_MESSAGE));
                    }else{
                        utils.Toast(response.getString(DataAttributes.RESPONSE_MESSAGE));

                    }
                } catch (JSONException e) {
                    utils.CloseDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,"Volley parsing error : "+volleyError.toString());
                Log.e(TAG,volleyError.getCause().toString());
                utils.CloseDialog();
            }
        });
        Log.e(TAG,"Request added");
        queue.add(request);
    }
    void GetWorkers(){
        utils.ShowDialog();
        JSONObject param = new JSONObject();
        try {
            param.put(DataAttributes.REQUEST_OPTION,"get_res_workers");
            param.put(RespAttr.COL_req_rid,requirements.RID);
            Log.e(TAG,param.toString()+" "+ URL.URL_Contractors);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Supervisor, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e(TAG,response.toString());
                    if(response.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                        utils.Toast(response.getString(DataAttributes.RESPONSE_MESSAGE));
                        JSONArray WorkerArray = response.getJSONArray(DataAttributes.RESPONSE_DATA);
                        for(int i = 0 ; i < WorkerArray.length(); i ++){
                            JSONObject object = WorkerArray.getJSONObject(i);
                            int eid = object.getInt(RespAttr.COL_emp_eid);
                            int cid = object.getInt(RespAttr.COL_emp_cid);
                            String name = object.getString(RespAttr.COL_emp_name);
                            int auth = object.getInt(RespAttr.COL_emp_auth);
                            long aadhar_uid = object.getLong(RespAttr.COL_emp_aadhar_uid);
                            String aadhar_string = object.getString(RespAttr.COL_emp_aadhar_string);
                            int skill = object.getInt(RespAttr.COL_emp_skill);
                            int emptype = object.getInt(RespAttr.COL_emp_emptype);
                            String created = object.getString(RespAttr.COL_emp_created);
                            int aid = object.getInt("aid");
                            Worker worker = new Worker(eid,cid,emptype,auth,skill,name,aadhar_uid,aadhar_string,created);
                            worker.setAID(aid);
                            ListAdapter.addWorker(worker);
                            Log.e(TAG,"worker added");
                            utils.CloseDialog();
                        }
                        workerList.setAdapter(ListAdapter);
                    }else{
                        utils.Toast(response.getString(DataAttributes.RESPONSE_MESSAGE));
                        utils.CloseDialog();
                    }
                } catch (JSONException e) {
                    utils.CloseDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,"Volley parsing error : "+volleyError.toString());
                Log.e(TAG,volleyError.getCause().toString());
                utils.CloseDialog();
            }
        });
        Log.e(TAG,"Request added");
        queue.add(request);
    }

}
