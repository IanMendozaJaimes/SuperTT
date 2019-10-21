package com.equipo.superttapp.projects.interactor;

import androidx.lifecycle.MutableLiveData;

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
        List<ProyectoData> proyectos = new ArrayList<>();
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
    public MutableLiveData<BusinessResult<ProyectoModel>> deleteProyecto(Integer idProyecto, String token){
        MutableLiveData<BusinessResult<ProyectoModel>> data = new MutableLiveData<>();
        data = repository.deleteProyecto(idProyecto, token);
        return data;
    }

    @Override
    public MutableLiveData<BusinessResult<ProyectoModel>> updateProyecto(ProyectoModel model, String token) {
        ProyectoData proyectoData = new ProyectoData();
        MutableLiveData<BusinessResult<ProyectoModel>> data = new MutableLiveData<>();
        proyectoData.setCalificacion(model.getRate());
        proyectoData.setNombre(model.getName());
        proyectoData.setId(model.getId());
        proyectoData.setIdUsuario(model.getIdUsuario());
        model.setValidName(RN002.isProyectoNombreValid(model.getName()));
        if (model.getValidName()) {
            data = repository.updateProyecto(proyectoData, token);
        } else {
            BusinessResult<ProyectoModel> result = new BusinessResult<>();
            result.setCode(ResultCodes.RN002);
            data.setValue(result);
        }
        return data;
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

    @Override
    public MutableLiveData<BusinessResult<ProyectoModel>> findAllProyectosByUser(int i, String s) {
        return repository.findAllProyectosByUser(i, s);
    }
}
