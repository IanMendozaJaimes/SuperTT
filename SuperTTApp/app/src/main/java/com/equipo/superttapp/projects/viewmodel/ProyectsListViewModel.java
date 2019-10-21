package com.equipo.superttapp.projects.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.equipo.superttapp.projects.interactor.ProyectoInteractor;
import com.equipo.superttapp.projects.interactor.ProyectoInteractorImpl;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public class ProyectsListViewModel extends ViewModel {
    private  MutableLiveData<BusinessResult<ProyectoModel>> data = new MutableLiveData<>();
    private ProyectoInteractor interactor = new ProyectoInteractorImpl();

    public MutableLiveData<BusinessResult<ProyectoModel>> findUserProyects(Integer id, String key) {
        data = interactor.findAllProyectosByUser(id, key);
        return data;
    }

    public void setData(MutableLiveData<BusinessResult<ProyectoModel>> data) {
        this.data = data;
    }
}
