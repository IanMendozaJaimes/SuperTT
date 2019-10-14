package com.equipo.superttapp.projects.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.TraduccionRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.presenter.TraduccionListPresenter;
import com.equipo.superttapp.projects.presenter.TraduccionListPresenterImpl;
import com.equipo.superttapp.util.BundleConstants;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraduccionListActivity extends AppCompatActivity implements TraduccionListView{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter traduccionAdapter;
    private FloatingActionButton fabTradducion;
    private TraduccionListPresenter presenter;
    private String nombreProyecto;
    private Integer idProyecto;
    private List<TraduccionModel> traduccionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduccion_list);
        recyclerView = findViewById(R.id.rv_traduccion_list);
        fabTradducion = findViewById(R.id.fab_add_traduccion);
        layoutManager = new LinearLayoutManager(this);
        traduccionModels = new ArrayList<>();
        presenter = new TraduccionListPresenterImpl();
        TraduccionModel traduccionModel = new TraduccionModel();
        traduccionModel.setCalificacion(5);
        traduccionModel.setEcuacion("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "consequat.");
        traduccionModel.setFecha(new Date());
        traduccionModels.add(traduccionModel);
        traduccionAdapter = new TraduccionRecyclerViewAdapter(R.layout.item_traduccion, traduccionModels);

        recyclerView.setAdapter(traduccionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getIntent().getExtras();
        nombreProyecto = bundle.getString(BundleConstants.TITULO_KEY);
        idProyecto = bundle.getInt(BundleConstants.PROYECTO_ID);
        setTitle(nombreProyecto);
        fabTradducion.setOnClickListener(v -> {
            Toast.makeText(this, "Agregar traduccion", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarTraducciones();
    }

    public void recuperarTraducciones() {
        BusinessResult<TraduccionModel> result = presenter.findAllTraduccionesByProyecto(idProyecto);
        if (result.getCode().equals(ResultCodes.SUCCESS)) {
            traduccionModels.clear();
            traduccionModels.addAll(result.getResults());
            traduccionAdapter.notifyDataSetChanged();
        } else {
            showMessage(result);
        }
    }

    @Override
    public void showMessage(BusinessResult<TraduccionModel> result) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activity_traduccion_list),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (result.getCode().equals(ResultCodes.RN009)) {
            snackbar.setText(R.string.msg8_no_se_puede_mostrar_proyecto);
        }
        snackbar.show();
    }

    @Override
    public void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg11_confirmacion_operacion_borrar_proyecto)
                .setPositiveButton(R.string.label_si, (dialog, which) -> {
                    // Borrar
                    Intent intent = new Intent(this, MainActivity.class);
                    finish();
                    startActivity(intent);
                })
                .setNegativeButton(R.string.label_no, (dialog, which) -> dialog.cancel());
        AlertDialog alerta = builder.create();
        alerta.show();
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
                    dialog.cancel();
                    EditText etNombre = view.findViewById(R.id.et_nombre_proyecto);
                    setTitle("HOLA " + etNombre.getText().toString());
                })
                .setNegativeButton(R.string.label_cancelar, (dialog, which) -> dialog.cancel());
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
