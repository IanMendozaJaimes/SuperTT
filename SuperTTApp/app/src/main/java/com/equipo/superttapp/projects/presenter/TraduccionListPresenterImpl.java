package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.interactor.TraduccionInteractor;
import com.equipo.superttapp.projects.interactor.TraduccionInteractorImpl;
import com.equipo.superttapp.projects.model.Traduccion;
import com.equipo.superttapp.util.BusinessResult;

import java.util.List;

public class TraduccionListPresenterImpl implements TraduccionListPresenter {
    private TraduccionInteractor interactor;

    public TraduccionListPresenterImpl() {
        this.interactor = new TraduccionInteractorImpl();
    }

    @Override
    public List<Traduccion> findAllTraduccionesByProyecto(Integer idProyecto) {
        BusinessResult<Traduccion> result = interactor.findAllTraduccionesByProyecto(idProyecto);
        return null;
    }
}
