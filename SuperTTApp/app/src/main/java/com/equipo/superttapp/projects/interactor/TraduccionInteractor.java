package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

public interface TraduccionInteractor {
    BusinessResult<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto);
    BusinessResult<TraduccionModel> deleteTraduccion(Integer idTraduccion);
    BusinessResult<TraduccionModel> updateTraduccion(TraduccionModel model);
}
