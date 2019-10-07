package com.equipo.superttapp.users.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.equipo.superttapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.btn_crear_cuenta)
    Button btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        btnCrearCuenta.setOnClickListener(v -> Toast.makeText(SignInActivity.this, "Crear cuenta", Toast.LENGTH_SHORT).show());
    }
}
