package com.equipo.superttapp.users.interactor;

import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.users.repository.UserRepository;
import com.equipo.superttapp.users.repository.UserRepositoryImpl;
import com.equipo.superttapp.util.RN002;
import com.equipo.superttapp.util.ResultCodes;

public class UserInteractorImpl implements UserInteractor {
    private UserRepository repository;

    public UserInteractorImpl() {
        repository = new UserRepositoryImpl();
    }
    @Override
    public LoginFormModel logIn(LoginFormModel loginFormModel) {
        Integer resultado = ResultCodes.ERROR;
        loginFormModel.setValidPassword(RN002.isPasswordValid(loginFormModel.getPassword()));
        loginFormModel.setValidEmail(RN002.isEmailValid(loginFormModel.getEmail()));
        if (loginFormModel.isValidEmail() && loginFormModel.isValidPassword())
            resultado = repository.login(loginFormModel.getEmail(), loginFormModel.getPassword());
        loginFormModel.setResultCode(resultado);
        return loginFormModel;
    }

    @Override
    public LoginFormModel sendEmail(LoginFormModel loginFormModel) {
        Integer resultado = ResultCodes.ERROR;
        loginFormModel.setValidEmail(RN002.isEmailValid(loginFormModel.getEmail()));
        if (loginFormModel.isValidEmail())
            resultado = repository.forgotPassword(loginFormModel.getEmail());
        loginFormModel.setResultCode(resultado);
        return loginFormModel;
    }
}
