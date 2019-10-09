package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.model.Proyecto;

import java.util.List;

public interface ProjectListPresenter {
    List<Proyecto> findAllProyectosByUser(String user);
}
