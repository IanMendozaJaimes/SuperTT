package com.equipo.superttapp.projects.interactor;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.repository.TraduccionRepository;
import com.equipo.superttapp.projects.repository.TraduccionRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;

public class TraduccionInteractorImpl implements TraduccionInteractor{
    private TraduccionRepository repository;

    public TraduccionInteractorImpl() {
        repository = new TraduccionRepositoryImpl();
    }
    @Override
    public MutableLiveData<BusinessResult<TraduccionModel>> findAllTraduccionesByProyecto(Integer idProyecto, String key) {
        return repository.findAllTraduccionesByProyecto(idProyecto, key);
    }

    @Override
    public MutableLiveData<BusinessResult<TraduccionModel>> deleteTraduccion(Integer idTraduccion, String token) {
        MutableLiveData<BusinessResult<TraduccionModel>> data = new MutableLiveData<>();
        data = repository.deleteTraduccion(idTraduccion, token);
        return data;
    }

}
