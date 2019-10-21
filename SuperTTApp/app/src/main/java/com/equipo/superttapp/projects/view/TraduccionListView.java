package com.equipo.superttapp.projects.view;

import com.equipo.superttapp.projects.model.ProyectoModel;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.util.BusinessResult;

public interface TraduccionListView {
    void showMessage(BusinessResult<TraduccionModel> result);
    void showDeleteDialog();
    void showEditDialog();
    void borrarTraduccion(Integer idTraduccion);
    void deleteProyectoSuccess();
    void deleteProyectoError();
    void changeProyectoSuccess(BusinessResult<ProyectoModel> result);
}
