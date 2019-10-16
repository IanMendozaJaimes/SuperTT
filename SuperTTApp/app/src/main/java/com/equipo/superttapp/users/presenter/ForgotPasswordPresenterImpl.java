package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.users.view.ForgotPasswordView;
import com.equipo.superttapp.util.BusinessResult;

public class ForgotPasswordPresenterImpl implements ForgotPasswordPresenter{
    private UserInteractor interactor;
    private ForgotPasswordView view;

    public ForgotPasswordPresenterImpl(ForgotPasswordView loginView) {
        this.view = loginView;
        this.interactor = new UserInteractorImpl();
    }
    @Override
    public void sendEmail(UsuarioModel model) {
        view.showProgressBar();
        BusinessResult<UsuarioModel> result = interactor.sendEmail(model);
        showMessage(result);
    }

    @Override
    public void showMessage(BusinessResult<UsuarioModel> errorModel) {
        view.hideProgressBar();
        view.showMessage(errorModel);
    }
}
