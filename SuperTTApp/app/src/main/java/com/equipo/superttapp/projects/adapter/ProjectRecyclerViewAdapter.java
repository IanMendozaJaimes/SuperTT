package com.equipo.superttapp.projects.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.view.TraduccionListActivity;
import com.equipo.superttapp.util.BundleConstants;

import java.util.List;

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<ProjectRecyclerViewAdapter.ViewHolder> {
    private List<ProyectoModel> proyectoModelList;
    private Activity activity;
    private int resource;

    public ProjectRecyclerViewAdapter(List<ProyectoModel> proyectoModelList, Activity activity, int resource) {
        this.proyectoModelList = proyectoModelList;
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
        final ProyectoModel proyectoModel = this.proyectoModelList.get(position);
        holder.tvName.setText(proyectoModel.getName());
        holder.tvFecha.setText(proyectoModel.getTextDate());
        holder.tvCalificacion.setText(proyectoModel.getRate().toString());
        holder.cvProyecto.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TraduccionListActivity.class);
            intent.putExtra(BundleConstants.PROYECTO_NOMBRE, proyectoModel.getName());
            intent.putExtra(BundleConstants.PROYECTO_ID, proyectoModel.getId());
            intent.putExtra(BundleConstants.PROYECTO_CALIFICACION, proyectoModel.getRate());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return proyectoModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvFecha;
        TextView tvCalificacion;
        CardView cvProyecto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nombre_projecto);
            tvFecha = itemView.findViewById(R.id.tv_fecha_projecto);
            tvCalificacion = itemView.findViewById(R.id.tv_calificacion_projecto);
            cvProyecto = itemView.findViewById(R.id.card_project);
        }
    }
}
