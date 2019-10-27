package com.equipo.superttapp.projects.repository;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface TraduccionRepository {
    MutableLiveData<BusinessResult<TraduccionModel>> findAllTraduccionesByProyecto(Integer idProyecto, String key);
    MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer idTraduccion, String token);
    MutableLiveData<BusinessResult<TraduccionModel>> uploadTraduccion(RequestBody idUsuario,
                                                                      RequestBody idProyecto,
                                                                      MultipartBody.Part image);
}
