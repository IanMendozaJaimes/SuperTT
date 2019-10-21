package com.equipo.superttapp.projects.view;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.ProjectRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.presenter.ProyectsListViewModel;
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
    private List<ProyectoModel> proyectoModelList;
    private static final String TAG = ProjectListFragment.class.getCanonicalName();
    ProyectsListViewModel proyectsListViewModel;

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
        projectAdapter = new ProjectRecyclerViewAdapter(proyectoModelList, getActivity(),
                R.layout.item_project);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(layoutManager);
        proyectsListViewModel = ViewModelProviders.of(this).get(ProyectsListViewModel.class);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarTodosProyectos();
    }

    public void recuperarTodosProyectos() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext(),
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.keyExists(PreferencesManager.KEY_USER_IS_LOGGED)
                && preferencesManager.getBooleanValue(PreferencesManager.KEY_USER_IS_LOGGED)) {
            int idUsuario = preferencesManager.getIntegerValue(PreferencesManager.KEY_USER_ID);
            String keyUser = preferencesManager.getStringValue(PreferencesManager.KEY_USER_TOKEN);
            Integer id = 1;
            String key = "Token 8a1b6290aa20003bc5730d49e11b244100d69002";
            proyectsListViewModel.findUserProyects(id, key).observe(this, proyectodata -> {
                Log.i(TAG, "recuperarTodosProyectos() " + proyectodata.getCode());
                if (proyectodata.getCode().equals(ResultCodes.SUCCESS)) {
                    proyectoModelList.clear();
                    proyectoModelList.addAll(proyectodata.getResults());
                    projectAdapter.notifyDataSetChanged();
                }
                showMessage(proyectodata);
            });
        }
    }

    @Override
    public void showMessage(BusinessResult<ProyectoModel> result) {
        Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.fl_proyectos),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (result.getCode().equals(ResultCodes.RN008)) {
            snackbar.setText(R.string.msg7_no_existe_proyectos_para_mostrar);
        }
        if (!result.getCode().equals(ResultCodes.SUCCESS))
            snackbar.show();
    }
}
