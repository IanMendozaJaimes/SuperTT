package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ForgotPasswordView {
    void showMessage(BusinessResult<LoginFormModel> model);
    void hideProgressBar();

    void showProgressBar();
}
