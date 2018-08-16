package com.example.allu.hrm_superviser.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by allu on 3/21/17.
 */

public class SupVis_Requirements implements Parcelable {
    public int RID,SU_ID,SID,Skill_1,Skill_2,Skill_3,Skill_4,Alloc_skill_1,Alloc_skill_2,Alloc_skill_3,Alloc_skill_4,C_response;
    public String Taskname,Date,Alloc_Time,Req_date,Created;


    public SupVis_Requirements(int RID, int SU_ID, int SID, int skill_1, int skill_2, int skill_3, int skill_4, int alloc_skill_1, int alloc_skill_2, int alloc_skill_3, int alloc_skill_4, int c_response, String taskname, String date, String alloc_Time, String req_date, String created) {
        this.RID = RID;
        this.SU_ID = SU_ID;
        this.SID = SID;
        Skill_1 = skill_1;
        Skill_2 = skill_2;
        Skill_3 = skill_3;
        Skill_4 = skill_4;
        Alloc_skill_1 = alloc_skill_1;
        Alloc_skill_2 = alloc_skill_2;
        Alloc_skill_3 = alloc_skill_3;
        Alloc_skill_4 = alloc_skill_4;
        C_response = c_response;
        Taskname = taskname;
        Date = date;
        Alloc_Time = alloc_Time;
        Req_date = req_date;
        Created = created;
    }

    protected SupVis_Requirements(Parcel in) {
        RID = in.readInt();
        SU_ID = in.readInt();
        SID = in.readInt();
        Skill_1 = in.readInt();
        Skill_2 = in.readInt();
        Skill_3 = in.readInt();
        Skill_4 = in.readInt();
        Alloc_skill_1 = in.readInt();
        Alloc_skill_2 = in.readInt();
        Alloc_skill_3 = in.readInt();
        Alloc_skill_4 = in.readInt();
        C_response = in.readInt();
        Taskname = in.readString();
        Date = in.readString();
        Alloc_Time = in.readString();
        Req_date = in.readString();
        Created = in.readString();
    }

    public static final Parcelable.Creator<SupVis_Requirements> CREATOR = new Parcelable.Creator<SupVis_Requirements>() {
        @Override
        public SupVis_Requirements createFromParcel(Parcel in) {
            return new SupVis_Requirements(in);
        }

        @Override
        public SupVis_Requirements[] newArray(int size) {
            return new SupVis_Requirements[size];
        }
    };

    public String getGson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(RID);
        parcel.writeInt(SU_ID);
        parcel.writeInt(SID);
        parcel.writeInt(Skill_1);
        parcel.writeInt(Skill_2);
        parcel.writeInt(Skill_3);
        parcel.writeInt(Skill_4);
        parcel.writeInt(Alloc_skill_1);
        parcel.writeInt(Alloc_skill_2);
        parcel.writeInt(Alloc_skill_3);
        parcel.writeInt(Alloc_skill_4);
        parcel.writeInt(C_response);
        parcel.writeString(Taskname);
        parcel.writeString(Date);
        parcel.writeString(Alloc_Time);
        parcel.writeString(Req_date);
        parcel.writeString(Created);
    }
}
