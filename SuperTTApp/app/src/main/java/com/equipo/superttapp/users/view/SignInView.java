package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.SignInFormModel;

public interface SignInView {
    void showProgressBar();
    void hideProgressBar();
    void showMessage(SignInFormModel result);
}
