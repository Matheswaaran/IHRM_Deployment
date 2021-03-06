package com.example.allu.hrm_contractor.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.allu.hrm_contractor.Pojo.User;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.utils.DataAttributes;
import com.example.allu.hrm_contractor.utils.RespAttr;
import com.example.allu.hrm_contractor.utils.URL;
import com.example.allu.hrm_contractor.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "LoginActivity";
    RequestQueue queue;
    Utils utils;

    EditText Edit_username,Edit_password;
    String Username,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue = Volley.newRequestQueue(this);
        utils = new Utils(this);
        Edit_username = (EditText)findViewById(R.id.edit_username);
        Edit_password = (EditText)findViewById(R.id.edit_password);

    }

    void Login(){
        Username = Edit_username.getText().toString();
        Password = Edit_password.getText().toString();
        if(!utils.isEmptyString(Username,Password)){
            utils.Toast("Enter all fields!..");
        }else{
            RequestLogin();
        }
    }

    void RequestLogin(){
        utils.ShowDialog();
        JSONObject param = new JSONObject();
        try {
            param.put(DataAttributes.REQUEST_OPTION,"login");
            param.put(RespAttr.COL_con_name,Username);
            param.put(RespAttr.COL_con_password,Password);
            Log.e(TAG,param.toString()+" "+URL.URL_Contractors);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL.URL_Contractors, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                utils.CloseDialog();
                try {
                    Log.e(TAG,response.toString());
                    if(response.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                        utils.Toast(response.getString(DataAttributes.RESPONSE_MESSAGE));
                        Log.e(TAG,"login sucesss");
                        JSONObject jsonObject = response.getJSONObject(DataAttributes.RESPONSE_DATA);
                        int cid = jsonObject.getInt(RespAttr.COL_con_cid);
                        int sid = jsonObject.getInt(RespAttr.COL_con_sid);
                        String name = jsonObject.getString(RespAttr.COL_con_name);
                        String email = jsonObject.getString(RespAttr.COL_con_email);
                        long aadharuid = jsonObject.getLong(RespAttr.COL_con_aadhar_uid);
                        String aadharstr = jsonObject.getString(RespAttr.COL_con_aadhar_string);
                        int gid = jsonObject.getInt(RespAttr.COL_con_gid);
                        String created = jsonObject.getString(RespAttr.COL_con_created);

                        User user = new User(cid,sid,gid,aadharuid,name,email,aadharstr,created);
                        utils.Login(user);
                        utils.Goto(HomeActivity.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Login();
                break;
            default:
                break;
        }
    }
}
