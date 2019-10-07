package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.SignInFormModel;

public interface SignInPresenter {
    void signIn(SignInFormModel model);
    void showMessage(SignInFormModel result);
}
