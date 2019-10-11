package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.Proyecto;
import com.equipo.superttapp.projects.repository.ProjectRepository;
import com.equipo.superttapp.projects.repository.ProjectRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;

public class ProjectInteractorImpl implements ProjectInteractor {
    private ProjectRepository repository;

    public ProjectInteractorImpl() {
        repository = new ProjectRepositoryImpl();
    }

    @Override
    public BusinessResult<Proyecto> findAllProyectosByUser(Integer user) {
        BusinessResult<Proyecto> resultado = new BusinessResult<>();
        resultado.setResults(repository.findAllProyectosByUser(user));
        return resultado;
    }
}
