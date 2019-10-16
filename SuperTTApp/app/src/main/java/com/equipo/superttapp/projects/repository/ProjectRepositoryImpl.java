package com.equipo.superttapp.projects.repository;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.ResultCodes;
import com.equipo.superttapp.util.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepositoryImpl implements ProjectRepository{
    private APIService service = ServiceGenerator.createService(APIService.class);
    private static final String TAG = ProjectRepositoryImpl.class.getCanonicalName();
    @Override
    public MutableLiveData<List<ProyectoData>> findAllProyectosByUser(Integer id) {
        List<ProyectoData> proyectos = new ArrayList<>();
        MutableLiveData<List<ProyectoData>> proyectoDataMutableLiveData = new MutableLiveData<>();

        try {
            service.getProyectosByUsuario(id).enqueue(new Callback<List<ProyectoData>>() {
                @Override
                public void onResponse(Call<List<ProyectoData>> call, Response<List<ProyectoData>> response) {
                    Log.i(TAG, "NANA " + response.body().size());
                    proyectoDataMutableLiveData.setValue(response.body());
                }
                @Override
                public void onFailure(Call<List<ProyectoData>> call, Throwable t) {

                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "findAllProyectosByUser ", e);
        }
        return proyectoDataMutableLiveData;
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
