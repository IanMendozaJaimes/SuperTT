package com.equipo.superttapp.projects.interactor;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.repository.ProjectRepository;
import com.equipo.superttapp.projects.repository.ProjectRepositoryImpl;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.RN002;
import com.equipo.superttapp.util.ResultCodes;

public class ProyectoInteractorImpl implements ProyectoInteractor {
    private ProjectRepository repository;

    public ProyectoInteractorImpl() {
        repository = new ProjectRepositoryImpl();
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
    public MutableLiveData<BusinessResult<ProyectoModel>> createProyecto(ProyectoModel model, String token) {
        ProyectoData proyectoData = new ProyectoData();
        MutableLiveData<BusinessResult<ProyectoModel>> data = new MutableLiveData<>();
        proyectoData.setNombre(model.getName());
        proyectoData.setIdUsuario(model.getIdUsuario());
        model.setValidName(RN002.isProyectoNombreValid(model.getName()));
        if (model.getValidName()) {
            data = repository.createProyecto(proyectoData, token);
        } else {
            BusinessResult<ProyectoModel> result = new BusinessResult<>();
            result.setCode(ResultCodes.RN002);
            data.setValue(result);
        }
        return data;
    }

    @Override
    public MutableLiveData<BusinessResult<ProyectoModel>> findAllProyectosByUser(int i, String s) {
        return repository.findAllProyectosByUser(i, s);
    }
}
