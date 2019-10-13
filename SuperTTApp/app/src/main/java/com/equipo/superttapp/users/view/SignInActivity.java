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
import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.presenter.SignInPresenter;
import com.equipo.superttapp.users.presenter.SignInPresenterImpl;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity implements SignInView {

    @BindView(R.id.btn_crear_cuenta)
    Button btnCrearCuenta;
    @BindView(R.id.pg_sign_in)
    ProgressBar progressBar;
    @BindView(R.id.sign_in_et_email)
    EditText etEmail;
    @BindView(R.id.sign_in_et_contra)
    EditText etPassword;
    @BindView(R.id.sign_in_et_confirmar_contra)
    EditText etSecondPassword;
    @BindView(R.id.sign_in_et_nombre)
    EditText etName;
    @BindView(R.id.sign_in_et_apellido)
    EditText etLastname;
    SignInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        hideProgressBar();
        presenter = new SignInPresenterImpl(this);
        btnCrearCuenta.setOnClickListener(v -> {
            cleanErrors();
            SignInFormModel model = new SignInFormModel();
            model.setName(etName.getText().toString());
            model.setLastname(etLastname.getText().toString());
            model.setSecondPassword(etSecondPassword.getText().toString());
            model.setPassword(etPassword.getText().toString());
            model.setEmail(etEmail.getText().toString());
            presenter.signIn(model);
            hideKeyboard();
        });
        setTitle(R.string.title_activity_sign_in);
    }

    @Override
    public void cleanErrors() {
        etName.setError(null);
        etLastname.setError(null);
        etSecondPassword.setError(null);
        etPassword.setError(null);
        etEmail.setError(null);
    }

    @Override
    public void hideKeyboard() {
        etName.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etLastname.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etSecondPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(BusinessResult<SignInFormModel> result) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.cl_activity_sign_in),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (result.getCode().equals(ResultCodes.RN003))
            snackbar.setText(R.string.msg4_correo_electronico_ya_registrado);
        else if (result.getCode().equals(ResultCodes.SUCCESS))
            snackbar.setText(R.string.msg5_verifique_su_cuenta);
        else if (result.getCode().equals(ResultCodes.RN001)
                || result.getCode().equals(ResultCodes.RN002)){
            if (!result.getResult().isValidEmail())
                etEmail.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidPassword())
                etPassword.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidSecondPassword())
                etSecondPassword.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidName())
                etName.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidLastName())
                etLastname.setError(getText(R.string.msg1_datos_no_validos));
            snackbar.setText(R.string.msg1_datos_no_validos);
        }
        snackbar.show();
    }
}
