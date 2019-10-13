package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ProyectoInteractor {
    BusinessResult<ProyectoModel> findAllProyectosByUser(Integer user);
    BusinessResult<ProyectoModel> deleteProyecto(Integer idProyecto);
    BusinessResult<ProyectoModel> updateProyecto(ProyectoModel model);
    BusinessResult<ProyectoModel> createProyecto(ProyectoModel model, Integer idUsuario);
}
