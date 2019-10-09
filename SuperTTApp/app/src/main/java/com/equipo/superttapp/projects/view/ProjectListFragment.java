package com.equipo.superttapp.projects.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.ProjectRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.Proyecto;
import com.equipo.superttapp.projects.presenter.ProjectListPresenter;
import com.equipo.superttapp.projects.presenter.ProjectListPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectListFragment extends Fragment implements ProjectListView{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter projectAdapter;
    private ProjectListPresenter presenter;

    public ProjectListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        layoutManager = new LinearLayoutManager(getContext());
        List<Proyecto> proyectos = new ArrayList<>();
        Proyecto proyecto = new Proyecto();
        proyecto.setName("Projecto 1");
        proyecto.setTextDate("01/05/2019");
        proyecto.updateDate();
        proyecto.setRate(4.4);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        proyectos.add(proyecto);
        presenter = new ProjectListPresenterImpl();
        projectAdapter = new ProjectRecyclerViewAdapter(proyectos, getActivity(), R.layout.item_project);
        recyclerView = view.findViewById(R.id.rv_project_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(layoutManager);
        presenter.findAllProyectosByUser("email");
        return view;
    }

}
