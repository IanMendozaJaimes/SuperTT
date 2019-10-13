package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.model.TraduccionModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;

public class TraduccionRepositoryImpl implements TraduccionRepository{
    @Override
    @GET(value="/proyectos/{id}")
    public List<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto) {

        return new ArrayList<>();
    }
}
