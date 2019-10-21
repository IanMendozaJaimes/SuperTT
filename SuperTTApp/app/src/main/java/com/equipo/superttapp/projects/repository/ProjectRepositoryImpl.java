package com.equipo.superttapp.projects.repository;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.data.TraduccionData;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.BusinessResult;
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
    public MutableLiveData<BusinessResult<ProyectoModel>> findAllProyectosByUser(Integer id, String key) {
        MutableLiveData<BusinessResult<ProyectoModel>> proyectoDataMutableLiveData = new MutableLiveData<>();

        try {
            service.getProyectosByUsuario(id, key).enqueue(new Callback<List<ProyectoData>>() {
                @Override
                public void onResponse(Call<List<ProyectoData>> call, Response<List<ProyectoData>> response) {
                    BusinessResult<ProyectoModel> model = new BusinessResult<>();
                    List<ProyectoModel> modelos = new ArrayList<>();
                    if (response.isSuccessful()) {
                        for (ProyectoData data : response.body()) {
                            ProyectoModel proyectoModel = new ProyectoModel();
                            proyectoModel.setRate(data.getCalificacion());
                            proyectoModel.setId(data.getId());
                            proyectoModel.setName(data.getNombre());
                            proyectoModel.setTextDate(data.getFecha());
                            modelos.add(proyectoModel);
                        }
                        model.setResults(modelos);
                        model.setCode(ResultCodes.SUCCESS);
                    }
                    proyectoDataMutableLiveData.setValue(model);
                }
                @Override
                public void onFailure(Call<List<ProyectoData>> call, Throwable t) {
                    BusinessResult<ProyectoModel> model = new BusinessResult<>();
                    proyectoDataMutableLiveData.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "findAllProyectosByUser ", e);
        }
        return proyectoDataMutableLiveData;
    }

    @Override
    public MutableLiveData<BusinessResult<ProyectoModel>> deleteProyecto(Integer idProyecto, String token) {
        MutableLiveData<BusinessResult<ProyectoModel>> resultado = new MutableLiveData<>();
        try {
            service.deleteProyecto(idProyecto, token).enqueue(new Callback<ProyectoData>() {
                @Override
                public void onResponse(Call<ProyectoData> call, Response<ProyectoData> response) {
                    Log.i(TAG, "deleteProyecto-onResponse " + response.body());
                    BusinessResult<ProyectoModel> model = new BusinessResult<>();
                    model.setCode(ResultCodes.SUCCESS);
                    resultado.setValue(model);
                }
                @Override
                public void onFailure(Call<ProyectoData> call, Throwable t) {
                    Log.e(TAG, "deleteProyecto-onFailure ", t);
                    BusinessResult<ProyectoModel> model = new BusinessResult<>();
                    model.setCode(ResultCodes.ERROR);
                    resultado.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "deleteProyecto ", e);
        }
        return resultado;
    }

    @Override
    public MutableLiveData<BusinessResult<ProyectoModel>> updateProyecto(ProyectoData proyectoData, String token) {
        MutableLiveData<BusinessResult<ProyectoModel>> resultado = new MutableLiveData<>();
        try {
            service.editProyecto(proyectoData.getId(), proyectoData, token).enqueue(new Callback<ProyectoData>() {
                @Override
                public void onResponse(Call<ProyectoData> call, Response<ProyectoData> response) {
                    Log.i(TAG, "updateProyecto-onResponse " + response.body());
                    BusinessResult<ProyectoModel> businessResult = new BusinessResult<>();
                    businessResult.setCode(ResultCodes.SUCCESS);
                    ProyectoModel model = new ProyectoModel();
                    model.setName(proyectoData.getNombre());
                    businessResult.setResult(model);
                    resultado.setValue(businessResult);
                }

                @Override
                public void onFailure(Call<ProyectoData> call, Throwable t) {
                    Log.e(TAG, "updateProyecto-onFailure ", t);
                    BusinessResult<ProyectoModel> model = new BusinessResult<>();
                    model.setCode(ResultCodes.ERROR);
                    resultado.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "findAllProyectosByUser ", e);
        }
        return resultado;
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
