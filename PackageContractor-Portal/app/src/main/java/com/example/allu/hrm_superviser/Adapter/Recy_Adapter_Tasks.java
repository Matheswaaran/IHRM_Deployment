package com.example.allu.hrm_superviser.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allu.hrm_superviser.Pojo.Task_view;
import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.utils.Utils;

import java.util.ArrayList;

/**
 * Created by allu on 3/21/17.
 */

public class Recy_Adapter_Tasks extends RecyclerView.Adapter<ViewHolder_task> {
    Context context;
    ArrayList<Task_view> taskArrayList;

    Utils utils;

    public Recy_Adapter_Tasks(Context context, ArrayList<Task_view> taskArrayList) {
        this.context = context;
        this.taskArrayList = taskArrayList;

        utils = new Utils(context);
    }


    @Override
    public ViewHolder_task onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_task_layout,parent,false);
        return new ViewHolder_task(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder_task holder, int position) {
        final Task_view task = taskArrayList.get(position);
        holder.Task.setText(task.TaskName);
        holder.layout.setBackgroundColor(ContextCompat.getColor(context, task.Color_ID));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.Goto(task.IntentClass);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }
}

class ViewHolder_task extends RecyclerView.ViewHolder{
    LinearLayout layout;
    TextView Task;
    public ViewHolder_task(View itemView) {
        super(itemView);
        layout = (LinearLayout)itemView.findViewById(R.id.lay_task);
        Task = (TextView)itemView.findViewById(R.id.text_task);
    }
}
