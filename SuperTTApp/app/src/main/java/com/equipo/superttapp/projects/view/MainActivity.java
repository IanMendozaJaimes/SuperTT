package com.equipo.superttapp.projects.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.equipo.superttapp.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    public static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile
        ).setDrawerLayout(drawerLayout).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.i(TAG, "ENTRO");
            Log.i(TAG, "Destino " + destination.getId() + " " + destination.getLabel());
            /*if (destination.getId() == R.id.mobile_navigation) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.msg11_confirmacion_operacion_cerrar_sesion)
                        .setPositiveButton(R.string.label_si, (dialog, which) -> {
                            Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.label_no, (dialog, which) -> {
                            Toast.makeText(this, "Nada", Toast.LENGTH_SHORT).show();
                        });
                AlertDialog dialogo = builder.create();
                dialogo.show();
            }*/
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
