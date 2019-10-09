package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.model.Proyecto;
import com.equipo.superttapp.util.BusinessResult;

import java.util.List;

public interface ProjectListPresenter {
    BusinessResult<Proyecto> findAllProyectosByUser(String user);
}
