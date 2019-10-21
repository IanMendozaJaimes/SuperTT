package com.equipo.superttapp.projects.presenter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.equipo.superttapp.projects.interactor.ProyectoInteractor;
import com.equipo.superttapp.projects.interactor.ProyectoInteractorImpl;
import com.equipo.superttapp.projects.interactor.TraduccionInteractor;
import com.equipo.superttapp.projects.interactor.TraduccionInteractorImpl;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

public class TraducccionListViewModel extends ViewModel {
    private MutableLiveData<BusinessResult<TraduccionModel>> data = new MutableLiveData<>();
    private MutableLiveData<BusinessResult<ProyectoModel>> dataProyecto = new MutableLiveData<>();
    private TraduccionInteractor interactor = new TraduccionInteractorImpl();
    private ProyectoInteractor proyectoInteractor = new ProyectoInteractorImpl();

    public MutableLiveData<BusinessResult<TraduccionModel>> findTraducciones(Integer id, String key) {
        data = interactor.findAllTraduccionesByProyecto(id, key);
        return data;
    }

    public MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer id, String key) {
        data = interactor.deleteTraduccion(id, key);
        return data;
    }

    public MutableLiveData<BusinessResult<ProyectoModel>> deleteProyecto(Integer idProyecto, String key) {
        dataProyecto = proyectoInteractor.deleteProyecto(idProyecto, key);
        return dataProyecto;
    }

    public void setData(MutableLiveData<BusinessResult<TraduccionModel>> data) {
        this.data = data;
    }
}
