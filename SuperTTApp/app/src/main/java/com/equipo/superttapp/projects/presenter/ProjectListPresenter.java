package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ProjectListPresenter {
    BusinessResult<ProyectoModel> findAllProyectosByUser(Integer user);
}
