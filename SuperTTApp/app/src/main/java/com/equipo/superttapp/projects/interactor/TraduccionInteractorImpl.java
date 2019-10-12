package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.repository.TraduccionRepository;
import com.equipo.superttapp.projects.repository.TraduccionRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;

public class TraduccionInteractorImpl implements TraduccionInteractor{
    private TraduccionRepository repository;

    public TraduccionInteractorImpl() {
        repository = new TraduccionRepositoryImpl();
    }
    @Override
    public BusinessResult<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto) {
        BusinessResult<TraduccionModel> result = new BusinessResult<>();
        result.setResults(repository.findAllTraduccionesByProyecto(idProyecto));
        result.setCode(ResultCodes.SUCCESS);
        return result;
    }
}
