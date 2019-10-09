package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.Proyecto;

import java.util.List;

public interface ProjectInteractor {
    List<Proyecto> findAllProyectosByUser(String user);
}
