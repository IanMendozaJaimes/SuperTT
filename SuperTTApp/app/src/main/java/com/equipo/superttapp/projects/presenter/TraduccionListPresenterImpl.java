package com.equipo.superttapp.projects.presenter;

import com.equipo.superttapp.projects.interactor.ProyectoInteractor;
import com.equipo.superttapp.projects.interactor.ProyectoInteractorImpl;
import com.equipo.superttapp.projects.interactor.TraduccionInteractor;
import com.equipo.superttapp.projects.interactor.TraduccionInteractorImpl;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.view.TraduccionListView;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;

import java.util.List;

public class TraduccionListPresenterImpl implements TraduccionListPresenter {
    private TraduccionInteractor interactor;
    private ProyectoInteractor proyectoInteractor;
    private TraduccionListView view;

    public TraduccionListPresenterImpl(TraduccionListView view) {
        this.interactor = new TraduccionInteractorImpl();
        this.proyectoInteractor = new ProyectoInteractorImpl();
        this.view = view;
    }

    @Override
    public void changeProyectoNombre(ProyectoModel model) {
        BusinessResult<ProyectoModel> result = proyectoInteractor.updateProyecto(model);
        if (result.getCode().equals(ResultCodes.SUCCESS))
            view.changeProyectoSuccess(result);
        else {
            BusinessResult<TraduccionModel> error = new BusinessResult<>();
            error.setCode(result.getCode());
            view.showMessage(error);
        }

    }
}
