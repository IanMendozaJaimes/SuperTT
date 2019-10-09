package com.equipo.superttapp.users.interactor;

import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.util.BusinessResult;

public interface UserInteractor {
    BusinessResult<LoginFormModel> logIn(LoginFormModel loginFormModel);
    BusinessResult<LoginFormModel> sendEmail(LoginFormModel loginFormModel);
    BusinessResult<SignInFormModel> createAccount(SignInFormModel signInFormModel);
    BusinessResult<SignInFormModel>  updateAccount(SignInFormModel model);
}
