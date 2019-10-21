package com.equipo.superttapp.users.repository;


import android.os.NetworkOnMainThreadException;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.users.data.UsuarioData;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;
import com.equipo.superttapp.util.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {
    private APIService service = ServiceGenerator.createService(APIService.class);
    private static final String TAG = UserRepositoryImpl.class.getCanonicalName();
    @Override
    public UsuarioData login(UsuarioData usuarioData) {
        try {
            Response<UsuarioData> response = service.loginUsuario(usuarioData).execute();
            if (response.isSuccessful())
                usuarioData = response.body();
            else
                usuarioData.setResponseCode(ResultCodes.ERROR);
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "login ", e);
            usuarioData.setResponseCode(ResultCodes.ERROR);
        }
        return usuarioData;
    }

    @Override
    public Integer forgotPassword(UsuarioData usuarioData) {
        Integer resultado = ResultCodes.ERROR;
        try {
            Response<UsuarioData> response = service.recuperarUsuario(usuarioData).execute();
            if (response.isSuccessful() && response.body() != null)
                resultado = response.body().getResponseCode();
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "forgotPassword ", e);
        }
        return resultado;
    }

    @Override
    public Integer createAccount(UsuarioData usuarioData) {
        Integer resultado = ResultCodes.ERROR;
        try {
            Response<UsuarioData> response = service.createUsuario(usuarioData).execute();
            if (response.isSuccessful() && response.body() != null)
                resultado = response.body().getResponseCode();
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "createAccount ", e);
        }
        return resultado;
    }

    @Override
    public MutableLiveData<BusinessResult<UsuarioModel>> updateAccount(UsuarioData usuarioData, String token) {
        MutableLiveData<BusinessResult<UsuarioModel>> resultado = new MutableLiveData<>();
        try {
            service.editUsuario(usuarioData.getId(), usuarioData, token).enqueue(new Callback<UsuarioData>() {
                @Override
                public void onResponse(Call<UsuarioData> call, Response<UsuarioData> response) {
                    Log.i(TAG, "updateAccount-onResponse " + response.body());
                    BusinessResult<UsuarioModel> businessResult = new BusinessResult<>();
                    businessResult.setCode(ResultCodes.SUCCESS);
                    UsuarioModel model = new UsuarioModel();
                    model.setName(usuarioData.getNombre());
                    model.setLastname(usuarioData.getApellidos());
                    businessResult.setResult(model);
                    resultado.setValue(businessResult);
                }

                @Override
                public void onFailure(Call<UsuarioData> call, Throwable t) {
                    Log.e(TAG, "updateAccount-onFailure ", t);
                    BusinessResult<UsuarioModel> model = new BusinessResult<>();
                    model.setCode(ResultCodes.ERROR);
                    resultado.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "updateAccount ", e);
        }
        return resultado;
    }

}
