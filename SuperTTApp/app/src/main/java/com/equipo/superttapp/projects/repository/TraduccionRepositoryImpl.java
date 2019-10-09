package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.model.Traduccion;

import java.util.ArrayList;
import java.util.List;

public class TraduccionRepositoryImpl implements TraduccionRepository{
    @Override
    public List<Traduccion> findAllTraduccionesByProyecto(Integer idProyecto) {
        return new ArrayList<>();
    }
}
