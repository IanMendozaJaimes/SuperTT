package com.equipo.superttapp.users.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public class SignInViewModel extends ViewModel {
    private MutableLiveData<BusinessResult<UsuarioModel>> data = new MutableLiveData<>();
    private UserInteractor interactor = new UserInteractorImpl();

    public MutableLiveData<BusinessResult<UsuarioModel>> createAccount(UsuarioModel model) {
        data = interactor.createAccount(model);
        return data;
    }
}