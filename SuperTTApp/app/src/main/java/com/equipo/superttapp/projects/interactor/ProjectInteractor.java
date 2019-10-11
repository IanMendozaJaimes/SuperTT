package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.Proyecto;
import com.equipo.superttapp.util.BusinessResult;

import java.util.List;

public interface ProjectInteractor {
    BusinessResult<Proyecto> findAllProyectosByUser(Integer user);
}
