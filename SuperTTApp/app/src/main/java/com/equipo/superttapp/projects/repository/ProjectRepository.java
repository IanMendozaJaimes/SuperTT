package com.equipo.superttapp.projects.repository;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ProjectRepository {
    MutableLiveData<BusinessResult<ProyectoModel>> findAllProyectosByUser(Integer id, String token);
    MutableLiveData<BusinessResult<ProyectoModel>> deleteProyecto(Integer idProyecto, String token);
    MutableLiveData<BusinessResult<ProyectoModel>>  updateProyecto(ProyectoData proyectoData, String token);
    Integer createProyecto(ProyectoData proyectoData);
}
