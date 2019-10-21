package com.equipo.superttapp.projects.repository;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.equipo.superttapp.projects.data.TraduccionData;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class TraduccionRepositoryImpl implements TraduccionRepository{
    private APIService service = ServiceGenerator.createService(APIService.class);
    private static final String TAG = TraduccionRepositoryImpl.class.getCanonicalName();
    @Override
    public List<TraduccionData> findAllTraduccionesByProyecto(Integer idProyecto) {
        List<TraduccionData> proyectos = new ArrayList<>();
        try {
            Response<List<TraduccionData>> response = service.getTraduccionesByProyecto(idProyecto).execute();
            if (response.isSuccessful())
                proyectos = response.body();
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "findAllTraduccionesByProyecto ", e);
        }
        return proyectos;
    }

    @Override
    public Integer deleteTraduccion(Integer idTraduccion) {
        return null;
    }

    @Override
    public Integer updateTraduccion(TraduccionData data) {
        return null;
    }
}
