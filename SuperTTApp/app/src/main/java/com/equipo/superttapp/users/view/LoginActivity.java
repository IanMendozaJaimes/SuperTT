package com.equipo.superttapp.users.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.btn_iniciar_sesion)
    Button btnIniciarSesion;

    @BindView(R.id.btn_registrarte)
    Button btnRegistrate;

    @BindView(R.id.tv_recuperar_contra)
    TextView tvRecuperarContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCreateAccount();
            }
        });
        tvRecuperarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForgotPassword();
            }
        });
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void goCreateAccount() {
        Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void goForgotPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
