package com.equipo.superttapp.projects.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.viewmodel.NewTraduccionViewModel;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.Constants;
import com.equipo.superttapp.util.PreferencesManager;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTraduccionActivity extends AppCompatActivity {
    private static final String TAG = NewTraduccionActivity.class.getName();
    private String photoPath;
    private Integer idProyecto;
    private NewTraduccionViewModel viewModel;

    @BindView(R.id.imvPreview)
    ImageView imvPreview;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    @BindView(R.id.pbNewTraduccion)
    ProgressBar pbLogin;
    private UsuarioModel usuarioModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_traduccion);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idProyecto = bundle.getInt(Constants.PROYECTO_ID);
            photoPath = bundle.getString(Constants.TRADUCCION_PATH);
            Picasso.get().load("file:" + photoPath).into(imvPreview);
        }
        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.isLogged()) {
            usuarioModel = preferencesManager.getUser();
        }
        viewModel = ViewModelProviders.of(this).get(NewTraduccionViewModel.class);
        setTitle(R.string.title_activity_new_traduccion);
        hideProgressBar();
    }

    @OnClick(R.id.btnUpload)
    public void onViewClicked(View view) {
        showProgressBar();
        imvPreview.setDrawingCacheEnabled(true);
        imvPreview.buildDrawingCache();
        Bitmap bitmap = imvPreview.getDrawingCache();

        TraduccionModel model = new TraduccionModel();
        model.setUrl(photoPath);
        model.setIdProyecto(idProyecto);

        viewModel.uploadImage(model, usuarioModel.getKeyAuth(), bitmap).observe(this, result -> {
            hideProgressBar();
            if (result.getCode().equals(ResultCodes.SUCCESS)) {
                finish();
            } else {
                showMessage(result);
            }
        });
    }

    public void showMessage(BusinessResult<TraduccionModel> result) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activity_new_traduccion),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void hideProgressBar() {
        pbLogin.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        pbLogin.setVisibility(View.VISIBLE);
    }
}
