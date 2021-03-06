package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface SignInView {
    void cleanErrors();
    void hideKeyboard();
    void showProgressBar();
    void hideProgressBar();
    void showMessage(BusinessResult<UsuarioModel> result);
}
