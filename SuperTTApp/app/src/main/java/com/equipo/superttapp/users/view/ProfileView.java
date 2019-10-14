package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ProfileView {
    void cleanErrors();
    void hidekeyboard();
    void showProgressBar();
    void hideProgressBar();
    void showMessage(BusinessResult<UsuarioModel> result);
    void showConfirmationDialog();
    void saveUser(UsuarioModel model);
}
