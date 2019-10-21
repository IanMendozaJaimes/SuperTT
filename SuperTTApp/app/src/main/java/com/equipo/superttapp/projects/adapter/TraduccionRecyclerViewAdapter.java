package com.equipo.superttapp.projects.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.view.TraduccionListView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TraduccionRecyclerViewAdapter extends RecyclerView.Adapter<TraduccionRecyclerViewAdapter.ViewHolder> {
    private int resource;
    private List<TraduccionModel> traducciones;
    private TraduccionListView view;

    public TraduccionRecyclerViewAdapter(int resource, List<TraduccionModel> traducciones, TraduccionListView view) {
        this.resource = resource;
        this.view = view;
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
        final TraduccionModel traduccionModel = traducciones.get(position);
        holder.tvCalificacion.setText(traduccionModel.getCalificacion().toString());
        holder.tvEcuacion.setText(traduccionModel.getEcuacion());
        holder.tvFecha.setText(traduccionModel.getFecha());
        Picasso.get().load(traduccionModel.getUrl()).error(R.drawable.card_defecto)
                .into(holder.imvBackground);
        holder.btnBorrarTraduccion
                .setOnClickListener(v -> view.borrarTraduccion(traduccionModel.getId()));
    }

    @Override
    public int getItemCount() {
        return traducciones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha;
        TextView tvEcuacion;
        TextView tvCalificacion;
        ImageView imvBackground;
        Button btnBorrarTraduccion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tv_fecha_traduccion);
            tvEcuacion = itemView.findViewById(R.id.tv_ecuacion_texto);
            tvCalificacion = itemView.findViewById(R.id.tv_calificacion_traduccion);
            imvBackground = itemView.findViewById(R.id.image_ecuacion);
            btnBorrarTraduccion = itemView.findViewById(R.id.btn_borrar_traduccion);
        }
    }
}
