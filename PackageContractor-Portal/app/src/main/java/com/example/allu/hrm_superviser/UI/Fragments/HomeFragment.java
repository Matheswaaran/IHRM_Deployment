package com.example.allu.hrm_superviser.UI.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.allu.hrm_superviser.Pojo.Su_user;
import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.utils.Utils;

public class HomeFragment extends Fragment {

    String Tag = "HomeFragment";

    TextView TXT_suid,TXT_suName,TXT_suEmail;
    Utils utils;
    Su_user user;


    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TXT_suid = (TextView)view.findViewById(R.id.txt_suid);
        TXT_suName = (TextView)view.findViewById(R.id.txt_suname);
        TXT_suEmail = (TextView)view.findViewById(R.id.txt_suemailid);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        utils = new Utils(this.getContext());
        user = utils.GetUserDetails();
        TXT_suid.setText(user.SU_ID+"");
        TXT_suName.setText(user.Name);
        TXT_suEmail.setText(user.Email);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
