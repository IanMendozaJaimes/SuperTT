package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.interactor.ProjectInteractor;
import com.equipo.superttapp.projects.interactor.ProjectInteractorImpl;
import com.equipo.superttapp.projects.model.Proyecto;

import java.util.List;

public class ProjectListPresenterImpl implements ProjectListPresenter {
    private ProjectInteractor interactor;

    public ProjectListPresenterImpl() {
        this.interactor = new ProjectInteractorImpl();
    }

    @Override
    public List<Proyecto> findAllProyectosByUser(String user) {

        return interactor.findAllProyectosByUser(user);
    }
}
