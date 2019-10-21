package com.equipo.superttapp.projects.interactor;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ProyectoInteractor {
    MutableLiveData<BusinessResult<ProyectoModel>> deleteProyecto(Integer idProyecto, String token);
    MutableLiveData<BusinessResult<ProyectoModel>> updateProyecto(ProyectoModel model, String token);
    MutableLiveData<BusinessResult<ProyectoModel>>createProyecto(ProyectoModel model, String token);
    MutableLiveData<BusinessResult<ProyectoModel>> findAllProyectosByUser(int i, String s);
}
