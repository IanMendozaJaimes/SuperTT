package com.equipo.superttapp.projects.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.TraduccionRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.viewmodel.TraducccionListViewModel;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.Constants;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.PreferencesManager;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraduccionListActivity extends AppCompatActivity implements TraduccionListView {
    private static final String TAG = TraduccionListActivity.class.getCanonicalName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter traduccionAdapter;
    private FloatingActionButton fabTraduccion;
    private String nombreProyecto;
    private Integer idProyecto;
    private Double calificacionProyecto;
    private List<TraduccionModel> traduccionModels;
    TraducccionListViewModel traducionListViewModel;
    private String photoPathTemp;
    private UsuarioModel usuarioModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduccion_list);
        recyclerView = findViewById(R.id.rv_traduccion_list);
        fabTraduccion = findViewById(R.id.fab_add_traduccion);
        layoutManager = new LinearLayoutManager(this);
        traduccionModels = new ArrayList<>();
        traduccionAdapter = new TraduccionRecyclerViewAdapter(R.layout.item_traduccion,
                traduccionModels, this);

        recyclerView.setAdapter(traduccionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nombreProyecto = bundle.getString(Constants.PROYECTO_NOMBRE);
            idProyecto = bundle.getInt(Constants.PROYECTO_ID);
            calificacionProyecto = bundle.getDouble(Constants.PROYECTO_CALIFICACION);
        }
        setTitle(nombreProyecto);
        fabTraduccion.setOnClickListener(v -> onFabTraduccionClick());
        traducionListViewModel = ViewModelProviders.of(this).get(TraducccionListViewModel.class);

        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.isLogged()) {
            usuarioModel = preferencesManager.getUser();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarTraducciones();
    }

    public void onFabTraduccionClick() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImagefile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, Constants.APP_DOMAIN_PROVIDER, photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                Log.d(TAG, "photoPathTemp " + photoPathTemp);
                Log.d(TAG, "photoUri " + photoUri);
                startActivityForResult(intent, Constants.TAKE_PICTURE_RESULT);
            }
        }
    }

    private File createImagefile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageName = "IMAGE_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(imageName, ".jpg", storageDir);
        photoPathTemp = photo.getAbsolutePath();
        return photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TAKE_PICTURE_RESULT && resultCode == Activity.RESULT_OK) {
            Intent i = new Intent(this, NewTraduccionActivity.class);
            i.putExtra(Constants.TRADUCCION_PATH, photoPathTemp);
            i.putExtra(Constants.PROYECTO_ID, idProyecto);
            startActivity(i);
        }
    }

    public void recuperarTraducciones() {
        traducionListViewModel.findTraducciones(idProyecto, usuarioModel.getKeyAuth()).observe(this, traducciones -> {
            if (traducciones.getCode().equals(ResultCodes.SUCCESS)) {
                traduccionModels.clear();
                traduccionModels.addAll(traducciones.getResults());
                traduccionAdapter.notifyDataSetChanged();
            } else {
                showMessage(traducciones);
            }
        });
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg11_confirmacion_operacion_borrar_proyecto)
                .setPositiveButton(R.string.label_si, (dialog, which) -> {
                    traducionListViewModel.deleteProyecto(idProyecto, usuarioModel.getKeyAuth())
                            .observe(this, proyectoResult -> {
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
        intent.putExtra(Constants.OPERACION_BORRRAR_PROYECTO, true);
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
                    traducionListViewModel.updateProyecto(model, usuarioModel.getKeyAuth())
                            .observe(this, proyectoModelBusinessResult -> {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg11_confirmacion_operacion_borrar_proyecto)
                .setPositiveButton(R.string.label_si, (dialog, which) -> {
                    traducionListViewModel.deleteTraduccion(idTraduccion,
                            usuarioModel.getKeyAuth()).observe(this, result -> {
                                showMessage(result);
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
