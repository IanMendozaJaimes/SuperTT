package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

import java.util.List;

public interface TraduccionListPresenter {
    BusinessResult<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto);
    void deleteProyecto(Integer idProyecto);
    void deleteTraduccion(Integer idTraduccion);
    void calificarTraduccion(TraduccionModel model);
    void changeProyectoNombre(ProyectoModel model);
}
