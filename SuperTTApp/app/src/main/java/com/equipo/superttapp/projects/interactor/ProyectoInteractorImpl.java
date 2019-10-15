package com.equipo.superttapp.projects.interactor;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.repository.ProjectRepository;
import com.equipo.superttapp.projects.repository.ProjectRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.RN002;
import com.equipo.superttapp.util.ResultCodes;

import java.util.ArrayList;
import java.util.List;

public class ProyectoInteractorImpl implements ProyectoInteractor {
    private ProjectRepository repository;

    public ProyectoInteractorImpl() {
        repository = new ProjectRepositoryImpl();
    }

    @Override
    public BusinessResult<ProyectoModel> findAllProyectosByUser(Integer user) {
        BusinessResult<ProyectoModel> resultado = new BusinessResult<>();
        List<ProyectoData> proyectos = repository.findAllProyectosByUser(user);
        List<ProyectoModel> proyectoModels = new ArrayList<>();
        for (ProyectoData proyecto : proyectos) {
            ProyectoModel proyectoModel = new ProyectoModel();
            proyectoModel.setId(proyecto.getId());
            proyectoModel.setName(proyecto.getNombre());
            proyectoModel.setRate(proyecto.getCalificacion());
            proyectoModels.add(proyectoModel);
        }
        if (proyectoModels.size() > 0) {
            resultado.setCode(ResultCodes.SUCCESS);
        }
        resultado.setResults(proyectoModels);
        return resultado;
    }

    @Override
    public BusinessResult<ProyectoModel> deleteProyecto(Integer idProyecto) {
        BusinessResult<ProyectoModel> result = new BusinessResult<>();
        result.setCode(repository.deleteProyecto(idProyecto));
        return result;
    }

    @Override
    public BusinessResult<ProyectoModel> updateProyecto(ProyectoModel model) {
        ProyectoData proyectoData = new ProyectoData();
        BusinessResult<ProyectoModel> result = new BusinessResult<>();
        //proyectoData.setCalificacion(model.getRate());
        //proyectoData.setFecha(model.getTextDate());
        proyectoData.setId(model.getId());
        model.setValidName(RN002.isProyectoNombreValid(model.getName()));
        if (model.getValidName()) {
            proyectoData.setNombre(model.getName());
            result.setCode(repository.updateProyecto(proyectoData));
        } else {
            result.setCode(ResultCodes.RN002);
        }

        return result;
    }

    @Override
    public BusinessResult<ProyectoModel> createProyecto(ProyectoModel model, Integer idUsuario) {
        ProyectoData proyectoData = new ProyectoData();
        BusinessResult<ProyectoModel> result = new BusinessResult<>();
        proyectoData.setCalificacion(model.getRate());
        proyectoData.setFecha(model.getTextDate());
        proyectoData.setId(model.getId());
        proyectoData.setNombre(model.getName());
        proyectoData.setIdUsuario(idUsuario);
        result.setCode(repository.createProyecto(proyectoData));
        return result;
    }
}
