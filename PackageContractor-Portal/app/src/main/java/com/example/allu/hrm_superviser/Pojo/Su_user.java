package com.example.allu.hrm_superviser.Pojo;

import com.google.gson.Gson;

import java.sql.Timestamp;

/**
 * Created by allu on 3/30/17.
 */

public class Su_user {
    public int SU_ID,SID,GID;
    long Aadhar_UID;
    public String Name,Email,Aadhar_String;
    Timestamp Created;

    public Su_user(int SU_ID, int SID, int GID, long aadhar_UID, String name, String email, String aadhar_String, String created) {
        this.SU_ID = SU_ID;
        this.SID = SID;
        this.GID = GID;
        Aadhar_UID = aadhar_UID;
        Name = name;
        Email = email;
        Aadhar_String = aadhar_String;
        Created = Timestamp.valueOf(created);
    }


    public String getGson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
