package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.data.TraduccionData;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.projects.repository.TraduccionRepository;
import com.equipo.superttapp.projects.repository.TraduccionRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;

import java.util.ArrayList;
import java.util.List;

public class TraduccionInteractorImpl implements TraduccionInteractor{
    private TraduccionRepository repository;

    public TraduccionInteractorImpl() {
        repository = new TraduccionRepositoryImpl();
    }
    @Override
    public BusinessResult<TraduccionModel> findAllTraduccionesByProyecto(Integer idProyecto) {
        BusinessResult<TraduccionModel> result = new BusinessResult<>();
        List<TraduccionData> traduccionesData = repository.findAllTraduccionesByProyecto(idProyecto);
        List<TraduccionModel> traduccionesModel = new ArrayList<>();
        for (TraduccionData data : traduccionesData) {
            TraduccionModel model = new TraduccionModel();
            model.setCalificacion(data.getCalificacion());
            model.setEcuacion(data.getEcuacion());
            model.setId(data.getId());
            model.setIdProyecto(data.getIdProyecto());
            model.setUrl(data.getUrl());
            traduccionesModel.add(model);
        }
        if (traduccionesModel.size() > 0)
            result.setCode(ResultCodes.SUCCESS);
        result.setResults(traduccionesModel);

        return result;
    }

    @Override
    public BusinessResult<TraduccionModel> deleteTraduccion(Integer idTraduccion) {
        return null;
    }

    @Override
    public BusinessResult<TraduccionModel> updateTraduccion(TraduccionModel model) {
        return null;
    }
}
