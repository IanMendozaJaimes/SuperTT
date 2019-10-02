package com.equipo.superttapp.projects.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.model.Project;

import java.util.List;

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectRecyclerViewAdapter.ViewHolder> {
    private List<Project> projectList;
    private Activity activity;
    private int resource;

    public ProjectRecyclerViewAdapter(List<Project> projectList, Activity activity, int resource) {
        this.projectList = projectList;
        this.activity = activity;
        this.resource = resource;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Project project = this.projectList.get(position);
        holder.tvName.setText(project.getName());
        holder.tvFecha.setText(project.getTextDate());
        holder.tvCalificacion.setText(project.getRate().toString());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvFecha;
        TextView tvCalificacion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nombre_projecto);
            tvFecha = itemView.findViewById(R.id.tv_fecha_projecto);
            tvCalificacion = itemView.findViewById(R.id.tv_calificacion_projecto);
        }
    }
}
