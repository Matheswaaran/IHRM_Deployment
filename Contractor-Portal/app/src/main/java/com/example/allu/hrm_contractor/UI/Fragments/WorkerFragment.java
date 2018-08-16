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

import com.example.allu.hrm_contractor.Adapter.Recy_Adapter_Tasks;
import com.example.allu.hrm_contractor.Pojo.Task_view;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker.CreateWorkerActivity;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker.ViewAllWorkersActivity;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker.ViewApprovedWorkers;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker.ViewAssignedEmployees;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker.ViewUnAssignedWorkers;

import java.util.ArrayList;

/**
 * Created by allu on 3/21/17.
 */

public class WorkerFragment extends Fragment {
    RecyclerView Task_Listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_worker, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Manage Worker");
        Task_Listview = (RecyclerView)view.findViewById(R.id.Recy_tasks);
        Task_Listview.setLayoutManager(new GridLayoutManager(getContext(),2));
        Task_Listview.setItemAnimator(new DefaultItemAnimator());
        Task_Listview.setHasFixedSize(false);

        Task_view taskView = new Task_view(R.color.light_green,0,"View Workers");
        taskView.setIntentClass(ViewAllWorkersActivity.class);
        Task_view taskView1 = new Task_view(R.color.lime,0,"Create Worker");
        taskView1.setIntentClass(CreateWorkerActivity.class);
        Task_view taskView2 = new Task_view(R.color.teal,0,"Approved Workers");
        taskView2.setIntentClass(ViewApprovedWorkers.class);
        Task_view taskView3 = new Task_view(R.color.pink,0,"Assigned Workers");
        taskView3.setIntentClass(ViewAssignedEmployees.class);
        Task_view taskView4 = new Task_view(R.color.pink,0,"UnAssigned Workers");
        taskView4.setIntentClass(ViewUnAssignedWorkers.class);

        ArrayList<Task_view> task_views = new ArrayList<>();

        task_views.add(taskView1);
        task_views.add(taskView);
        task_views.add(taskView2);
        task_views.add(taskView3);
        task_views.add(taskView4);

        Recy_Adapter_Tasks adapter_tasks = new Recy_Adapter_Tasks(getContext(),task_views);
        Task_Listview.setAdapter(adapter_tasks);
    }
}

