package com.equipo.superttapp.projects.repository;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

import java.util.List;

public interface ProjectRepository {
    MutableLiveData<BusinessResult<ProyectoModel>> findAllProyectosByUser(Integer id, String key);
    MutableLiveData<BusinessResult<ProyectoModel>> deleteProyecto(Integer idProyecto, String token);
    Integer updateProyecto(ProyectoData proyectoData);
    Integer createProyecto(ProyectoData proyectoData);
}
