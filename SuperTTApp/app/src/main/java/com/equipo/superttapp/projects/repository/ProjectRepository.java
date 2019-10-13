package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.data.ProyectoData;

import java.util.List;

public interface ProjectRepository {
    List<ProyectoData> findAllProyectosByUser(Integer user);
    Integer deleteProyecto(Integer idProyecto);
    Integer updateProyecto(ProyectoData proyectoData);
    Integer createProyecto(ProyectoData proyectoData);
}
