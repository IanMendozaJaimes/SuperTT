package com.equipo.superttapp.projects.view;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.ProjectRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.viewmodel.ProyectsListViewModel;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.PreferencesManager;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton fabCreate;
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
        fabCreate = view.findViewById(R.id.fab_create_proyecto);
        layoutManager = new LinearLayoutManager(getContext());
        proyectoModelList = new ArrayList<>();
        projectAdapter = new ProjectRecyclerViewAdapter(proyectoModelList, getActivity(),
                R.layout.item_project);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(layoutManager);
        proyectsListViewModel = ViewModelProviders.of(this).get(ProyectsListViewModel.class);
        fabCreate.setOnClickListener(v -> crearProyecto());
        return view;
    }

    public void crearProyecto() {
        LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = inflater.inflate(R.layout.dialog_edit_proyecto, null);
        builder.setView(view)
                .setTitle(R.string.label_crear_proyecto)
                .setMessage(R.string.msg12_ingrese_nombre)
                .setPositiveButton(R.string.label_guardar_cambios, (dialog, which) -> {
                    EditText etNombre = view.findViewById(R.id.et_nombre_proyecto);
                    ProyectoModel model = new ProyectoModel();
                    model.setName(etNombre.getText().toString());
                    model.setIdUsuario(14);
                    String key = "Token d8415efb592e04ce9cab000db578c111b47fc32e";
                    proyectsListViewModel.createProyecto(model, key).observe(this,
                            proyectoModelBusinessResult -> {
                                if (proyectoModelBusinessResult.getCode().equals(ResultCodes.SUCCESS))
                                    recuperarTodosProyectos();
                                showMessage(proyectoModelBusinessResult);
                            });
                    dialog.cancel();
                })
                .setNegativeButton(R.string.label_cancelar, (dialog, which) -> dialog.cancel());
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
        recuperarTodosProyectos();
    }

    public void recuperarTodosProyectos() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext(),
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
//        if (preferencesManager.keyExists(PreferencesManager.KEY_USER_IS_LOGGED)
//                && preferencesManager.getBooleanValue(PreferencesManager.KEY_USER_IS_LOGGED)) {
        if (true) {
            int idUsuario = preferencesManager.getIntegerValue(PreferencesManager.KEY_USER_ID);
            String keyUser = preferencesManager.getStringValue(PreferencesManager.KEY_USER_TOKEN);
            Integer id = 14;
            String key = "Token d8415efb592e04ce9cab000db578c111b47fc32e";
            Log.i(TAG, "recuperarTodosProyectos() " + key);
            proyectsListViewModel.findUserProyects(id, key).observe(this, proyectodata -> {
                Log.i(TAG, "recuperarTodosProyectos() " + proyectodata.getCode());
                if (proyectodata.getCode().equals(ResultCodes.SUCCESS)) {
                    proyectoModelList.clear();
                    proyectoModelList.addAll(proyectodata.getResults());
                    projectAdapter.notifyDataSetChanged();
                } else
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
        } else if (result.getCode().equals(ResultCodes.SUCCESS)) {
            snackbar.setText(R.string.msg9_operacion_exitosa);
        }
        snackbar.show();
    }
}
