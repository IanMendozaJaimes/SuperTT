package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.util.BusinessResult;

public interface LoginView {
    void cleanErrors();
    void hideKeyboard();
    void goCreateAccount();
    void goForgotPassword();
    void showProgressBar();
    void hideProgressBar();
    void loginError(BusinessResult<LoginFormModel> resultado);
    void goHome();
    void saveUser(LoginFormModel model);

}
