package com.equipo.superttapp.users.repository;

import androidx.lifecycle.MutableLiveData;

import com.equipo.superttapp.users.data.UsuarioData;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface UserRepository {
    MutableLiveData<BusinessResult<UsuarioModel>> login(UsuarioData usuarioData);
    Integer forgotPassword(UsuarioData usuarioData);
    Integer createAccount(UsuarioData usuarioData);
    MutableLiveData<BusinessResult<UsuarioModel>> updateAccount(UsuarioData usuarioData, String token);
}
