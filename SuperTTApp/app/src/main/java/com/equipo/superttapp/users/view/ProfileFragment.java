package com.equipo.superttapp.users.view;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.equipo.superttapp.R;
import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.users.presenter.ProfileViewModel;
import com.equipo.superttapp.util.BusinessResult;
import com.equipo.superttapp.util.PreferencesManager;
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
    @BindView(R.id.profile_et_current_password)
    EditText etCurrentPassword;
    @BindView(R.id.profile_et_nombre)
    EditText etName;
    @BindView(R.id.profile_et_apellidos)
    EditText etLastname;
    private PreferencesManager preferencesManager;
    ProfileViewModel profileViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        hideProgressBar();
        if (preferencesManager == null)
            preferencesManager = new PreferencesManager(getContext(),
                    PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        populateUserForm();
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        btnConfirmar.setOnClickListener(v -> showConfirmationDialog());
        return view;
    }

    public void actualizarPerfil() {
        cleanErrors();
        String token = "";
        UsuarioModel model = new UsuarioModel();
        model.setId(preferencesManager.getIntegerValue(PreferencesManager.KEY_USER_ID));
        model.setEmail(etEmail.getText().toString());
        model.setPassword(etPassword.getText().toString());
        model.setLastname(etLastname.getText().toString());
        model.setName(etName.getText().toString());
        model.setSecondPassword(etSecondPassword.getText().toString());
        model.setCurrentPassword(etCurrentPassword.getText().toString());
        profileViewModel.updateAccount(model, token).observe(this, usuarioModelBusinessResult -> {
            if(usuarioModelBusinessResult.getResult().equals(ResultCodes.SUCCESS)) {
                saveUser(usuarioModelBusinessResult.getResult());
            }
            showMessage(usuarioModelBusinessResult);
        });


        hidekeyboard();
    }

    private void populateUserForm() {
        if (preferencesManager == null)
            preferencesManager = new PreferencesManager(getContext(),
                    PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferencesManager.keyExists(PreferencesManager.KEY_USER_IS_LOGGED)
                && preferencesManager.getBooleanValue(PreferencesManager.KEY_USER_IS_LOGGED)) {
            String email = preferencesManager.getStringValue(PreferencesManager.KEY_USER_EMAIL);
            String nombre = preferencesManager.getStringValue(PreferencesManager.KEY_USER_NAME);
            String lastName = preferencesManager.getStringValue(PreferencesManager.KEY_USER_LAST_NAME);
            etEmail.setText(email);
            etLastname.setText(lastName);
            etName.setText(nombre);
        }
    }

    @Override
    public void cleanErrors() {
        etEmail.setError(null);
        etPassword.setError(null);
        etLastname.setError(null);
        etName.setError(null);
        etSecondPassword.setError(null);
    }

    @Override
    public void hidekeyboard() {
        etEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etLastname.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etName.onEditorAction(EditorInfo.IME_ACTION_DONE);
        etSecondPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
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
    public void showMessage(BusinessResult<UsuarioModel> result) {
        Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.cl_fragment_profile),
                R.string.msg10_operacion_fallida, Snackbar.LENGTH_LONG);
        if (result.getCode().equals(ResultCodes.SUCCESS))
            snackbar.setText(R.string.msg9_operacion_exitosa);
        else if (result.getCode().equals(ResultCodes.RN001)
                || result.getCode().equals(ResultCodes.RN002)) {
            snackbar.setText(R.string.msg1_datos_no_validos);
            if (!result.getResult().getValidEmail())
                etEmail.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().getValidPassword())
                etPassword.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().getValidSecondPassword())
                etSecondPassword.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().getValidName())
                etName.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().getValidLastName())
                etLastname.setError(getText(R.string.msg1_datos_no_validos));
            if (!result.getResult().getValidCurrentPassword())
                etCurrentPassword.setError(getText(R.string.msg1_datos_no_validos));
        }
        snackbar.show();
    }

    @Override
    public void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.label_confirmar_operacion)
                .setMessage(R.string.msg11_confirmacion_actualizar_perfil)
                .setPositiveButton(R.string.label_si, (dialog, which) -> actualizarPerfil())
                .setNegativeButton(R.string.label_no, (dialog, which) -> dialog.cancel());
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    @Override
    public void saveUser(UsuarioModel model) {
        PreferencesManager preferencesManager = new PreferencesManager(getContext(),
                PreferencesManager.PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferencesManager.saveValue(PreferencesManager.KEY_USER_NAME, model.getName());
        preferencesManager.saveValue(PreferencesManager.KEY_USER_LAST_NAME, model.getLastname());
        preferencesManager.saveValue(PreferencesManager.KEY_USER_EMAIL, model.getEmail());
    }
}
