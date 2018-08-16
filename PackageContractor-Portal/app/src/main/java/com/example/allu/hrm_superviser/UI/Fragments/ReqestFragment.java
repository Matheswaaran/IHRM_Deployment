package com.example.allu.hrm_superviser.UI.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allu.hrm_superviser.Adapter.Recy_Adapter_Tasks;
import com.example.allu.hrm_superviser.Pojo.Task_view;
import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.UI.Activities.SubActivities.Requests.CompletedRequests;
import com.example.allu.hrm_superviser.UI.Activities.SubActivities.Requests.CreateRequest;
import com.example.allu.hrm_superviser.UI.Activities.SubActivities.Requests.ViewYOurRequests;

import java.util.ArrayList;


public class ReqestFragment extends Fragment {

    RecyclerView Request_Listview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reqest, container, false);

        return v;
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

        Task_view taskView = new Task_view(R.color.light_green,0,"Create Requests");
        taskView.setIntentClass(CreateRequest.class);
        Task_view taskView1 = new Task_view(R.color.lime,0,"View Your Requests");
        taskView1.setIntentClass(ViewYOurRequests.class);
        Task_view t2 = new Task_view(R.color.cyan,0,"Completed requests");
        t2.setIntentClass(CompletedRequests.class);


        ArrayList<Task_view> task_views = new ArrayList<>();
        task_views.add(taskView);
        task_views.add(taskView1);
        task_views.add(t2);
        Recy_Adapter_Tasks adapter_tasks = new Recy_Adapter_Tasks(getContext(),task_views);

        Request_Listview.setAdapter(adapter_tasks);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
