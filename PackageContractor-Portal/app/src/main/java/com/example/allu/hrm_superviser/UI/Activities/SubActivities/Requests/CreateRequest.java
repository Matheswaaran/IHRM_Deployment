package com.example.allu.hrm_superviser.UI.Activities.SubActivities.Requests;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.UI.Activities.HomeActivity;
import com.example.allu.hrm_superviser.utils.DataAttributes;
import com.example.allu.hrm_superviser.utils.RespAttr;
import com.example.allu.hrm_superviser.utils.URL;
import com.example.allu.hrm_superviser.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Queue;

public class CreateRequest extends AppCompatActivity implements View.OnClickListener {

    String TAG = CreateRequest.class.getSimpleName();
    Utils utils;
    RequestQueue queue;

    Calendar c;

    EditText Edit_taskname,Edit_date,Edit_skill1,Edit_skill2,Edit_skill3,Edit_skill4;
    String Taskname,Date;
    int Skill1,Skill2,Skill3,Skill4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        queue = Volley.newRequestQueue(this);

        utils = new Utils(this);
        LoadView();
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


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra(DataAttributes.INTENT_FragmentId,R.id.nav_requests);
        startActivity(i);
    }

    void LoadView(){
        Edit_taskname = (EditText)findViewById(R.id.edit_taskname);
        Edit_date = (EditText)findViewById(R.id.edit_date);
        Edit_skill1 = (EditText)findViewById(R.id.edit_skill1);
        Edit_skill2 = (EditText)findViewById(R.id.edit_skill2);
        Edit_skill3 = (EditText)findViewById(R.id.edit_skill3);
        Edit_skill4 = (EditText)findViewById(R.id.edit_skill4);
    }

    void CreateRequest(){
        utils.ShowDialog();
        Taskname = Edit_taskname.getText().toString();
        Date = Edit_date.getText().toString();
        Skill1 = Integer.parseInt(Edit_skill1.getText().toString());
        Skill2 = Integer.parseInt(Edit_skill2.getText().toString());
        Skill3 = Integer.parseInt(Edit_skill3.getText().toString());
        Skill4 = Integer.parseInt(Edit_skill4.getText().toString());
        Log.e(TAG,Taskname+" "+Date+" "+Skill1+" "+Skill2+" "+Skill3+" "+Skill4+" "+URL.URL_Supervisor);

        if(utils.isEmptyString(Taskname,Date) && utils.isEmptyint(Skill1,Skill2,Skill3,Skill4)){
            JSONObject param = new JSONObject();
            try {
                param.put(DataAttributes.REQUEST_OPTION,"create_request");
                param.put(RespAttr.COL_su_suid,utils.GetUserDetails().SU_ID);
                param.put(RespAttr.COL_su_sid,utils.GetUserDetails().SID);
                param.put(RespAttr.COL_req_name,Taskname);
                param.put(RespAttr.COL_req_date,Date);
                param.put(RespAttr.COL_req_skill1,Skill1);
                param.put(RespAttr.COL_req_skill2,Skill2);
                param.put(RespAttr.COL_req_skill3,Skill3);
                param.put(RespAttr.COL_req_skill4,Skill4);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e(TAG,param.toString()+" "+URL.URL_Supervisor);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Supervisor, param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    utils.CloseDialog();
                    try {
                        Log.e(TAG,response.toString());
                        if(response.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                            utils.Toast(response.getString(DataAttributes.RESPONSE_MESSAGE));
                            Log.e(TAG,"success : "+response.getString(DataAttributes.RESPONSE_MESSAGE));
                            utils.Goto(HomeActivity.class);
                        }else{
                            utils.Toast(response.getString(DataAttributes.RESPONSE_MESSAGE));
                            Log.e(TAG,"error : "+response.getString(DataAttributes.RESPONSE_MESSAGE));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG,e.getCause().toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    utils.CloseDialog();
                    Log.e(TAG,volleyError.getCause().toString());
                }
            });
            queue.add(request);
        }else{
            utils.Toast("Enter All Fields");
        }
        utils.CloseDialog();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_createrequest:
                CreateRequest();
                break;
            case R.id.edit_date:
                c = Calendar.getInstance();
                final SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                DatePickerDialog dialog = new DatePickerDialog(CreateRequest.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        c.set(Calendar.YEAR, i);
                        c.set(Calendar.MONTH, i1);
                        c.set(Calendar.DAY_OF_MONTH, i2);
                        Date = df.format(c.getTime());
                        Edit_date.setText(df.format(c.getTime()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
        }
    }
}
