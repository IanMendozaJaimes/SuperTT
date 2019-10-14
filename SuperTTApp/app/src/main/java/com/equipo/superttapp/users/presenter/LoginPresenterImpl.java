package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.users.view.LoginView;
import com.equipo.superttapp.util.BusinessResult;

public class LoginPresenterImpl implements LoginPresenter{
    private UserInteractor interactor;
    private LoginView view;

    public LoginPresenterImpl(LoginView loginView) {
        this.view = loginView;
        this.interactor = new UserInteractorImpl();
    }

    @Override
    public void logIn(UsuarioModel loginFormModel) {
        view.showProgressBar();
        BusinessResult<UsuarioModel> resultado = interactor.logIn(loginFormModel);
        //if (resultado.getCode().equals(ResultCodes.SUCCESS))
            logInSuccess(resultado);
       // else
        //    logInError(resultado);
    }

    @Override
    public void logInSuccess(BusinessResult<UsuarioModel> resultado) {
        view.hideProgressBar();
        view.saveUser(resultado.getResult());
        view.goHome();
    }

    @Override
    public void logInError(BusinessResult<UsuarioModel> resultado) {
        view.hideProgressBar();
        view.loginError(resultado);
    }
}
