package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.data.TraduccionData;
import com.equipo.superttapp.projects.model.TraduccionModel;

import java.util.List;

public interface TraduccionRepository {
    List<TraduccionData> findAllTraduccionesByProyecto(Integer idProyecto);
    Integer deleteTraduccion(Integer idTraduccion);
    Integer updateTraduccion(TraduccionData data);
}
