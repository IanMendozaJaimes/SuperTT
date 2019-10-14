package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

import java.util.List;

public interface TraduccionListPresenter {
    BusinessResult<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto);
}
