package com.equipo.superttapp.users.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.view.MainActivity;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.users.viewmodel.LoginViewModel;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.PreferencesManager;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.btn_iniciar_sesion)
    Button btnIniciarSesion;
    @BindView(R.id.btn_registrarte)
    Button btnRegistrate;
    @BindView(R.id.tv_recuperar_contra)
    TextView tvRecuperarContra;
    @BindView(R.id.sign_in_et_email)
    EditText etCorreo;
    @BindView(R.id.sign_in_et_contra)
    EditText etContra;
    @BindView(R.id.pbLogin)
    ProgressBar pbLogin;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.isLogged()) {
            goHome();
        }
        hideProgressBar();
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        btnRegistrate.setOnClickListener(v -> goCreateAccount());
        tvRecuperarContra.setOnClickListener(v -> goForgotPassword());
        btnIniciarSesion.setOnClickListener(v -> {
            cleanErrors();
            UsuarioModel form = new UsuarioModel();
            form.setEmail(etCorreo.getText().toString());
            form.setPassword(etContra.getText().toString());
            showProgressBar();
            loginViewModel.login(form).observe(this, usuarioModelBusinessResult -> {
                if (usuarioModelBusinessResult.getCode().equals(ResultCodes.SUCCESS)) {
                    saveUser(usuarioModelBusinessResult.getResult());
                    goHome();
                } else {
                    loginError(usuarioModelBusinessResult);
                }
                hideProgressBar();

            });
            hideKeyboard();
        });
        setTitle(R.string.label_login);

    }

    @Override
    public void cleanErrors() {
        etContra.setError(null);
        etCorreo.setError(null);
    }

    @Override
    public void hideKeyboard() {
        etCorreo.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etContra.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    @Override
    public void goCreateAccount() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void goForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void hideProgressBar() {
        pbLogin.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        pbLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginError(BusinessResult<UsuarioModel> resultado) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_main_activity),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (resultado.getCode().equals(ResultCodes.RN006)) {
            snackbar.setText(R.string.msg2_cuenta_no_verificada);
        } else if (resultado.getCode().equals(ResultCodes.RN001)) {
            snackbar.setText(R.string.msg1_datos_no_validos);
        } else if (resultado.getCode().equals(ResultCodes.RN002)) {
            snackbar.setText(R.string.msg1_datos_no_validos);
            if (!resultado.getResult().getValidEmail()) {
                etCorreo.setError(getText(R.string.msg1_datos_no_validos));
            }
            if (!resultado.getResult().getValidPassword()) {
                etContra.setError(getText(R.string.msg1_datos_no_validos));
            }
        }
        snackbar.show();
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void saveUser(UsuarioModel model) {
        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferencesManager.saveUser(model);
    }
}
