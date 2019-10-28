package com.equipo.superttapp.projects.repository;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.TraduccionData;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;
import com.equipo.superttapp.util.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraduccionRepositoryImpl implements TraduccionRepository{
    private APIService service = ServiceGenerator.createService(APIService.class);
    private static final String TAG = TraduccionRepositoryImpl.class.getCanonicalName();
    @Override
    public MutableLiveData<BusinessResult<TraduccionModel>> findAllTraduccionesByProyecto(Integer idProyecto, String key) {
        MutableLiveData<BusinessResult<TraduccionModel>> proyectoDataMutableLiveData = new MutableLiveData<>();

        try {
            service.getTraduccionesByProyecto(idProyecto, key).enqueue(new Callback<List<TraduccionData>>() {
                @Override
                public void onResponse(Call<List<TraduccionData>> call, Response<List<TraduccionData>> response) {
                    Log.i(TAG, "findAllTraduccionesByProyecto-onResponse " + response.body());
                    BusinessResult<TraduccionModel> model = new BusinessResult<>();
                    List<TraduccionModel> modelos = new ArrayList<>();
                    model.setCode(ResultCodes.SUCCESS);
                    for (TraduccionData data : response.body()) {
                        TraduccionModel traduccion = new TraduccionModel();
                        traduccion.setCalificacion(data.getCalificacion());
                        traduccion.setId(data.getId());
                        traduccion.setEcuacion(data.getEcuacion());
                        traduccion.setUrl(data.getUrl());
                        traduccion.setFecha(data.getFecha());
                        modelos.add(traduccion);
                    }
                    model.setResults(modelos);
                    proyectoDataMutableLiveData.setValue(model);
                }
                @Override
                public void onFailure(Call<List<TraduccionData>> call, Throwable t) {
                    BusinessResult<TraduccionModel> model = new BusinessResult<>();
                    proyectoDataMutableLiveData.setValue(model);
                    Log.i(TAG, "findAllTraduccionesByProyecto-onFailure ", t);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "findAllTraduccionesByProyecto ", e);
        }
        return proyectoDataMutableLiveData;
    }

    @Override
    public MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer idTraduccion, String token) {
        MutableLiveData<BusinessResult<TraduccionModel>> resultado = new MutableLiveData<>();
        try {
            service.deleteTraduccion(idTraduccion, token).enqueue(new Callback<TraduccionData>() {
                @Override
                public void onResponse(Call<TraduccionData> call, Response<TraduccionData> response) {
                    BusinessResult<TraduccionModel> model = new BusinessResult<>();
                    model.setCode(ResultCodes.SUCCESS);
                    resultado.setValue(model);
                }
                @Override
                public void onFailure(Call<TraduccionData> call, Throwable t) {
                    Log.e(TAG, "onFailure ", t);
                    BusinessResult<TraduccionModel> model = new BusinessResult<>();
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
    public MutableLiveData<BusinessResult<TraduccionModel>> uploadTraduccion(RequestBody idProyecto, MultipartBody.Part image, String token) {
        MutableLiveData<BusinessResult<TraduccionModel>> resultado = new MutableLiveData<>();
        try {
            service.uploadTraduccion(idProyecto, image, token).enqueue(new Callback<TraduccionData>() {
                @Override
                public void onResponse(Call<TraduccionData> call, Response<TraduccionData> response) {
                    Log.i(TAG, "uploadTraduccion-onResponse " + response.isSuccessful() + " "
                            + response.body());
                    BusinessResult<TraduccionModel> model = new BusinessResult<>();
                    if (response.isSuccessful()) {
                        model.setCode(ResultCodes.SUCCESS);
                    }
                }

                @Override
                public void onFailure(Call<TraduccionData> call, Throwable t) {
                    Log.e(TAG, "uploadTraduccion-onFailure ", t);
                    BusinessResult<TraduccionModel> model = new BusinessResult<>();
                    resultado.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "uploadTraduccion ", e);
        }
        return resultado;
    }
}
