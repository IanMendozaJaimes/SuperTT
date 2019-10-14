package com.equipo.superttapp.projects.view;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.ProjectRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.presenter.ProjectListPresenter;
import com.equipo.superttapp.projects.presenter.ProjectListPresenterImpl;
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
    private List<ProyectoModel> proyectoModels;
    private static final String TAG = ProjectListFragment.class.getCanonicalName();

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
        proyectoModels = new ArrayList<>();
        presenter = new ProjectListPresenterImpl();
        ProyectoModel proyectoModel = new ProyectoModel();
        proyectoModel.setName("Projecto 1");
        proyectoModel.setTextDate("01/05/2019");
        proyectoModel.updateDate();
        proyectoModel.setRate(4.4);
        proyectoModel.setId(-1);
        proyectoModels.add(proyectoModel);
        projectAdapter = new ProjectRecyclerViewAdapter(proyectoModels, getActivity(),
                R.layout.item_project);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        recuperarTodosProyectos();
    }

    public void recuperarTodosProyectos() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext(),
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.keyExists(PreferencesManager.KEY_USER_IS_LOGGED)
                && preferencesManager.getBooleanValue(PreferencesManager.KEY_USER_IS_LOGGED)) {
            int idUsuario = preferencesManager.getIntegerValue(PreferencesManager.KEY_USER_ID);
            BusinessResult<ProyectoModel> result = presenter.findAllProyectosByUser(idUsuario);
            if (result.getCode().equals(ResultCodes.SUCCESS)) {
                proyectoModels.clear();
                proyectoModels.addAll(result.getResults());
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
