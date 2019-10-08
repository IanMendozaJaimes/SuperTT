package com.equipo.superttapp.users.interactor;

import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.users.model.SignInFormModel;
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

    @Override
    public SignInFormModel createAccount(SignInFormModel model) {
        Integer resultado = ResultCodes.ERROR;
        model.setValidPassword(RN002.isPasswordValid(model.getPassword()));
        model.setValidEmail(RN002.isEmailValid(model.getEmail()));
        model.setValidSecondPassword(RN002.isSecondPasswordValid(
                model.getPassword(), model.getSecondPassword()));
        model.setValidName(RN002.isNameValid(model.getName()));
        model.setValidLastName(RN002.isLastnameValid(model.getLastname()));
        if (model.isValidPassword() && model.isValidEmail() && model.isValidName()
                && model.isValidSecondPassword() && model.isValidLastName()) {
            resultado = repository.createAccount(model.getEmail(), model.getPassword(),
                    model.getName(), model.getLastname());
        }
        model.setResultCode(resultado);
        return model;
    }

    @Override
    public SignInFormModel updateAccount(SignInFormModel model) {
        Integer resultado = ResultCodes.ERROR;
        model.setValidPassword(RN002.isPasswordValid(model.getPassword()));
        model.setValidEmail(RN002.isEmailValid(model.getEmail()));
        model.setValidSecondPassword(RN002.isSecondPasswordValid(
                model.getPassword(), model.getSecondPassword()));
        model.setValidName(RN002.isNameValid(model.getName()));
        model.setValidLastName(RN002.isLastnameValid(model.getLastname()));
        if (model.isValidPassword() && model.isValidEmail() && model.isValidName()
                && model.isValidSecondPassword() && model.isValidLastName()) {
            resultado = repository.updateAccount(model.getEmail(), model.getPassword(),
                    model.getName(), model.getLastname());
        }
        model.setResultCode(resultado);
        return model;
    }
}
