package com.equipo.superttapp.projects.interactor;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ProyectoInteractor {
    BusinessResult<ProyectoModel> findAllProyectosByUser(Integer user);
    MutableLiveData<BusinessResult<ProyectoModel>> deleteProyecto(Integer idProyecto, String token);
    BusinessResult<ProyectoModel> updateProyecto(ProyectoModel model);
    BusinessResult<ProyectoModel> createProyecto(ProyectoModel model, Integer idUsuario);

    MutableLiveData<BusinessResult<ProyectoModel>> findAllProyectosByUser(int i, String s);
}
