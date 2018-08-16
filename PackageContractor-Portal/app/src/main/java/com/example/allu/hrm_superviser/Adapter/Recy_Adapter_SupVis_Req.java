package com.example.allu.hrm_superviser.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allu.hrm_superviser.Pojo.SupVis_Requirements;
import com.example.allu.hrm_superviser.R;
import com.example.allu.hrm_superviser.interfaces.Requirment_clickInterface;

import java.util.ArrayList;

/**
 * Created by allu on 3/21/17.
 */

public class Recy_Adapter_SupVis_Req extends RecyclerView.Adapter<Viewholder_SupVis_Req> {
    String TAG = Recy_Adapter_SupVis_Req.class.getSimpleName();
    Context context;
    ArrayList<SupVis_Requirements> requirementsArrayList;

    Requirment_clickInterface requirment_clickInterface;

    public Recy_Adapter_SupVis_Req(Context context, ArrayList<SupVis_Requirements> requirementsArrayList,Requirment_clickInterface clickInterface) {
        this.context = context;
        this.requirementsArrayList = requirementsArrayList;
        requirment_clickInterface = clickInterface;

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
        holder.SupervisorName.setText(requirements.SID+"");
        holder.SupervisorName.setVisibility(View.GONE);
        holder.No_skill1.setText(requirements.Skill_1+"");
        holder.No_skill2.setText(requirements.Skill_2+"");
        holder.No_skill3.setText(requirements.Skill_3+"");
        holder.No_skill4.setText(requirements.Skill_4+"");
        if(requirements.C_response == 1){
            holder.Response.setText("YES");
        }else {
            holder.Response.setText("NO");
        }
        holder.Single_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requirment_clickInterface.onClick(requirements);
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
    TextView Taskname,SupervisorName,No_skill1,No_skill2,No_skill3,No_skill4,Response;
    LinearLayout Single_lay;
    public Viewholder_SupVis_Req(View itemView) {
        super(itemView);
        Taskname = (TextView)itemView.findViewById(R.id.text_task_name);
        SupervisorName = (TextView)itemView.findViewById(R.id.text_suvis_name);
        No_skill1 = (TextView)itemView.findViewById(R.id.text_no_skill1);
        No_skill2 = (TextView)itemView.findViewById(R.id.text_no_skill2);
        No_skill3 = (TextView)itemView.findViewById(R.id.text_no_skill3);
        No_skill4 = (TextView)itemView.findViewById(R.id.text_no_skill4);
        Response = (TextView)itemView.findViewById(R.id.txt_response);

        Single_lay = (LinearLayout)itemView.findViewById(R.id.lay_single);
    }
}
