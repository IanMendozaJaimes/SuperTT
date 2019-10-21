package com.equipo.superttapp.projects.view;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.ProjectRecyclerViewAdapter;
import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.presenter.ProjectListPresenter;
import com.equipo.superttapp.projects.presenter.ProjectListPresenterImpl;
import com.equipo.superttapp.projects.presenter.ProyectosViewModel;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.PreferencesManager;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectListFragment extends Fragment implements ProjectListView {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter projectAdapter;
    private ProjectListPresenter presenter;
    private List<ProyectoModel> proyectoModelList;
    private static final String TAG = ProjectListFragment.class.getCanonicalName();
    ProyectosViewModel proyectosViewModel;

    public ProjectListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        recyclerView = view.findViewById(R.id.rv_project_list);
        layoutManager = new LinearLayoutManager(getContext());
        proyectoModelList = new ArrayList<>();
        presenter = new ProjectListPresenterImpl();
        projectAdapter = new ProjectRecyclerViewAdapter(proyectoModelList, getActivity(),
                R.layout.item_project);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(layoutManager);
        proyectosViewModel = ViewModelProviders.of(this).get(ProyectosViewModel.class);
        algo();
        return view;
    }

    public void algo() {
        proyectosViewModel.getData().observe(this, proyectodata -> {
            Log.i(TAG, "SE DISPARO " + proyectodata.size());
            List<ProyectoModel> nada = new ArrayList<>();
            for (ProyectoData proyecto : proyectodata) {
                ProyectoModel proyectoModel = new ProyectoModel();
                proyectoModel.setId(proyecto.getId());
                proyectoModel.setName(proyecto.getNombre());
                proyectoModel.setRate(proyecto.getCalificacion());
                nada.add(proyectoModel);
            }
            proyectoModelList.addAll(nada);
            projectAdapter.notifyDataSetChanged();
            showMessage(new BusinessResult<>());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        algo();
        //recuperarTodosProyectos();
    }

    public void recuperarTodosProyectos() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext(),
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.keyExists(PreferencesManager.KEY_USER_IS_LOGGED)
                && preferencesManager.getBooleanValue(PreferencesManager.KEY_USER_IS_LOGGED)) {
            int idUsuario = preferencesManager.getIntegerValue(PreferencesManager.KEY_USER_ID);
            Log.i(TAG, "GAGa " + idUsuario);
            BusinessResult<ProyectoModel> result = presenter.findAllProyectosByUser(idUsuario);
            if (result.getCode().equals(ResultCodes.SUCCESS)) {
                proyectoModelList.clear();
                proyectoModelList.addAll(result.getResults());
                projectAdapter.notifyDataSetChanged();
            } else {
                showMessage(result);
            }
        }
    }

    @Override
    public void showMessage(BusinessResult<ProyectoModel> result) {
        Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.fl_proyectos),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (result.getCode().equals(ResultCodes.RN008)) {
            snackbar.setText(R.string.msg7_no_existe_proyectos_para_mostrar);
        }
        snackbar.show();
    }
}
