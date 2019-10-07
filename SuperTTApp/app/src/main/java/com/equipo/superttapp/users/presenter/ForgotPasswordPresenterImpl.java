package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.users.view.ForgotPasswordView;

public class ForgotPasswordPresenterImpl implements ForgotPasswordPresenter{
    private UserInteractor interactor;
    private ForgotPasswordView view;

    public ForgotPasswordPresenterImpl(ForgotPasswordView loginView) {
        this.view = loginView;
        this.interactor = new UserInteractorImpl();
    }
    @Override
    public void sendEmail(LoginFormModel model) {
        view.showProgressBar();
        model = interactor.sendEmail(model);
        showMessage(model);
    }

    @Override
    public void showMessage(LoginFormModel errorModel) {
        view.hideProgressBar();
        view.showMessage(errorModel);
    }
}
