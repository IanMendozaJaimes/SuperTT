package com.equipo.superttapp.users.interactor;

import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.model.LoginFormModel;

public interface UserInteractor {
    LoginFormModel logIn(LoginFormModel loginFormModel);
    LoginFormModel sendEmail(LoginFormModel loginFormModel);
    SignInFormModel createAccount(SignInFormModel signInFormModel);
}
