package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.Traduccion;
import com.equipo.superttapp.util.BusinessResult;

public interface TraduccionInteractor {
    BusinessResult<Traduccion> findAllTraduccionesByProyecto(Integer idProyecto);
}
