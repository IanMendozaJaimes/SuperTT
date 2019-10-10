package com.equipo.superttapp.projects.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.equipo.superttapp.R;
import com.equipo.superttapp.projects.adapter.TraduccionRecyclerViewAdapter;
import com.equipo.superttapp.projects.model.Traduccion;
import com.equipo.superttapp.projects.presenter.TraduccionListPresenter;
import com.equipo.superttapp.projects.presenter.TraduccionListPresenterImpl;
import com.equipo.superttapp.util.BundleConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraduccionListActivity extends AppCompatActivity implements TraduccionListView{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter traduccionAdapter;
    private TraduccionListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduccion_list);
        layoutManager = new LinearLayoutManager(this);
        List<Traduccion> traduccions = new ArrayList<>();
        Traduccion traduccion = new Traduccion();
        traduccion.setCalificacion(5);
        traduccion.setEcuacion("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "consequat.");
        traduccion.setFecha(new Date());
        traduccions.add(traduccion);
        traduccions.add(traduccion);
        traduccions.add(traduccion);
        traduccionAdapter = new TraduccionRecyclerViewAdapter(R.layout.item_traduccion, traduccions);
        recyclerView = findViewById(R.id.rv_traduccion_list);
        recyclerView.setAdapter(traduccionAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getIntent().getExtras();
        String titulo = bundle.getString(BundleConstants.TITULO_KEY);
        Integer idProyecto = bundle.getInt(BundleConstants.PROYECTO_ID);
        presenter = new TraduccionListPresenterImpl();
        presenter.findAllTraduccionesByProyecto(idProyecto);
        setTitle(titulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_editar:
                Toast.makeText(this, "EDITAR", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_borrar:
                Toast.makeText(this, "Borrar", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
