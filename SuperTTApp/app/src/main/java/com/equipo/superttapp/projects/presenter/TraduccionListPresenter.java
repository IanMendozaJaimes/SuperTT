package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.model.TraduccionModel;

import java.util.List;

public interface TraduccionListPresenter {
    List<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto);
}
