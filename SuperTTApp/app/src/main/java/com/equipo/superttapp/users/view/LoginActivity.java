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

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.view.MainActivity;
import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.users.presenter.LoginPresenter;
import com.equipo.superttapp.users.presenter.LoginPresenterImpl;
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

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        hideProgressBar();
        presenter = new LoginPresenterImpl(this);
        btnRegistrate.setOnClickListener(v -> goCreateAccount());
        tvRecuperarContra.setOnClickListener(v -> goForgotPassword());
        btnIniciarSesion.setOnClickListener(v -> {
            cleanErrors();
            LoginFormModel form = new LoginFormModel();
            form.setEmail(etCorreo.getText().toString());
            form.setPassword(etContra.getText().toString());
            presenter.logIn(form);
            hideKeyboard();
        });
        setTitle(R.string.label_login);
        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.keyExists(PreferencesManager.KEY_IS_LOGGED)
                && preferencesManager.getBooleanValue(PreferencesManager.KEY_IS_LOGGED)) {
            goHome();
        }
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
    public void showProgressBar() {
        pbLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pbLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(BusinessResult<LoginFormModel> resultado) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_main_activity),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (resultado.getCode().equals(ResultCodes.RN006)) {
            snackbar.setText(R.string.msg2_cuenta_no_verificada);
        } else if (resultado.getCode().equals(ResultCodes.RN001)
                || resultado.getCode().equals(ResultCodes.RN002) ) {
            snackbar.setText(R.string.msg1_datos_no_validos);
            if (!resultado.getResult().isValidEmail()) {
                etCorreo.setError(getText(R.string.msg1_datos_no_validos));
            }
            if (!resultado.getResult().isValidPassword()) {
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
    public void saveUser(LoginFormModel model) {
        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferencesManager.saveValue(PreferencesManager.KEY_EMAIL, model.getEmail());
        preferencesManager.saveValue(PreferencesManager.KEY_IS_LOGGED, true);
        preferencesManager.saveValue(PreferencesManager.KEY_USER_ID, model.getId());
    }
}
