package com.equipo.superttapp.projects.repository;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.TraduccionData;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

import java.util.List;

public interface TraduccionRepository {
    MutableLiveData<BusinessResult<TraduccionModel>> findAllTraduccionesByProyecto(Integer idProyecto, String key);
    MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer idTraduccion, String token);
}
