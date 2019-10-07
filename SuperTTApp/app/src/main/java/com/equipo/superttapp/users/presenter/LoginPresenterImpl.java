package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.users.view.LoginView;
import com.equipo.superttapp.util.ResultCodes;

public class LoginPresenterImpl implements LoginPresenter{
    private UserInteractor interactor;
    private LoginView view;

    public LoginPresenterImpl(LoginView loginView) {
        this.view = loginView;
        this.interactor = new UserInteractorImpl();
    }

    @Override
    public void logIn(LoginFormModel loginFormModel) {
        view.showProgressBar();
        loginFormModel = interactor.logIn(loginFormModel);
        if (loginFormModel.getResultCode().equals(ResultCodes.SUCCESS))
            logInSuccess();
        else
            logInError(loginFormModel);
    }

    @Override
    public void logInSuccess() {
        view.hideProgressBar();
        view.goHome();
    }

    @Override
    public void logInError(LoginFormModel error) {
        view.hideProgressBar();
        view.loginError(error);
    }
}
