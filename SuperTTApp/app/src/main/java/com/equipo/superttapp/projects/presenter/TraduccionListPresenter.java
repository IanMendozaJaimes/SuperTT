package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.model.Traduccion;

import java.util.List;

public interface TraduccionListPresenter {
    List<Traduccion> findAllTraduccionesByProyecto(Integer idProyecto);
}
