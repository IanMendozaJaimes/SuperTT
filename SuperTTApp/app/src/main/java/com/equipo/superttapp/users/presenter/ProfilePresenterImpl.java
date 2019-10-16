package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.interactor.UserInteractor;
import com.equipo.superttapp.users.interactor.UserInteractorImpl;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.users.view.ProfileView;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;

public class ProfilePresenterImpl implements ProfilePresenter{
    private UserInteractor interactor;
    private ProfileView view;

    public ProfilePresenterImpl(ProfileView view) {
        this.view = view;
        this.interactor = new UserInteractorImpl();
    }

    @Override
    public void updateAccount(UsuarioModel model) {
        view.showProgressBar();
        BusinessResult<UsuarioModel> result = interactor.updateAccount(model);
        if (result.getCode().equals(ResultCodes.SUCCESS))
            view.saveUser(result.getResult());
        showMessage(result);
    }

    @Override
    public void showMessage(BusinessResult<UsuarioModel> result) {
        view.hideProgressBar();
        view.showMessage(result);
    }
}
