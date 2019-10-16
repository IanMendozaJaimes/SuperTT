package com.equipo.superttapp.projects.repository;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.ProyectoData;

import java.util.List;

public interface ProjectRepository {
    MutableLiveData<List<ProyectoData>> findAllProyectosByUser(Integer user);
    Integer deleteProyecto(Integer idProyecto);
    Integer updateProyecto(ProyectoData proyectoData);
    Integer createProyecto(ProyectoData proyectoData);
}
