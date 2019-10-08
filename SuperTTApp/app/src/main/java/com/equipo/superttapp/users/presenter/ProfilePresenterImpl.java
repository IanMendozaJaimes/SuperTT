package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.view.ProfileView;

public class ProfilePresenterImpl implements ProfilePresenter{
    private UserInteractor interactor;
    private ProfileView view;

    public ProfilePresenterImpl(ProfileView view) {
        this.view = view;
        this.interactor = new UserInteractorImpl();
    }

    @Override
    public void updateAccount(SignInFormModel model) {
        view.showProgressBar();
        model = interactor.updateAccount(model);
        showMessage(model);
    }

    @Override
    public void showMessage(SignInFormModel result) {
        view.hideProgressBar();
        view.showMessage(result);
    }
}
