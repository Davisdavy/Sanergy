package com.moringa.sanergyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moringa.sanergyapp.R;
import com.moringa.sanergyapp.models.Employees;
import com.moringa.sanergyapp.ui.IssueAssetActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeesRecyclerAdapter extends RecyclerView.Adapter<EmployeesRecyclerAdapter.ViewHolder> {

    private List<Employees> employeesList;
    private Context context;
    public EmployeesRecyclerAdapter(Context context, List<Employees> employeesList){
        this.employeesList =employeesList;
        this.context = context;
    }
    @NonNull
    @Override
    public EmployeesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employees_list_item, parent, false);
        return new ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesRecyclerAdapter.ViewHolder holder, int position) {
        holder.user_name_view.setText(employeesList.get(position).getEmp_name());
        holder.user_title_view.setText(employeesList.get(position).getEmp_title());
        CircleImageView user_image_view = holder.user_image_view;
        Glide.with(context).load(employeesList.get(position).getImage()).into(user_image_view);

        String user_id= employeesList.get(position).empId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(context, IssueAssetActivity.class);
                sendIntent.putExtra("emp_id",user_id);
                context.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private  View mView;
        private TextView user_name_view;
        private TextView user_title_view;
        private CircleImageView user_image_view;

        public ViewHolder( View itemView){
            super(itemView);
            mView = itemView;
            user_image_view = (CircleImageView) mView.findViewById(R.id.user_list_image);
            user_name_view = (TextView) mView.findViewById(R.id.user_list_name);
            user_title_view = (TextView) mView.findViewById(R.id.user_list_title);

        }

    }
}
