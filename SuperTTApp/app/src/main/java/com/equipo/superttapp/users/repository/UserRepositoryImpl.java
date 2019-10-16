package com.equipo.superttapp.users.repository;


import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.equipo.superttapp.users.data.UsuarioData;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.ResultCodes;
import com.equipo.superttapp.util.ServiceGenerator;

import java.io.IOException;

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
    public Integer updateAccount(UsuarioData usuarioData) {
        Integer resultado = ResultCodes.ERROR;
        try {
            Response<UsuarioData> response = service.editUsuario(usuarioData.getId(), usuarioData)
                    .execute();
            if (response.isSuccessful() && response.body() != null)
                resultado = response.body().getResponseCode();
        } catch (IOException | NetworkOnMainThreadException e) {
            Log.e(TAG, "updateAccount ", e);
        }
        return resultado;
    }

}
