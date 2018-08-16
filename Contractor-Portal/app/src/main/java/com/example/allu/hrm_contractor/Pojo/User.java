package com.example.allu.hrm_contractor.Pojo;

import com.google.gson.Gson;

import java.sql.Timestamp;

/**
 * Created by allu on 3/20/17.
 */

public class User {
    public int CID,SID,GID;
    long Aadhar_UID;
    public String Name,Email,Aadhar_String;
    Timestamp Created;

    public User(int CID, int SID, int GID, long aadhar_UID, String name, String email, String aadhar_String, String created) {
        this.CID = CID;
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
