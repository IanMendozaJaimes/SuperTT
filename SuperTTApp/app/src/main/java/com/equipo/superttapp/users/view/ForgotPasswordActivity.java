package com.equipo.superttapp.users.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.equipo.superttapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.btn_enviar_correo_recuperacion)
    Button btnEnviarCorreoRecuperacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        btnEnviarCorreoRecuperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEnviarCorreoClicked();
            }
        });
    }

    public void onClickEnviarCorreoClicked() {
        Toast.makeText(this, "Enviar correo de recuepracion", Toast.LENGTH_SHORT).show();
    }
}
