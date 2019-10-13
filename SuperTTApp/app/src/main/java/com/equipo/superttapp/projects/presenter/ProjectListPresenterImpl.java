package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.interactor.ProyectoInteractor;
import com.equipo.superttapp.projects.interactor.ProyectoInteractorImpl;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public class ProjectListPresenterImpl implements ProjectListPresenter {
    private ProyectoInteractor interactor;

    public ProjectListPresenterImpl() {
        this.interactor = new ProyectoInteractorImpl();
    }

    @Override
    public BusinessResult<ProyectoModel> findAllProyectosByUser(Integer user) {
        return interactor.findAllProyectosByUser(user);
    }
}
