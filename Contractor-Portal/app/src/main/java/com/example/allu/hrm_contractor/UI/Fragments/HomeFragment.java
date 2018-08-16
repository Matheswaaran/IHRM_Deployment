package com.example.allu.hrm_contractor.UI.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.allu.hrm_contractor.Pojo.User;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.utils.Utils;

public class HomeFragment extends Fragment {
    String Tag = "HomeFragment";

    TextView TXT_Cid,TXT_CName,TXT_CEmail;
    Utils utils;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        Log.e(Tag,"init");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TXT_Cid = (TextView)view.findViewById(R.id.text_cid);
        TXT_CName = (TextView)view.findViewById(R.id.text_cname);
        TXT_CEmail = (TextView)view.findViewById(R.id.text_cemailid);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        utils = new Utils(this.getContext());
        user = utils.GetUserDetails();
        TXT_Cid.setText(user.CID+"");
        TXT_CName.setText(user.Name);
        TXT_CEmail.setText(user.Email);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
