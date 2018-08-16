package com.example.allu.hrm_contractor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allu.hrm_contractor.Pojo.SupVis_Requirements;
import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.SubActivities.Request.AssignEmployees;
import com.example.allu.hrm_contractor.utils.DataAttributes;
import com.example.allu.hrm_contractor.utils.Utils;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by allu on 3/21/17.
 */

public class Recy_Adapter_SupVis_Req extends RecyclerView.Adapter<Viewholder_SupVis_Req> {
    String TAG = Recy_Adapter_SupVis_Req.class.getSimpleName();
    Context context;
    Utils utils;
    ArrayList<SupVis_Requirements> requirementsArrayList;

    public Recy_Adapter_SupVis_Req(Context context, ArrayList<SupVis_Requirements> requirementsArrayList) {
        this.context = context;
        this.requirementsArrayList = requirementsArrayList;
        utils = new Utils(context);

    }

    @Override
    public Viewholder_SupVis_Req onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_requirement_layout,parent,false);
        return new Viewholder_SupVis_Req(v);
    }

    @Override
    public void onBindViewHolder(Viewholder_SupVis_Req holder, int position) {
        final SupVis_Requirements requirements = requirementsArrayList.get(position);
        holder.Taskname.setText(requirements.Taskname);
        holder.SU_ID.setText(requirements.SU_ID+"");
        holder.No_skill1.setText(requirements.Skill_1+"");
        holder.No_skill2.setText(requirements.Skill_2+"");
        holder.No_skill3.setText(requirements.Skill_3+"");
        holder.No_skill4.setText(requirements.Skill_4+"");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,""+requirements.C_response);
                if(requirements.C_response == 0){
                    Intent i = new Intent(context, AssignEmployees.class);
                    i.putExtra(DataAttributes.INTENT_Su_req,requirements);
                    context.startActivity(i);
                }else{
                    utils.Toast("Request Already addressed");
                }

            }
        });
    }

    public void addRequests(SupVis_Requirements requirements){
        Log.e(TAG,"req added");
        requirementsArrayList.add(requirements);
        this.notifyItemInserted(requirementsArrayList.size() - 1);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return requirementsArrayList.size();
    }
}

class Viewholder_SupVis_Req extends RecyclerView.ViewHolder{
    LinearLayout layout;
    TextView Taskname,SU_ID,No_skill1,No_skill2,No_skill3,No_skill4;
    public Viewholder_SupVis_Req(View itemView) {
        super(itemView);
        layout = (LinearLayout)itemView.findViewById(R.id.lay_req_layout);
        Taskname = (TextView)itemView.findViewById(R.id.text_task_name);
        SU_ID = (TextView)itemView.findViewById(R.id.txt_suvis_id);
        No_skill1 = (TextView)itemView.findViewById(R.id.text_no_skill1);
        No_skill2 = (TextView)itemView.findViewById(R.id.text_no_skill2);
        No_skill3 = (TextView)itemView.findViewById(R.id.text_no_skill3);
        No_skill4 = (TextView)itemView.findViewById(R.id.text_no_skill4);
    }
}
