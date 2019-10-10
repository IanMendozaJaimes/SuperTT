package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ForgotPasswordPresenter {
    void sendEmail(LoginFormModel model);
    void showMessage(BusinessResult<LoginFormModel> errorModel);

}
