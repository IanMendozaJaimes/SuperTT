package com.equipo.superttapp.projects.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.model.Traduccion;
import com.equipo.superttapp.util.DateFormater;

import java.util.List;

public class TraduccionRecyclerViewAdapter extends RecyclerView.Adapter<TraduccionRecyclerViewAdapter.ViewHolder> {
    private int resource;
    private List<Traduccion> traducciones;

    public TraduccionRecyclerViewAdapter(int resource, List<Traduccion> traducciones) {
        this.resource = resource;
        this.traducciones = traducciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Traduccion traduccion = traducciones.get(position);
        holder.tvCalificacion.setText(traduccion.getCalificacion().toString());
        holder.tvEcuacion.setText(traduccion.getEcuacion());
        holder.tvFecha.setText(DateFormater.convertDateToString(traduccion.getFecha()));
    }

    @Override
    public int getItemCount() {
        return traducciones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha;
        TextView tvEcuacion;
        TextView tvCalificacion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tv_fecha_traduccion);
            tvEcuacion = itemView.findViewById(R.id.tv_ecuacion_texto);
            tvCalificacion = itemView.findViewById(R.id.tv_calificacion_traduccion);
        }
    }
}
