package com.example.allu.hrm_contractor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.allu.hrm_contractor.Pojo.SupVis_Requirements;
import com.example.allu.hrm_contractor.Pojo.Worker;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Worker.CreateWorkerActivity;
import com.example.allu.hrm_contractor.interfaces.WorkerAssign_Interface;
import com.example.allu.hrm_contractor.utils.DataAttributes;

import java.util.ArrayList;

/**
 * Created by allu on 3/31/17.
 */

public class Recy_Adapter_Worker_Assignment extends RecyclerView.Adapter<ViewHolder_WorkerAsssignment> {
    String TAG = Recy_Adapter_Worker_Assignment.class.getSimpleName();
    ArrayList<Worker> workerArrayList;
    Context context;
    WorkerAssign_Interface workerAssign_interface;

    public Recy_Adapter_Worker_Assignment(ArrayList<Worker> workerArrayList, Context context, WorkerAssign_Interface workerAssign_interface) {
        this.workerArrayList = workerArrayList;
        this.context = context;
        this.workerAssign_interface = workerAssign_interface;
    }


    @Override
    public ViewHolder_WorkerAsssignment onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_worker_assignment_layout,parent,false);
        return new ViewHolder_WorkerAsssignment(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_WorkerAsssignment holder, final int position) {
        final Worker worker = workerArrayList.get(position);
        holder.Name.setText(worker.Name);
        holder.Type.setText(worker.getType());
        holder.Skill.setText(worker.getSkill());

        if(worker.Auth == 1){
            holder.Assign.setChecked(true);
        }else{
            holder.Assign.setChecked(false);
        }

        holder.Approval.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                holder.Approval.setChecked(!b);
            }
        });

        holder.Assign.setChecked(worker.assigned);
        holder.Assign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                setAssigned(position,b);
                if(b){
                    workerAssign_interface.OnAssign(position);
                }else{
                    workerAssign_interface.OnDeAssign(position);
                }
            }
        });
    }

    void setAssigned(int pos,boolean b){
        workerArrayList.get(pos).assigned = b;
    }

    @Override
    public int getItemCount() {
        return workerArrayList.size();
    }

    public void addWorker(Worker worker){
        Log.e(TAG,"worker added");
        workerArrayList.add(worker);
        this.notifyItemInserted(workerArrayList.size() - 1);
        this.notifyDataSetChanged();
    }

}

class ViewHolder_WorkerAsssignment extends RecyclerView.ViewHolder{
    TextView Name,Skill,Type;
    CheckBox Approval;
    LinearLayout Single_worker;

    CheckBox Assign;
    public ViewHolder_WorkerAsssignment(View itemView) {
        super(itemView);
        Name = (TextView) itemView.findViewById(R.id.text_worker_name);
        Skill = (TextView) itemView.findViewById(R.id.text_worker_skill);
        Type = (TextView)itemView.findViewById(R.id.text_worker_type);
        Approval = (CheckBox) itemView.findViewById(R.id.check_appoved);
        Approval.setActivated(false);
        Approval.setClickable(false);

        Single_worker = (LinearLayout)itemView.findViewById(R.id.lay_single_worker);

        Assign = (CheckBox) itemView.findViewById(R.id.radio_assign);
    }
}
