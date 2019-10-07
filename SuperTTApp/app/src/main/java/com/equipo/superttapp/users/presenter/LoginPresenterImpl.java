package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.LoginInteractor;
import com.equipo.superttapp.users.interactor.LoginInteractorImpl;
import com.equipo.superttapp.users.model.LoginForm;
import com.equipo.superttapp.users.view.LoginView;
import com.equipo.superttapp.util.ResultCodes;

public class LoginPresenterImpl implements LoginPresenter{
    private LoginInteractor interactor;
    private LoginView view;

    public LoginPresenterImpl(LoginView loginView) {
        this.view = loginView;
        this.interactor = new LoginInteractorImpl();
    }
    @Override
    public void logIn(LoginForm loginForm) {
        view.showProgressBar();
        loginForm = interactor.logIn(loginForm);
        if (loginForm.getResultCode().equals(ResultCodes.SUCCESS))
            logInSuccess();
        else
            logInError(loginForm);
    }

    @Override
    public void logInSuccess() {
        view.hideProgressBar();
        view.goHome();
    }

    @Override
    public void logInError(LoginForm error) {
        view.hideProgressBar();
        view.loginError(error);
    }
}
