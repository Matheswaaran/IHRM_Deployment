package com.example.allu.hrm_contractor.UI.Activities.SubActivities.Request;

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
import com.example.allu.hrm_contractor.Adapter.Recy_Adapter_SupVis_Req;
import com.example.allu.hrm_contractor.Pojo.SupVis_Requirements;
import com.example.allu.hrm_contractor.Pojo.Worker;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.HomeActivity;
import com.example.allu.hrm_contractor.utils.DataAttributes;
import com.example.allu.hrm_contractor.utils.RespAttr;
import com.example.allu.hrm_contractor.utils.URL;
import com.example.allu.hrm_contractor.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAllRequests extends AppCompatActivity {
    String TAG = ViewAllRequests.class.getSimpleName();
    Utils utils;
    RequestQueue queue;

    RecyclerView ListRequests;
    Recy_Adapter_SupVis_Req adapterSupVisReq;
    ArrayList<SupVis_Requirements> requirementsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_requests);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Requests");

        utils = new Utils(this);
        queue = Volley.newRequestQueue(this);


        ListRequests = (RecyclerView)findViewById(R.id.recy_request_list);
        ListRequests.setHasFixedSize(false);
        ListRequests.setItemAnimator(new DefaultItemAnimator());
        ListRequests.setLayoutManager(new GridLayoutManager(this,1));
        requirementsArrayList = new ArrayList<>();
        adapterSupVisReq = new Recy_Adapter_SupVis_Req(this,requirementsArrayList);

        getRequests();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra(DataAttributes.INTENT_FragmentId,R.id.nav_viewrequest);
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

    void getRequests(){
        utils.ShowDialog();
        JSONObject params = new JSONObject();
        try {
            params.put(DataAttributes.REQUEST_OPTION, "get_request");
            params.put(RespAttr.COL_emp_cid,utils.GetUserDetails().CID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG,params.toString()+" ");
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
                            int rid = object.getInt(RespAttr.COL_req_rid);
                            String name = object.getString(RespAttr.COL_req_name);
                            int suid = object.getInt(RespAttr.COL_req_su_id);
                            int sid = object.getInt(RespAttr.COL_req_sid);
                            String date = object.getString(RespAttr.COL_req_date);
                            int Skill1 = object.getInt(RespAttr.COL_req_skill1);
                            int Skill2 = object.getInt(RespAttr.COL_req_skill2);
                            int Skill3 = object.getInt(RespAttr.COL_req_skill3);
                            int Skill4 = object.getInt(RespAttr.COL_req_skill4);
                            int alloc_skill1 = object.getInt(RespAttr.COL_req_alloc_skill1);
                            int alloc_skill2 = object.getInt(RespAttr.COL_req_alloc_skill2);
                            int alloc_skill3 = object.getInt(RespAttr.COL_req_alloc_skill3);
                            int alloc_skill4 = object.getInt(RespAttr.COL_req_alloc_skill4);
                            String req_date = object.getString(RespAttr.COL_req_date);
                            String alloctime = object.getString(RespAttr.COL_req_alloc_time);
                            int response = object.getInt(RespAttr.COL_req_c_response);
                            String created = object.getString(RespAttr.COL_req_created);
                            Log.e(TAG,response+"");
                            SupVis_Requirements requirements = new SupVis_Requirements(rid,suid,sid,Skill1,Skill2,Skill3,Skill4,alloc_skill1,alloc_skill2,alloc_skill3,alloc_skill4,response,name,date,alloctime,req_date,created);
                            adapterSupVisReq.addRequests(requirements);
                        }
                        utils.CloseDialog();
                        ListRequests.setAdapter(adapterSupVisReq);
                    }else{
                        utils.CloseDialog();
                        utils.Toast(jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                    }
                } catch (JSONException e) {
                    utils.CloseDialog();
                    e.printStackTrace();
                    utils.Toast("Json parsing error "+e.toString());
                    Log.e(TAG,"Json parsing error "+e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utils.CloseDialog();
                utils.Toast("volley error "+error.toString());
                Log.e(TAG,"volley error "+error.toString());
            }
        });
        queue.add(request);
    }
}
