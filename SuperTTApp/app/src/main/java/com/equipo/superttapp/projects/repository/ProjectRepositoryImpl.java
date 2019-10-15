package com.equipo.superttapp.projects.repository;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.ResultCodes;
import com.equipo.superttapp.util.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ProjectRepositoryImpl implements ProjectRepository{
    private APIService service = ServiceGenerator.createService(APIService.class);
    private static final String TAG = ProjectRepositoryImpl.class.getCanonicalName();
    @Override
    public List<ProyectoData> findAllProyectosByUser(Integer id) {
        List<ProyectoData> proyectos = new ArrayList<>();
        try {
            Response<List<ProyectoData>> response = service.getProyectosByUsuario(id).execute();
            if (response.isSuccessful())
                proyectos = response.body();
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "findAllProyectosByUser ", e);
        }
        return proyectos;
    }

    @Override
    public Integer deleteProyecto(Integer idProyecto) {
        Integer code = ResultCodes.ERROR;
        try {
            Response<ProyectoData> response = service.deleteProyecto(idProyecto).execute();
            if (response.isSuccessful())
                code = ResultCodes.SUCCESS;
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "deleteProyecto ", e);
        }
        return code;
    }

    @Override
    public Integer updateProyecto(ProyectoData proyectoData) {
        Integer code = ResultCodes.ERROR;
        try {
            Response<ProyectoData> response = service.editProyecto(proyectoData.getId(),
                    proyectoData).execute();
            if (response.isSuccessful())
                code = ResultCodes.SUCCESS;
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "updateProyecto ", e);
        }
        return code;
    }

    @Override
    public Integer createProyecto(ProyectoData proyectoData) {
        Integer code = ResultCodes.ERROR;
        try {
            Response<ProyectoData> response = service.createProyecto(proyectoData).execute();
            if (response.isSuccessful())
                code = ResultCodes.SUCCESS;
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "createProyecto ", e);
        }
        return code;
    }
}
