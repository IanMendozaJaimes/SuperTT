package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.model.Traduccion;

import java.util.List;

public interface TraduccionRepository {
    List<Traduccion> findAllTraduccionesByProyecto(Integer idProyecto);
}
