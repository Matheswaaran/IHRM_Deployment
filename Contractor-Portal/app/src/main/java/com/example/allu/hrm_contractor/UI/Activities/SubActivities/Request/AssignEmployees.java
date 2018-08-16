package com.example.allu.hrm_contractor.UI.Activities.SubActivities.Request;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.hrm_contractor.Adapter.Recy_Adapter_Worker_Assignment;
import com.example.allu.hrm_contractor.Pojo.SupVis_Requirements;
import com.example.allu.hrm_contractor.Pojo.User;
import com.example.allu.hrm_contractor.Pojo.Worker;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.HomeActivity;
import com.example.allu.hrm_contractor.interfaces.WorkerAssign_Interface;
import com.example.allu.hrm_contractor.utils.DataAttributes;
import com.example.allu.hrm_contractor.utils.RespAttr;
import com.example.allu.hrm_contractor.utils.URL;
import com.example.allu.hrm_contractor.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AssignEmployees extends AppCompatActivity implements View.OnClickListener{
    String TAG = AssignEmployees.class.getSimpleName();
    RequestQueue queue;
    Utils utils;

    Intent i;
    TextView Txt_taskname,Txt_suid,Txt_reqdate,Txt_skill1,Txt_skill2,Txt_skill3,Txt_skill4;
    TextView Txt_alloc_s1,Txt_alloc_s2,Txt_alloc_s3,Txt_alloc_s4;
    RecyclerView Recy_Req_list;
    SupVis_Requirements requirements;

    Recy_Adapter_Worker_Assignment Listadapter;
    ArrayList<Worker> WorkerList;
    WorkerAssign_Interface worker_interface;

    ArrayList<Worker> AllocList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_employees);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        utils = new Utils(this);
        queue = Volley.newRequestQueue(this);
        WorkerList = new ArrayList();
        AllocList = new ArrayList<>();

        i = getIntent();
        if(!i.hasExtra(DataAttributes.INTENT_Su_req)){
            onBackPressed();
        }
        requirements = i.getParcelableExtra(DataAttributes.INTENT_Su_req);
        LoadUI();
        GetWorkers();

        worker_interface = new WorkerAssign_Interface() {
            @Override
            public boolean OnAssign(int pos) {
                Log.e(TAG,"assinged");
                return Assign(pos);
            }

            @Override
            public void OnDeAssign(int pos) {
                Log.e(TAG," de assinged");
                deAssign(pos);
            }
        };

        Listadapter = new Recy_Adapter_Worker_Assignment(WorkerList,this,worker_interface);
        GetWorkers();
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

    boolean Assign(int pos){
        Worker worker = WorkerList.get(pos);
        switch (worker.Skill){
            case 1:
                if(requirements.Skill_1 > requirements.Alloc_skill_1){
                    AllocList.add(worker);
                    requirements.Alloc_skill_1++;
                }else{
                    utils.Toast("Skilled worker filled");
                    return false;
                }
                break;
            case 2:
                if(requirements.Skill_2 > requirements.Alloc_skill_2){
                    AllocList.add(worker);
                    requirements.Alloc_skill_2++;
                }else{
                    utils.Toast("Semi Skilled worker filled");
                    return false;
                }
                break;
            case 3:
                if(requirements.Skill_3 > requirements.Alloc_skill_3){
                    AllocList.add(worker);
                    requirements.Alloc_skill_3++;
                }else{
                    utils.Toast("Un Skilled worker filled");
                    return false;
                }
                break;
            case 4:
                if(requirements.Skill_4 > requirements.Alloc_skill_4){
                    AllocList.add(worker);
                    requirements.Alloc_skill_4++;
                }else{
                    utils.Toast("helper filled");
                    return false;
                }
                break;
            default:
                Log.e(TAG,"error");
                break;
        }
        updateAlloc();

        return true;
    }

    void deAssign(int pos){
        Worker worker = WorkerList.get(pos);
        switch (worker.Skill){
            case 1:
                requirements.Alloc_skill_1--;
                AllocList.remove(worker);
                break;
            case 2:
                requirements.Alloc_skill_2--;
                AllocList.remove(worker);
                break;
            case 3:
                requirements.Alloc_skill_3--;
                AllocList.remove(worker);
                break;
            case 4:
                requirements.Alloc_skill_4--;
                AllocList.remove(worker);
                break;
            default:
                Log.e(TAG,"error");
                break;
        }
        updateAlloc();
    }


    void updateAlloc(){
        Txt_alloc_s1.setText(requirements.Alloc_skill_1+"");
        Txt_alloc_s2.setText(requirements.Alloc_skill_2+"");
        Txt_alloc_s3.setText(requirements.Alloc_skill_3+"");
        Txt_alloc_s4.setText(requirements.Alloc_skill_4+"");
    }

    void LoadUI(){
        Txt_taskname = (TextView)findViewById(R.id.txt_taskname);
        Txt_suid = (TextView)findViewById(R.id.txt_su_id);
        Txt_reqdate = (TextView)findViewById(R.id.txt_req_date);
        Txt_skill1 = (TextView)findViewById(R.id.txt_no_skill1);
        Txt_skill2 = (TextView)findViewById(R.id.txt_no_skill2);
        Txt_skill3 = (TextView)findViewById(R.id.txt_no_skill3);
        Txt_skill4 = (TextView)findViewById(R.id.txt_no_skill4);

        Txt_alloc_s1 = (TextView)findViewById(R.id.txt_alloc_skill1);
        Txt_alloc_s2 = (TextView)findViewById(R.id.txt_alloc_skill2);
        Txt_alloc_s3 = (TextView)findViewById(R.id.txt_alloc_skill3);
        Txt_alloc_s4 = (TextView)findViewById(R.id.txt_alloc_skill4);

        Recy_Req_list = (RecyclerView)findViewById(R.id.recy_employeelist);
        Recy_Req_list.setLayoutManager(new GridLayoutManager(this,1));
        Recy_Req_list.setItemAnimator(new DefaultItemAnimator());
        Recy_Req_list.setHasFixedSize(false);

        Txt_taskname.setText(requirements.Taskname);
        Txt_suid.setText(requirements.SU_ID+"");
        Txt_reqdate.setText(requirements.Req_date);

        Txt_skill1.setText(requirements.Skill_1+"");
        Txt_skill2.setText(requirements.Skill_2+"");
        Txt_skill3.setText(requirements.Skill_3+"");
        Txt_skill4.setText(requirements.Skill_4+"");

        Txt_alloc_s1.setText(0+"");
        Txt_alloc_s2.setText(0+"");
        Txt_alloc_s3.setText(0+"");
        Txt_alloc_s4.setText(0+"");
    }


    void GetWorkers(){
        utils.ShowDialog();
        JSONObject param = new JSONObject();
        try {
            param.put(DataAttributes.REQUEST_OPTION,"get_auth_emp");
            param.put(RespAttr.COL_emp_cid,utils.GetUserDetails().CID);
            Log.e(TAG,param.toString()+" "+ URL.URL_Contractors);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Contractors, param, new Response.Listener<JSONObject>() {
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
                            Worker worker = new Worker(eid,cid,emptype,auth,skill,name,aadhar_uid,aadhar_string,created);
                            Listadapter.addWorker(worker);
                            WorkerList.add(worker);
                            Log.e(TAG,"worker added");
                            utils.CloseDialog();
                        }
                        Recy_Req_list.setAdapter(Listadapter);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_post:
                postData();
                break;
            default:
                break;
        }
    }


    void postData(){
        utils.ShowDialog();
        JSONObject params = new JSONObject();
        try {
            params.put(DataAttributes.REQUEST_OPTION, "update_response");
            params.put(RespAttr.COL_emp_cid,utils.GetUserDetails().CID);
            params.put(RespAttr.COL_req_rid,requirements.RID);
            params.put(RespAttr.COL_req_alloc_skill1,requirements.Alloc_skill_1);
            params.put(RespAttr.COL_req_alloc_skill2,requirements.Alloc_skill_2);
            params.put(RespAttr.COL_req_alloc_skill3,requirements.Alloc_skill_3);
            params.put(RespAttr.COL_req_alloc_skill4,requirements.Alloc_skill_4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG , params.toString()+" "+URL.URL_Contractors);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Contractors, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                        utils.Toast(jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                        Log.e(TAG,jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                        for(int i = 0;i<AllocList.size();i++){
                            UpdateWorkers(AllocList.get(i));
                        }
                        utils.CloseDialog();
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

    void UpdateWorkers(Worker worker){
        utils.ShowDialog();
        JSONObject params = new JSONObject();
        try {
            params.put(DataAttributes.REQUEST_OPTION, "allocate_employee");
            params.put(RespAttr.COL_emp_cid,utils.GetUserDetails().CID);
            params.put(RespAttr.COL_req_rid,requirements.RID);
            params.put(RespAttr.COL_emp_eid,worker.EID);
            params.put(RespAttr.COL_req_date,requirements.Date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG , params.toString()+" "+URL.URL_Contractors);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Contractors, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                        utils.Toast(jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                        Log.e(TAG,jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                        utils.CloseDialog();
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
