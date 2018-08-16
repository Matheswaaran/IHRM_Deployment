package com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker;

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
import com.example.allu.hrm_contractor.Adapter.Recy_Adapter_Worker;
import com.example.allu.hrm_contractor.Pojo.Worker;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.HomeActivity;
import com.example.allu.hrm_contractor.utils.DataAttributes;
import com.example.allu.hrm_contractor.utils.RespAttr;
import com.example.allu.hrm_contractor.utils.URL;
import com.example.allu.hrm_contractor.utils.Utils;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAllWorkersActivity extends AppCompatActivity {

    String TAG = ViewAllWorkersActivity.class.getSimpleName();
    Utils utils;
    RequestQueue queue;

    RecyclerView Recy_allWorkers;
    Recy_Adapter_Worker adapter_worker;
    ArrayList<Worker> workerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_workers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("All workers");
        utils = new Utils(this);
        queue = Volley.newRequestQueue(this);

        Recy_allWorkers = (RecyclerView)findViewById(R.id.recy_allworkers);
        Recy_allWorkers.setHasFixedSize(false);
        Recy_allWorkers.setLayoutManager(new GridLayoutManager(this,1));
        Recy_allWorkers.setItemAnimator(new DefaultItemAnimator());

        workerArrayList = new ArrayList<>();
        adapter_worker = new Recy_Adapter_Worker(this,workerArrayList);

        GetWorkersFromServer();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra(DataAttributes.INTENT_FragmentId,R.id.nav_workers);
        startActivity(i);
        super.onBackPressed();
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

    void GetWorkersFromServer(){
        JSONObject params = new JSONObject();
        try {
            params.put(DataAttributes.REQUEST_OPTION, "get_emp");
            params.put(RespAttr.COL_emp_cid,utils.GetUserDetails().CID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Contractors, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                        utils.Toast(jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                        Log.e(TAG,jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                        JSONArray data = jsonObject.getJSONArray(DataAttributes.RESPONSE_DATA);
                        for(int i = 0 ;i < data.length() ; i++){
                            JSONObject object = data.getJSONObject(i);
                            int eid = object.getInt(RespAttr.COL_emp_eid);
                            int cid = object.getInt(RespAttr.COL_emp_cid);
                            String name = object.getString(RespAttr.COL_emp_name);
                            int auth = object.getInt(RespAttr.COL_emp_auth);
                            long aadhar_uid = object.getLong(RespAttr.COL_emp_aadhar_uid);
                            String aadhar_string = object.getString(RespAttr.COL_emp_aadhar_string);
                            int skill = object.getInt(RespAttr.COL_emp_skill);
                            int emptype = object.getInt(RespAttr.COL_emp_emptype);
                            Log.e(TAG,auth+"");
                            String created = object.getString(RespAttr.COL_emp_created);
                            Worker worker = new Worker(eid,cid,emptype,auth,skill,name,aadhar_uid,aadhar_string,created);
                            adapter_worker.addWorker(worker);
                            Log.e(TAG,"worker added");
                        }
                        Recy_allWorkers.setAdapter(adapter_worker);
                    }else{
                        utils.Toast(jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    utils.Toast("Json parsing error "+e.toString());
                    Log.e(TAG,"Json parsing error "+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utils.Toast("volley error "+error.toString());
                Log.e(TAG,"volley error "+error.toString());
            }
        });
        queue.add(request);
    }
}
