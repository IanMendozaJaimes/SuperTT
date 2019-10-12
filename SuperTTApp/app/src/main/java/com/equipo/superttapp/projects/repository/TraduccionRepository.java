package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.model.TraduccionModel;

import java.util.List;

public interface TraduccionRepository {
    List<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto);
}
