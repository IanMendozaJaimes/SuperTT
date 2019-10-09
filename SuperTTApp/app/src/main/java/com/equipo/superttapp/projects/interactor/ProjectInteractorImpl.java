package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.Proyecto;
import com.equipo.superttapp.projects.repository.ProjectRepository;
import com.equipo.superttapp.projects.repository.ProjectRepositoryImpl;
import com.equipo.superttapp.util.ResultCodes;

import java.util.List;

public class ProjectInteractorImpl implements ProjectInteractor {
    private ProjectRepository repository;

    public ProjectInteractorImpl() {
        repository = new ProjectRepositoryImpl();
    }

    @Override
    public List<Proyecto> findAllProyectosByUser(String user) {
        Integer resultado = ResultCodes.ERROR;
        return null;
    }
}
