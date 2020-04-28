package com.equipo.superttapp.users.repository;


import android.os.NetworkOnMainThreadException;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.users.data.UsuarioData;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.APIService;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;
import com.equipo.superttapp.util.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {
    private APIService service = ServiceGenerator.createService(APIService.class);
    private static final String TAG = UserRepositoryImpl.class.getCanonicalName();

    @Override
    public MutableLiveData<BusinessResult<UsuarioModel>> login(UsuarioData usuarioData) {
        MutableLiveData<BusinessResult<UsuarioModel>> resultado = new MutableLiveData<>();
        try {
            service.loginUsuario(usuarioData).enqueue(new Callback<UsuarioData>() {
                @Override
                public void onResponse(Call<UsuarioData> call, Response<UsuarioData> response) {
                    BusinessResult<UsuarioModel> businessResult = new BusinessResult<>();
                    UsuarioModel model = new UsuarioModel();
                    if (response.isSuccessful() && response.body() != null) {
                        businessResult.setCode(response.body().getResponseCode());
                        if (businessResult.getCode().equals(ResultCodes.SUCCESS)) {
                            businessResult.setCode(ResultCodes.SUCCESS);
                            model.setName(response.body().getNombre());
                            model.setEmail(response.body().getEmail());
                            model.setLastname(response.body().getApellidos());
                            model.setId(response.body().getId());
                            model.setKeyAuth(response.body().getKeyAuth());
                            model.setImage(response.body().getImage());
                            businessResult.setResult(model);
                        }
                    }
                    businessResult.setResult(model);
                    resultado.setValue(businessResult);
                }

                @Override
                public void onFailure(Call<UsuarioData> call, Throwable t) {
                    Log.e(TAG, "login-onFailure ", t);
                    BusinessResult<UsuarioModel> model = new BusinessResult<>();
                    model.setCode(ResultCodes.ERROR);
                    resultado.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "login ", e);
        }
        return resultado;
    }

    @Override
    public MutableLiveData<BusinessResult<UsuarioModel>> forgotPassword(UsuarioData usuarioData) {
        MutableLiveData<BusinessResult<UsuarioModel>> resultado = new MutableLiveData<>();
        try {
            service.recuperarUsuario(usuarioData).enqueue(new Callback<UsuarioData>() {
                @Override
                public void onResponse(Call<UsuarioData> call, Response<UsuarioData> response) {
                    Log.i(TAG, "forgotPassword-onResponse " + response);
                    BusinessResult<UsuarioModel> businessResult = new BusinessResult<>();
                    if (response.isSuccessful() && response.body() != null
                            && response.body().getResponseCode() != null) {
                        businessResult.setCode(response.body().getResponseCode());
                    }
                    resultado.setValue(businessResult);
                }

                @Override
                public void onFailure(Call<UsuarioData> call, Throwable t) {
                    Log.e(TAG, "forgotPassword-onFailure ", t);
                    BusinessResult<UsuarioModel> model = new BusinessResult<>();
                    resultado.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "forgotPassword ", e);
        }

        return resultado;
    }

    @Override
    public MutableLiveData<BusinessResult<UsuarioModel>> createAccount(UsuarioData usuarioData) {
        MutableLiveData<BusinessResult<UsuarioModel>> resultado = new MutableLiveData<>();
        try {
            service.createUsuario(usuarioData).enqueue(new Callback<UsuarioData>() {
                @Override
                public void onResponse(Call<UsuarioData> call, Response<UsuarioData> response) {
                    Log.i(TAG, "createAccount-onResponse " + response);
                    BusinessResult<UsuarioModel> businessResult = new BusinessResult<>();
                    if (response.isSuccessful() && response.body() != null) {
                        businessResult.setCode(response.body().getResponseCode());
                    }
                    resultado.setValue(businessResult);
                }

                @Override
                public void onFailure(Call<UsuarioData> call, Throwable t) {
                    Log.e(TAG, "createAccount-onFailure ", t);
                    BusinessResult<UsuarioModel> model = new BusinessResult<>();
                    model.setCode(ResultCodes.ERROR);
                    resultado.setValue(model);
                }
            });
        } catch (NetworkOnMainThreadException e) {
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
                    if (response.isSuccessful() && response.body() != null
                            && response.body().getResponseCode() != null) {
                        businessResult.setCode(response.body().getResponseCode());
                        UsuarioModel model = new UsuarioModel();
                        model.setName(usuarioData.getNombre());
                        model.setLastname(usuarioData.getApellidos());
                        if (businessResult.getCode().equals(ResultCodes.RN001))
                            model.setValidCurrentPassword(false);
                        businessResult.setResult(model);
                    }
                    resultado.setValue(businessResult);
                }

                @Override
                public void onFailure(Call<UsuarioData> call, Throwable t) {
                    Log.e(TAG, "updateAccount-onFailure ", t);
                    BusinessResult<UsuarioModel> businessResult = new BusinessResult<>();
                    resultado.setValue(businessResult);
                }
            });
        } catch (NetworkOnMainThreadException e) {
            Log.e(TAG, "updateAccount ", e);
            BusinessResult<UsuarioModel> businessResult = new BusinessResult<>();
            resultado.setValue(businessResult);
        }
        return resultado;
    }

}
