package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.SignInFormModel;

public interface ProfilePresenter {
    void updateAccount(SignInFormModel model);
    void showMessage(SignInFormModel result);
}
