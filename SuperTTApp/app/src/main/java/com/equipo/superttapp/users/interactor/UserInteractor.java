package com.equipo.superttapp.users.interactor;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface UserInteractor {
    MutableLiveData<BusinessResult<UsuarioModel>> logIn(UsuarioModel loginFormModel);
    BusinessResult<UsuarioModel> sendEmail(UsuarioModel loginFormModel);
    BusinessResult<UsuarioModel> createAccount(UsuarioModel signInFormModel);
    MutableLiveData<BusinessResult<UsuarioModel>> updateAccount(UsuarioModel model, String token);
}
