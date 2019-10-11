package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.model.Proyecto;

import java.util.List;

public interface ProjectRepository {
    List<Proyecto> findAllProyectosByUser(Integer user);
}
