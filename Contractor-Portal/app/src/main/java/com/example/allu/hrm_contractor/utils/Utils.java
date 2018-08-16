package com.example.allu.hrm_contractor.utils;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.allu.hrm_contractor.Pojo.Aadhar;
import com.example.allu.hrm_contractor.Pojo.User;
import com.example.allu.hrm_contractor.UI.Activities.LoginActivity;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;


/**
 * Created by allu on 2/14/17.
 */

public class Utils {

    String TAG = "Utils";
    Context mContext;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    Gson gson;
    Aadhar aadhar;

    public Utils(Context context){
        mContext = context;
        preferences = mContext.getSharedPreferences(DataAttributes.PREFERENCE_STRING,Context.MODE_PRIVATE);
        gson = new Gson();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Authenticating please wait");
        progressDialog.setCancelable(false);
    }

    public void Goto(Class cls){
        Intent i=new Intent(mContext,cls);
        mContext.startActivity(i);
    }

    public void ShowDialog(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    public void CloseDialog(){
        if(progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }

    public void Login(User user){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DataAttributes.PREFERENCE_USER_OBJECT,user.getGson());
        editor.apply();
    }

    public void Logout(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(DataAttributes.PREFERENCE_USER_OBJECT);
        editor.apply();
        Goto(LoginActivity.class);
    }

    public User GetUserDetails(){
        if(LoginStatus()){
            String json = preferences.getString(DataAttributes.PREFERENCE_USER_OBJECT,"");
            User user = gson.fromJson(json,User.class);
            return user;
        }else{
            return null;
        }
    }

    public boolean isEmptyString(String... Args){
        for (String arg : Args){
            if(arg.trim().equals("") || arg == null){
                return false;
            }
        }
        return true;
    }

    public boolean isEmptyint(int... Args){
        for (int arg : Args){
            if(arg == 0){
                return false;
            }
        }
        return true;
    }

    public boolean isEmptylong(long... Args){
        for (long arg : Args){
            if(arg == 0){
                return false;
            }
        }
        return true;
    }

    public boolean LoginStatus(){
        if(preferences.contains(DataAttributes.PREFERENCE_USER_OBJECT)){
            return true;
        }
        return false;
    }

    public void Toast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public Aadhar processScannedData(String scanData){
        Log.d("Rajdeol",scanData);
        aadhar = new Aadhar();
        XmlPullParserFactory pullParserFactory;
        try {
            // init the parserfactory
            pullParserFactory = XmlPullParserFactory.newInstance();
            // get the parser
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(scanData));
            // parse the XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("Rajdeol","Start document");
                } else if(eventType == XmlPullParser.START_TAG && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                    // extract data from tag
                    //uid
                    aadhar.uid = parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR);
                    //name
                    aadhar.name = parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR);
                    //gender
                    aadhar.gender = parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR);
                    // year of birth
                    aadhar.yob = parser.getAttributeValue(null,DataAttributes.AADHAR_YOB_ATTR);
                    //gname
                    aadhar.gname = parser.getAttributeValue(null,DataAttributes.AADHAR_GNAME_ATTR);

                    // care of
                    aadhar.careof = parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR);

                    // street
                    aadhar.street = parser.getAttributeValue(null,DataAttributes.AADHAR_STREET_ATTR);

                    // house
                    aadhar.house = parser.getAttributeValue(null,DataAttributes.AADHAR_HOUSE_ATTR);

                    // landmark
                    aadhar.landmark = parser.getAttributeValue(null,DataAttributes.AADHAR_LM_ATTR);

                    // village
                    aadhar.vtc = parser.getAttributeValue(null,DataAttributes.AADHAR_VTC_ATTR);
                    // Post Office
                    aadhar.postoffice = parser.getAttributeValue(null,DataAttributes.AADHAR_PO_ATTR);
                    // district
                    aadhar.dist = parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR);

                    // subdistrict
                    aadhar.subdist = parser.getAttributeValue(null,DataAttributes.AADHAR_SUBDIST_ATTR);

                    // state
                    aadhar.state = parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR);
                    // Post Code
                    aadhar.pincode = parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR);


                } else if(eventType == XmlPullParser.END_TAG) {
                    Log.d(TAG,"End tag "+parser.getName());
                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d(TAG,"Text "+parser.getText());
                }
                // update eventType
                eventType = parser.next();
            }
            // display the data on screen
        } catch (XmlPullParserException e) {

            aadhar = null;
            e.printStackTrace();
            Log.e(TAG,"error in parsing");
        } catch (IOException e) {
            aadhar = null;
            Log.e(TAG,"error in parsing 1");
            e.printStackTrace();
        }

        return aadhar;
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}
