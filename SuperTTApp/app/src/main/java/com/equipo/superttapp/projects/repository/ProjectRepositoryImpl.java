package com.equipo.superttapp.projects.repository;

import com.equipo.superttapp.projects.model.Proyecto;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRepository{
    @Override
    public List<Proyecto> findAllProyectosByUser(String user) {
        return new ArrayList<>();
    }
}
