package com.equipo.superttapp.util;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.model.TraduccionModel;
import com.equipo.superttapp.users.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    // Crear proyectoModel
    @POST("/proyectos")
    Call<ProyectoData> createProyecto(@Body ProyectoData proyectoModel);
    // Edicion de un proyectoModel
    @PUT("/proyectos/{idProyecto}")
    Call<ProyectoData> editProyecto(@Path("idProyecto") Integer idProyecto, @Body ProyectoData proyectoModel);
    // Elimina un proyecto
    @DELETE("/proyectos/{idProyecto}")
    Call<ProyectoData> deleteProyecto(@Path("idProyecto") Integer idProyecto);
    // Obtiene las traducciones asociadas a un proyecto
    @GET("/proyectos/{idProyecto}/traducciones")
    Call<List<TraduccionModel>> getTraduccionesByProyecto(@Path("idProyecto") Integer idProyecto);
    // Creacion de una traduccionModel
    @POST("/traducciones")
    Call<TraduccionModel> createTraduccion(@Body TraduccionModel traduccionModel);
    // Edicion de un traduccionModel
    @PUT("/traducciones/{idTraducion}")
    Call<TraduccionModel> editTraduccion(@Path("idTraducion") Integer idTraducion, @Body TraduccionModel traduccionModel);
    // Elimina una traduccion
    @DELETE("/traducciones/{idTraducion}")
    Call<TraduccionModel> deleteTraduccion(@Path("idTraducion") Integer idTraducion);
    // Manda a crear un usuario
    @POST("/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);
    // Para hacer login
    @POST("/usuarios/login")
    Call<Usuario> loginUsuario(@Body Usuario usuario);
    // Para hacer la recuperacion de contra
    @POST("/usuarios/recuperar")
    Call<Usuario> recuperarUsuario(@Body Usuario usuario);
    // Para editar usuario
    @PUT("/usuarios/{idUsuario}")
    Call<Usuario> editUsuario(@Path("idUsuario") Integer id, @Body Usuario usuario);
    // Obtiene los proyectos asociados a un usuario
    @GET("/usuarios/{idUsuario}/proyectos")
    Call<List<ProyectoData>> getProyectosByUsuario(@Path("idUsuario") Integer idUsuario);
}
