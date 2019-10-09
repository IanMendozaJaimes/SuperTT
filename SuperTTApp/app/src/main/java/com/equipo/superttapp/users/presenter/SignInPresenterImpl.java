package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.view.SignInView;
import com.equipo.superttapp.util.BusinessResult;

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
        BusinessResult<SignInFormModel> result = interactor.createAccount(model);
        showMessage(result);
    }

    @Override
    public void showMessage(BusinessResult<SignInFormModel> result) {
        view.hideProgressBar();
        view.showMessage(result);
    }
}
