package com.equipo.superttapp.users.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<BusinessResult<UsuarioModel>> data = new MutableLiveData<>();
    private UserInteractor interactor = new UserInteractorImpl();

    public MutableLiveData<BusinessResult<UsuarioModel>> updateAccount(UsuarioModel model, String key) {
        data = interactor.updateAccount(model, key);
        return data;
    }
}
