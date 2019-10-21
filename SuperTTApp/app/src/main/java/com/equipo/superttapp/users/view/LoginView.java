package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface LoginView {
    void cleanErrors();
    void hideKeyboard();
    void goCreateAccount();
    void goForgotPassword();
    void hideProgressBar();
    void loginError(BusinessResult<UsuarioModel> resultado);
    void goHome();
    void saveUser(UsuarioModel model);

}
