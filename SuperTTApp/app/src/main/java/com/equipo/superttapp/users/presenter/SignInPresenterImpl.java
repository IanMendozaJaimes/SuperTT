package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.view.SignInView;

public class SignInPresenterImpl implements SignInPresenter {
    private UserInteractor interactor;
    private SignInView view;

    public SignInPresenterImpl(SignInView loginView) {
        this.view = loginView;
        this.interactor = new UserInteractorImpl();
    }
    @Override
    public void signIn(SignInFormModel model) {
        view.showProgressBar();
        model = interactor.createAccount(model);
        showMessage(model);
    }

    @Override
    public void showMessage(SignInFormModel result) {
        view.hideProgressBar();
        view.showMessage(result);
    }
}
