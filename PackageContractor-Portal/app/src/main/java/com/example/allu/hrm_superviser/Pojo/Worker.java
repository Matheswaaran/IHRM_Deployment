package com.example.allu.hrm_superviser.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.allu.hrm_superviser.utils.DataAttributes;


/**
 * Created by allu on 3/21/17.
 */

public class Worker implements Parcelable {
    public int EID,CID,Type,Auth,Skill;
    public String Name,Aadhar_string,Created;
    public long Aadhar_uid;
    public int AID;

    public Worker(int EID, int CID, int type, int auth, int skill, String name, long aadhar_uid, String aadhar_string, String created) {
        this.EID = EID;
        this.CID = CID;
        Type = type;
        Auth = auth;
        Skill = skill;
        Name = name;
        Aadhar_uid = aadhar_uid;
        Aadhar_string = aadhar_string;
        Created = created;
    }

    public void setAID(int aid){
        this.AID = aid;
    }

    protected Worker(Parcel in) {
        EID = in.readInt();
        CID = in.readInt();
        Type = in.readInt();
        Auth = in.readInt();
        Skill = in.readInt();
        Name = in.readString();
        Aadhar_string = in.readString();
        Created = in.readString();
        Aadhar_uid = in.readLong();
        AID = in.readInt();
    }

    public static final Creator<Worker> CREATOR = new Creator<Worker>() {
        @Override
        public Worker createFromParcel(Parcel in) {
            return new Worker(in);
        }

        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };

    public String getType(){
        switch (Type){
            case 1:
                return DataAttributes.WORKER_TYPE_1;
            case 2:
                return DataAttributes.WORKER_TYPE_2;
            case 3:
                return DataAttributes.WORKER_TYPE_3;
            default:
                return null;
        }
    }

    public String getSkill(){
        switch (Skill){
            case 1:
                return DataAttributes.WORKER_SKILL_1;
            case 2:
                return DataAttributes.WORKER_SKILL_2;
            case 3:
                return DataAttributes.WORKER_SKILL_3;
            case 4:
                return DataAttributes.WORKER_SKILL_4;
            default:
                return null;
        }
    }

    public String getAuth(){
        switch (Auth){
            case 0:
                return DataAttributes.WORKER_NOT_AUTH;
            case 1:
                return DataAttributes.WORKER_AUTH;
            default:
                return null;
        }
    }

    public int getAuthImg(){
        switch (Auth){
            case 0:
                return android.R.drawable.checkbox_off_background;
            case 1:
                return android.R.drawable.checkbox_on_background;
            default:
                return 0;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(EID);
        parcel.writeInt(CID);
        parcel.writeInt(Type);
        parcel.writeInt(Auth);
        parcel.writeInt(Skill);
        parcel.writeString(Name);
        parcel.writeString(Aadhar_string);
        parcel.writeString(Created);
        parcel.writeLong(Aadhar_uid);
        parcel.writeInt(AID);
    }
}
