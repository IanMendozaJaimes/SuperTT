package com.equipo.superttapp.projects.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.TraduccionRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.presenter.TraducccionListViewModel;
import com.equipo.superttapp.util.BundleConstants;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.PreferencesManager;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class TraduccionListActivity extends AppCompatActivity implements TraduccionListView {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter traduccionAdapter;
    private FloatingActionButton fabTradducion;
    private Button btnBorrar;
    private String nombreProyecto;
    private Integer idProyecto;
    private Double calificacionProyecto;
    private List<TraduccionModel> traduccionModels;
    private static final String TAG = TraduccionListActivity.class.getCanonicalName();
    TraducccionListViewModel traducionListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduccion_list);
        recyclerView = findViewById(R.id.rv_traduccion_list);
        fabTradducion = findViewById(R.id.fab_add_traduccion);
        btnBorrar = findViewById(R.id.btn_borrar_traduccion);
        layoutManager = new LinearLayoutManager(this);
        traduccionModels = new ArrayList<>();
        traduccionAdapter = new TraduccionRecyclerViewAdapter(R.layout.item_traduccion,
                traduccionModels, this);

        recyclerView.setAdapter(traduccionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nombreProyecto = bundle.getString(BundleConstants.PROYECTO_NOMBRE);
            idProyecto = bundle.getInt(BundleConstants.PROYECTO_ID);
            calificacionProyecto = bundle.getDouble(BundleConstants.PROYECTO_CALIFICACION);
        }
        setTitle(nombreProyecto);
        fabTradducion.setOnClickListener(v -> Toast.makeText(this,
                "Agregar traduccion", Toast.LENGTH_SHORT).show());
        traducionListViewModel = ViewModelProviders.of(this).get(TraducccionListViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarTraducciones();
    }

    public void recuperarTraducciones() {
        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.keyExists(PreferencesManager.KEY_USER_IS_LOGGED)
                && preferencesManager.getBooleanValue(PreferencesManager.KEY_USER_IS_LOGGED)) {
            String keyUser = preferencesManager.getStringValue(PreferencesManager.KEY_USER_TOKEN);
            String key = "Token 8a1b6290aa20003bc5730d49e11b244100d69002";
            traducionListViewModel.findTraducciones(idProyecto, key).observe(this, traducciones -> {
                Log.i(TAG, "recuperarTraducciones() " + traducciones.getCode());
                if (traducciones.getCode().equals(ResultCodes.SUCCESS)) {
                    traduccionModels.clear();
                    traduccionModels.addAll(traducciones.getResults());
                    traduccionAdapter.notifyDataSetChanged();
                } else {
                    showMessage(traducciones);
                }
            });
        }
    }

    @Override
    public void showMessage(BusinessResult<TraduccionModel> result) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activity_traduccion_list),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (result.getCode().equals(ResultCodes.RN009)) {
            snackbar.setText(R.string.msg8_no_se_puede_mostrar_proyecto);
        } else if (result.getCode().equals(ResultCodes.RN001)
                || result.getCode().equals(ResultCodes.RN002)) {
            snackbar.setText(R.string.msg1_datos_no_validos);
        } else if (result.getCode().equals(ResultCodes.SUCCESS)) {
            snackbar.setText(R.string.msg9_operacion_exitosa);
        }
        snackbar.show();
    }

    @Override
    public void showDeleteDialog() {
        String key = "Token 8a1b6290aa20003bc5730d49e11b244100d69002";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg11_confirmacion_operacion_borrar_proyecto)
                .setPositiveButton(R.string.label_si, (dialog, which) -> {
                    traducionListViewModel.deleteProyecto(idProyecto, key).observe(this,
                            proyectoResult -> {
                                Log.i(TAG, "deleteProyecto() "
                                        + proyectoResult.getCode());
                                if (proyectoResult.getCode().equals(ResultCodes.SUCCESS))
                                    deleteProyectoSuccess();
                                else
                                    operationError();
                            });
                    dialog.cancel();
                })
                .setNegativeButton(R.string.label_no, (dialog, which) -> dialog.cancel());
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    @Override
    public void deleteProyectoSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BundleConstants.OPERACION_BORRRAR_PROYECTO, true);
        finish();
        startActivity(intent);
    }

    @Override
    public void operationError() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activity_traduccion_list),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void changeProyectoSuccess(BusinessResult<ProyectoModel> result) {
        setTitle(result.getResult().getName());
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activity_traduccion_list),
                R.string.msg9_operacion_exitosa, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showEditDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = inflater.inflate(R.layout.dialog_edit_proyecto, null);
        builder.setView(view)
                .setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg12_ingrese_nombre)
                .setPositiveButton(R.string.label_guardar_cambios, (dialog, which) -> {
                    EditText etNombre = view.findViewById(R.id.et_nombre_proyecto);
                    ProyectoModel model = new ProyectoModel();
                    model.setId(idProyecto);
                    model.setName(etNombre.getText().toString());
                    model.setRate(calificacionProyecto);
                    model.setIdUsuario(1);
                    String key = "Token 8a1b6290aa20003bc5730d49e11b244100d69002";
                    traducionListViewModel.updateProyecto(model, key).observe(this,
                            proyectoModelBusinessResult -> {
                        if (proyectoModelBusinessResult.getCode().equals(ResultCodes.SUCCESS)) {
                            changeProyectoSuccess(proyectoModelBusinessResult);
                        } else
                            operationError();
                    });
                    dialog.cancel();
                })
                .setNegativeButton(R.string.label_cancelar, (dialog, which) -> dialog.cancel());
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    @Override
    public void borrarTraduccion(Integer idTraduccion) {
        String key = "Token 8a1b6290aa20003bc5730d49e11b244100d69002";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg11_confirmacion_operacion_borrar_proyecto)
                .setPositiveButton(R.string.label_si, (dialog, which) -> {
                    traducionListViewModel.deleteTraduccion(idTraduccion, key).observe(this,
                            traduccionModelBusinessResult -> {
                                Log.i(TAG, "borrarTraduccion() "
                                        + traduccionModelBusinessResult.getCode());
                                showMessage(traduccionModelBusinessResult);
                                recuperarTraducciones();
                            });
                    dialog.cancel();
                })
                .setNegativeButton(R.string.label_no, (dialog, which) -> dialog.cancel());
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_editar:
                showEditDialog();
                break;
            case R.id.item_borrar:
                showDeleteDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
