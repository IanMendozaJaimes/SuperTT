package com.equipo.superttapp.users.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.equipo.superttapp.R;
import com.equipo.superttapp.users.model.SignInFormModel;
import com.equipo.superttapp.users.presenter.ProfilePresenter;
import com.equipo.superttapp.users.presenter.ProfilePresenterImpl;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.ResultCodes;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileView {
    private static final String TAG = ProfileFragment.class.getCanonicalName();
    @BindView(R.id.profile_btn_guardar)
    Button btnConfirmar;
    @BindView(R.id.pb_profile)
    ProgressBar progressBar;
    @BindView(R.id.profile_et_email)
    EditText etEmail;
    @BindView(R.id.profile_et_contra)
    EditText etPassword;
    @BindView(R.id.profile_et_confirmar)
    EditText etSecondPassword;
    @BindView(R.id.profile_et_nombre)
    EditText etName;
    @BindView(R.id.profile_et_apellidos)
    EditText etLastname;
    private ProfilePresenter presenter;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        presenter = new ProfilePresenterImpl(this);
        hideProgressBar();
        btnConfirmar.setOnClickListener(v -> {
            SignInFormModel model = new SignInFormModel();
            etEmail.setError(null);
            etPassword.setError(null);
            etLastname.setError(null);
            etName.setError(null);
            etSecondPassword.setError(null);
            model.setEmail(etEmail.getText().toString());
            model.setPassword(etPassword.getText().toString());
            model.setLastname(etLastname.getText().toString());
            model.setName(etName.getText().toString());
            model.setSecondPassword(etSecondPassword.getText().toString());
            presenter.updateAccount(model);
        });
        return view;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(BusinessResult<SignInFormModel> result) {
        Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.cl_fragment_profile),
                R.string.msg1_datos_no_validos, Snackbar.LENGTH_LONG);
        if (result.getCode().equals(ResultCodes.RN003))
            snackbar.setText(R.string.msg4_correo_electronico_ya_registrado);
        else if (result.getCode().equals(ResultCodes.SUCCESS))
            snackbar.setText(R.string.msg9_operacion_exitosa);
        else {
            if (!result.getResult().isValidEmail())
                etEmail.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidPassword())
                etPassword.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidSecondPassword())
                etSecondPassword.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidName())
                etName.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().isValidLastName())
                etLastname.setError(getText(R.string.msg1_datos_no_validos));
        }
        snackbar.show();
    }
}
