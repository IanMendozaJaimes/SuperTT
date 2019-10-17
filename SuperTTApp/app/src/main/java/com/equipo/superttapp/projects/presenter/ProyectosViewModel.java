package com.equipo.superttapp.projects.presenter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.repository.ProjectRepository;
import com.equipo.superttapp.projects.repository.ProjectRepositoryImpl;

import java.util.List;

public class ProyectosViewModel extends ViewModel {
    private  MutableLiveData<List<ProyectoData>> data = new MutableLiveData<>();
    private ProjectRepository repository = new ProjectRepositoryImpl();

    public MutableLiveData<List<ProyectoData>> getData() {
        data = repository.findAllProyectosByUser(9);
        return data;
    }

    public void setData(MutableLiveData<List<ProyectoData>> data) {
        this.data = data;
    }
}
