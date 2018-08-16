package com.example.allu.hrm_superviser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allu.hrm_superviser.Pojo.Worker;
import com.example.allu.hrm_superviser.R;
//import com.example.allu.hrm_superviser.UI.Activities.SubActivities.Worker.CreateWorkerActivity;
import com.example.allu.hrm_superviser.utils.DataAttributes;
import com.example.allu.hrm_superviser.utils.Utils;

import java.util.ArrayList;

/**
 * Created by allu on 3/21/17.
 */

public class Recy_Adapter_Worker extends RecyclerView.Adapter<Viewholder_worker> {
    Context context;
    ArrayList<Worker> workerArrayList;
    Utils utils;
    String TAG = Recy_Adapter_Worker.class.getSimpleName();

    public Recy_Adapter_Worker(Context context, ArrayList<Worker> workerArrayList) {
        this.context = context;
        this.workerArrayList = workerArrayList;
        utils = new Utils(context);
    }

    @Override
    public Viewholder_worker onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_worker_layout,parent,false);
        return new Viewholder_worker(v);
    }

    @Override
    public void onBindViewHolder(Viewholder_worker holder, int position) {
        final Worker worker = workerArrayList.get(position);
        holder.Name.setText(worker.Name);
        holder.Type.setText(worker.getType());
        holder.Skill.setText(worker.getSkill());
        holder.Approval.setImageResource(worker.getAuthImg());

        holder.Single_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(context, CreateWorkerActivity.class);
                i.putExtra(DataAttributes.INTENT_VIEW,true);
                i.putExtra(DataAttributes.INTENT_WORKER,worker);
                context.startActivity(i);*/
               Log.e(TAG,"clicked");
            }
        });

    }

    public void addWorker(Worker worker){
        Log.e(TAG,"worker added");
        workerArrayList.add(worker);
        this.notifyItemInserted(workerArrayList.size() - 1);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return workerArrayList.size();
    }
}

class Viewholder_worker extends RecyclerView.ViewHolder{
    TextView Name,Skill,Type;
    ImageView Approval;
    LinearLayout Single_worker;

    public Viewholder_worker(View itemView) {
        super(itemView);
        Name = (TextView) itemView.findViewById(R.id.text_worker_name);
        Skill = (TextView) itemView.findViewById(R.id.text_worker_skill);
        Type = (TextView)itemView.findViewById(R.id.text_worker_type);
        Approval = (ImageView)itemView.findViewById(R.id.img_worker_approved);

        Single_worker = (LinearLayout)itemView.findViewById(R.id.lay_single_worker);
    }
}
