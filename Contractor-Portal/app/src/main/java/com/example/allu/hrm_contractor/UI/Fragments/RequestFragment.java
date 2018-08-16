package com.example.allu.hrm_contractor.UI.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allu.hrm_contractor.Adapter.Recy_Adapter_SupVis_Req;
import com.example.allu.hrm_contractor.Adapter.Recy_Adapter_Tasks;
import com.example.allu.hrm_contractor.Pojo.SupVis_Requirements;
import com.example.allu.hrm_contractor.Pojo.Task_view;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Request.ViewAllRequests;

import java.util.ArrayList;

/**
 * Created by allu on 3/21/17.
 */

public class RequestFragment extends Fragment {
    RecyclerView Request_Listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Requests");
        Request_Listview = (RecyclerView)view.findViewById(R.id.recy_request_list);
        Request_Listview.setHasFixedSize(false);
        Request_Listview.setItemAnimator(new DefaultItemAnimator());
        Request_Listview.setLayoutManager(new GridLayoutManager(view.getContext(),2));

        Task_view taskView = new Task_view(R.color.light_green,0,"View Requests");
        taskView.setIntentClass(ViewAllRequests.class);
        Task_view taskView1 = new Task_view(R.color.lime,0,"View SuperVisors");
        Task_view taskView2 = new Task_view(R.color.cyan,0,"Pending Requests");
        ArrayList<Task_view> task_views = new ArrayList<>();
        task_views.add(taskView);
      //  task_views.add(taskView1);
      //  task_views.add(taskView2);
        Recy_Adapter_Tasks adapter_tasks = new Recy_Adapter_Tasks(getContext(),task_views);

        Request_Listview.setAdapter(adapter_tasks);
    }

}
