package com.example.allu.hrm_superviser.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allu.hrm_superviser.Pojo.Worker;
import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.interfaces.AttendanceInterface;
import com.example.allu.hrm_superviser.utils.Utils;

import java.util.ArrayList;

/**
 * Created by allu on 4/1/17.
 */

public class Recy_Adapter_worker_atten extends RecyclerView.Adapter<ViewHolder_atten> {

    Context context;
    ArrayList<Worker> workerArrayList;
    Utils utils;
    String TAG = Recy_Adapter_Worker.class.getSimpleName();

    AttendanceInterface attendanceInterface;

    public Recy_Adapter_worker_atten(Context context, ArrayList<Worker> workerArrayList,AttendanceInterface attendanceInterface) {
        this.context = context;
        this.workerArrayList = workerArrayList;
        this.attendanceInterface = attendanceInterface;
    }

    @Override
    public ViewHolder_atten onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_worker_atten_layout,parent,false);
        return new ViewHolder_atten(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder_atten holder, int position) {
        final Worker worker = workerArrayList.get(position);
        holder.Name.setText(worker.Name);
        holder.Type.setText(worker.getType());
        holder.Skill.setText(worker.getSkill());
        holder.Atten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                attendanceInterface.putAttendance(worker,b);
            }
        });

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

class ViewHolder_atten extends RecyclerView.ViewHolder{
    TextView Name,Skill,Type;
    CheckBox Atten;
    LinearLayout Single_worker;
    public ViewHolder_atten(View itemView) {
        super(itemView);
        Name = (TextView) itemView.findViewById(R.id.text_worker_name);
        Skill = (TextView) itemView.findViewById(R.id.text_worker_skill);
        Type = (TextView)itemView.findViewById(R.id.text_worker_type);
        Atten = (CheckBox) itemView.findViewById(R.id.check_worker_atten);

        Single_worker = (LinearLayout)itemView.findViewById(R.id.lay_single_worker);
    }
}
