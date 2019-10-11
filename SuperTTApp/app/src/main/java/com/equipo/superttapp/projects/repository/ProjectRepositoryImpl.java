package com.equipo.superttapp.projects.repository;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.equipo.superttapp.projects.model.Proyecto;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ProjectRepositoryImpl implements ProjectRepository{
    private APIService service = ServiceGenerator.createService(APIService.class);
    private static final String TAG = ProjectRepositoryImpl.class.getCanonicalName();
    @Override
    public List<Proyecto> findAllProyectosByUser(Integer id) {
        List<Proyecto> proyectos = new ArrayList<>();
        // Implementacion sincrona
        try {
            Response<List<Proyecto>> response = service.getProyectosByUsuario(id).execute();
            proyectos = response.body();
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "findAllProyectosByUser ", e);
        }
        return proyectos;
    }
}
