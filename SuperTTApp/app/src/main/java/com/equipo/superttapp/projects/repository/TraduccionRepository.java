package com.equipo.superttapp.projects.repository;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

public interface TraduccionRepository {
    MutableLiveData<BusinessResult<TraduccionModel>> findAllTraduccionesByProyecto(Integer idProyecto, String key);
    MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer idTraduccion, String token);
}
