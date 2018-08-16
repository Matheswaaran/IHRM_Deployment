package com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.example.allu.hrm_contractor.Pojo.Aadhar;
import com.example.allu.hrm_contractor.Pojo.User;
import com.example.allu.hrm_contractor.Pojo.Worker;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.HomeActivity;
import com.example.allu.hrm_contractor.utils.DataAttributes;
import com.example.allu.hrm_contractor.utils.RespAttr;
import com.example.allu.hrm_contractor.utils.Utils;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CreateWorkerActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "CreateWorkerActivty";
    Utils utils;
    RequestQueue queue;

    Worker worker;
    boolean View,Edit;

    EditText Edit_name,Edit_uid,Edit_gender,Edit_dob,Edit_co,Edit_add_dn,Edit_add_street,Edit_add_landmark,Edit_add_vill,Edit_add_post,Edit_add_dist,Edit_add_subdist,Edit_add_state,Edit_add_pincode;
    Spinner Spinner_Skill,Spinner_Emptype;
    Button BTN_Create,BTN_Aadhar_scan;

    String Wor_name,Wor_aadhar_string,Wor_Skill,Wor_EmpType;
    long Wor_aadhar_uid;

    User MainUser;

    ArrayAdapter<CharSequence> Skills,EmpTypes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_worker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Create Worker");

        Intent i = getIntent();
        View = i.getBooleanExtra(DataAttributes.INTENT_VIEW,false);
        Edit = i.getBooleanExtra(DataAttributes.INTENT_EDIT,false);


        LoadFields();
        LoadSpinner();

        utils = new Utils(this);
        queue = Volley.newRequestQueue(this);

        MainUser = utils.GetUserDetails();



        if(View){
            worker = i.getParcelableExtra(DataAttributes.INTENT_WORKER);
            int s_pos = Skills.getPosition(worker.getSkill());
            Spinner_Skill.setSelection(s_pos);

            int et_pos = EmpTypes.getPosition(worker.getType());
            Spinner_Emptype.setSelection(et_pos);

            BTN_Create.setClickable(false);
            BTN_Aadhar_scan.setClickable(false);
        }else if(Edit){

            worker = i.getParcelableExtra(DataAttributes.INTENT_WORKER);
            int s_pos = Skills.getPosition(worker.getSkill());
            Spinner_Skill.setSelection(s_pos);

            int et_pos = EmpTypes.getPosition(worker.getType());
            Spinner_Emptype.setSelection(et_pos);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra(DataAttributes.INTENT_FragmentId,R.id.nav_workers);
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

    void LoadFields(){
        BTN_Create = (Button)findViewById(R.id.btn_create);
        BTN_Aadhar_scan = (Button)findViewById(R.id.btn_worker_aadhar);

        Edit_name = (EditText)findViewById(R.id.edit_worker_name);
        Edit_uid = (EditText)findViewById(R.id.edit_aadhar_uid);
        Edit_gender = (EditText)findViewById(R.id.edit_aadhar_gender);
        Edit_dob = (EditText)findViewById(R.id.edit_aadhar_yob);
        Edit_co = (EditText)findViewById(R.id.edit_aadhar_co);
        Edit_add_dn = (EditText)findViewById(R.id.edit_aadhar_add_do);
        Edit_add_street = (EditText)findViewById(R.id.edit_aadhar_add_street);
        Edit_add_landmark = (EditText)findViewById(R.id.edit_aadhar_add_lm);
        Edit_add_post = (EditText)findViewById(R.id.edit_aadhar_add_post);
        Edit_add_vill = (EditText)findViewById(R.id.edit_aadhar_add_vill);
        Edit_add_dist = (EditText)findViewById(R.id.edit_aadhar_add_dist);
        Edit_add_subdist = (EditText)findViewById(R.id.edit_aadhar_add_subdis);
        Edit_add_state = (EditText)findViewById(R.id.edit_aadhar_add_state);
        Edit_add_pincode = (EditText)findViewById(R.id.edit_aadhar_add_pin);

        Spinner_Skill = (Spinner)findViewById(R.id.spinner_skill);
        Spinner_Emptype = (Spinner)findViewById(R.id.spinner_emptype);


    }

    void setAadharAttributesFormember(Aadhar aadhar){
        Edit_name.setText(aadhar.name);
        Edit_uid.setText(aadhar.uid);
        Edit_gender.setText(aadhar.gender);
        Edit_co.setText(aadhar.careof);
        Edit_dob.setText(aadhar.yob);
        Edit_add_dn.setText(aadhar.house);
        Edit_add_street.setText(aadhar.street);
        Edit_add_landmark.setText(aadhar.landmark);
        Edit_add_vill.setText(aadhar.vtc);
        Edit_add_post.setText(aadhar.postoffice);
        Edit_add_dist.setText(aadhar.dist);
        Edit_add_state.setText(aadhar.state);
        Edit_add_subdist.setText(aadhar.subdist);
        Edit_add_pincode.setText(aadhar.pincode);
        Log.e(TAG,"member details set");
    }

    void LoadSpinner(){

        Skills = ArrayAdapter.createFromResource(this, R.array.skill, android.R.layout.simple_spinner_item);
        Skills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Wor_Skill = Skills.getItem(1).toString();

        EmpTypes = ArrayAdapter.createFromResource(this, R.array.emptype, android.R.layout.simple_spinner_item);
        EmpTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Wor_EmpType = EmpTypes.getItem(1).toString();

        Spinner_Skill.setAdapter(Skills);
        Spinner_Skill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Wor_Skill = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Wor_Skill = adapterView.getItemAtPosition(1).toString();
            }
        });

        Spinner_Emptype.setAdapter(EmpTypes);
        Spinner_Emptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Wor_EmpType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Wor_EmpType = adapterView.getItemAtPosition(1).toString();
            }
        });
    }

    private void startScan() {
        MaterialBarcodeScanner barcodeScanner = new MaterialBarcodeScannerBuilder(this)
                .withEnableAutoFocus(true)
                .withBackfacingCamera()
                .withBleepEnabled(true)
                .withText("Scan the QR Code")
                .withCenterTracker()
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        Wor_aadhar_string = barcode.rawValue;
                        processScannedCode(barcode.rawValue);
                    }
                })
                .build();

        barcodeScanner.startScan();
    }

    void CreateNewUser(){
        Wor_name = Edit_name.getText().toString();
        if(utils.isEmptyString(Wor_name,Wor_name,Wor_aadhar_string,Wor_Skill,Wor_EmpType)){
            if(utils.isEmptylong(Long.parseLong(Edit_uid.getText().toString()))){
                Wor_aadhar_uid = Long.parseLong(Edit_uid.getText().toString());
                JSONObject param = new JSONObject();
                try {
                    param.put(DataAttributes.REQUEST_OPTION,"create_emp");
                    param.put(RespAttr.COL_emp_name,Wor_name);
                    param.put(RespAttr.COL_emp_cid,MainUser.CID);
                    param.put(RespAttr.COL_emp_aadhar_uid,Wor_aadhar_uid);
                    param.put(RespAttr.COL_con_aadhar_string,Wor_aadhar_string);
                    param.put(RespAttr.COL_emp_skill,Skills.getPosition(Wor_Skill)+1);
                    param.put(RespAttr.COL_emp_emptype,EmpTypes.getPosition(Wor_EmpType)+1);
                    Log.e(TAG,param.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    utils.Toast("Json parsing error "+e.toString());
                    Log.e(TAG,"Json parsing error "+e.toString());
                }
                Log.e(TAG,param.toString()+" "+com.example.allu.hrm_contractor.utils.URL.URL_Supervisor);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, com.example.allu.hrm_contractor.utils.URL.URL_Contractors, param, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Log.e(TAG,jsonObject.toString());
                            if (jsonObject.getInt(DataAttributes.RESPONSE_CODE) == DataAttributes.RESPONSE_SUCCESS){
                                utils.Toast(jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
                                Intent i = new Intent(CreateWorkerActivity.this,UploadFiles.class);
                                i.putExtra(DataAttributes.INTENT_name,Wor_name);
                                i.putExtra(DataAttributes.INTENT_aadhar_uid,Wor_aadhar_uid+"");
                                startActivity(i);
                                Log.e(TAG,jsonObject.getString(DataAttributes.RESPONSE_MESSAGE));
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
            }else{
                utils.Toast("Aadhar Card is Invalid");
                Log.e(TAG,"Aadhar Card is Invalid");
            }
        }else{
            utils.Toast("Enter All Fields");
            Log.e(TAG,"Enter All Fields");
        }

    }


    void processScannedCode(String data){
        Aadhar aadhar = utils.processScannedData(data);
        setAadharAttributesFormember(aadhar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_worker_aadhar:
                startScan();
                break;
            case R.id.btn_create:
                CreateNewUser();
                break;
            default:
                break;
        }
    }
}
