package com.equipo.superttapp.util;

import com.equipo.superttapp.projects.data.ProyectoData;
import com.equipo.superttapp.projects.data.TraduccionData;
import com.equipo.superttapp.users.data.UsuarioData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<ProyectoData> deleteProyecto(@Path("idProyecto") Integer idProyecto, @Header("Authorization") String key);
    // Obtiene las traducciones asociadas a un proyecto
    @GET("/proyectos/{idProyecto}/traducciones")
    Call<List<TraduccionData>> getTraduccionesByProyecto(@Path("idProyecto") Integer idProyecto, @Header("Authorization") String key);
    // Creacion de una traduccionModel
    @POST("/traducciones")
    Call<TraduccionData> createTraduccion(@Body TraduccionData traduccionData);
    // Edicion de un traduccionModel
    @PUT("/traducciones/{idTraducion}")
    Call<TraduccionData> editTraduccion(@Path("idTraducion") Integer idTraducion, @Body TraduccionData traduccionData);
    // Elimina una traduccion
    @DELETE("/traducciones/{idTraducion}")
    Call<TraduccionData> deleteTraduccion(@Path("idTraducion") Integer idTraducion, @Header("Authorization") String token);
    // Manda a crear un usuarioData
    @POST("/usuarios")
    Call<UsuarioData> createUsuario(@Body UsuarioData usuarioData);
    // Para hacer login
    @POST("/usuarios/login")
    Call<UsuarioData> loginUsuario(@Body UsuarioData usuarioData);
    // Para hacer la recuperacion de contra
    @POST("/usuarios/recuperar")
    Call<UsuarioData> recuperarUsuario(@Body UsuarioData usuarioData);
    // Para editar usuarioData
    @PUT("/usuarios/{idUsuario}")
    Call<UsuarioData> editUsuario(@Path("idUsuario") Integer id, @Body UsuarioData usuarioData);
    // Obtiene los proyectos asociados a un usuario
    @GET("/usuarios/{idUsuario}/proyectos")
    Call<List<ProyectoData>> getProyectosByUsuario(@Path("idUsuario") Integer idUsuario, @Header("Authorization") String key);
}
