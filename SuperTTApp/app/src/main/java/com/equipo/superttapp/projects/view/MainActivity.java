package com.equipo.superttapp.projects.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.equipo.superttapp.R;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.users.view.LoginActivity;
import com.equipo.superttapp.users.view.ProfileFragment;
import com.equipo.superttapp.util.PreferencesManager;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static final String TAG = MainActivity.class.getCanonicalName();
    private TextView tvNombre;
    private TextView tvEmail;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvNombre = headerView.findViewById(R.id.nav_header_tv_nombre);
        tvEmail = headerView.findViewById(R.id.nav_header_tv_correo);
        imageView = headerView.findViewById(R.id.imageView);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        PreferencesManager preferencesManager = new PreferencesManager(this,
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.isLogged()) {
            UsuarioModel usuarioModel = preferencesManager.getUser();
            Log.d(TAG, "email " + usuarioModel.getEmail() + " nombre "
                    + usuarioModel.getName() + " imagen " + usuarioModel.getImage());
            if (usuarioModel.getImage().length() < 1)
                usuarioModel.setImage("http://");
            tvNombre.setText(usuarioModel.getName());
            tvEmail.setText(usuarioModel.getEmail());
            Picasso.get().load(usuarioModel.getImage()).error(R.drawable.usuario_defecto).into(imageView);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Class fragment;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = ProjectListFragment.class;
                showFragment(fragment);
                setTitle(getString(R.string.menu_home));
                break;
            case R.id.nav_profile:
                fragment = ProfileFragment.class;
                showFragment(fragment);
                setTitle(getString(R.string.menu_profile));
                break;
            case R.id.nav_log_out:
                logout();
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg11_confirmacion_operacion_cerrar_sesion)
                .setPositiveButton(R.string.label_si, (dialog, which) -> {
                    PreferencesManager manager = new PreferencesManager(getApplicationContext(),
                            PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
                    manager.removeUser();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                })
                .setNegativeButton(R.string.label_no, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
    }
}
