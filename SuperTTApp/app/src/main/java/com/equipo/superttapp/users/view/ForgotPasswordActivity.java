package com.equipo.superttapp.users.view;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.equipo.superttapp.R;
import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.users.presenter.ForgotPasswordPresenter;
import com.equipo.superttapp.users.presenter.ForgotPasswordPresenterImpl;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordView{

    @BindView(R.id.btn_enviar_correo_recuperacion)
    Button btnEnviarCorreoRecuperacion;
    @BindView(R.id.forgot_password_et_email)
    EditText etEmail;
    @BindView(R.id.pb_forgot_password)
    ProgressBar progressBar;
    ForgotPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        hideProgressBar();

        presenter = new ForgotPasswordPresenterImpl(this);

        btnEnviarCorreoRecuperacion.setOnClickListener(v -> {
            etEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
            LoginFormModel model = new LoginFormModel();
            model.setEmail(etEmail.getText().toString());
            presenter.sendEmail(model);
        });
        setTitle(R.string.title_activity_recuperar);
    }

    @Override
    public void showMessage(LoginFormModel model) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activitu_forgot_password),
                R.string.msg1_datos_no_validos, Snackbar.LENGTH_LONG);
        if (model.getResultCode().equals(ResultCodes.RN006)) {
            snackbar.setText(R.string.msg3_correo_electronico_no_registrado);
        } else if (model.getResultCode().equals(ResultCodes.SUCCESS)) {
            snackbar.setText(R.string.msg6_envio_correo_recuperacion);
        } else {
            if (!model.isValidEmail()) {
                etEmail.setError(getText(R.string.msg1_datos_no_validos));
            }
        }
        snackbar.show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
