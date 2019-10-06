package com.equipo.superttapp.projects.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.TraduccionRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.Traduccion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectDetailFragment extends Fragment {
    public static final String TAG = ProjectDetailFragment.class.getCanonicalName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter traduccionAdapter;

    public ProjectDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_project_detail, container, false);
        setHasOptionsMenu(true);
        layoutManager = new LinearLayoutManager(getContext());
        List<Traduccion> traduccions = new ArrayList<>();
        Traduccion traduccion = new Traduccion();
        traduccion.setCalificacion(5);
        traduccion.setEcuacion("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "consequat.");
        traduccion.setFecha(new Date());
        traduccions.add(traduccion);
        traduccions.add(traduccion);
        traduccions.add(traduccion);
        traduccionAdapter = new TraduccionRecyclerViewAdapter(R.layout.item_traduccion, traduccions);
        recyclerView = view.findViewById(R.id.rv_traduccion_list);
        recyclerView.setAdapter(traduccionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getActivity().setTitle("TITULO DEL PROYECTO");
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.project_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_editar:
                Toast.makeText(getContext(), "EDITAR", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_borrar:
                Toast.makeText(getContext(), "Borrar", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
