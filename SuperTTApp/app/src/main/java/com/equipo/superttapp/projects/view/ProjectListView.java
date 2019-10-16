package com.equipo.superttapp.projects.view;

import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ProjectListView {
    void showMessage(BusinessResult<ProyectoModel> result);
}
