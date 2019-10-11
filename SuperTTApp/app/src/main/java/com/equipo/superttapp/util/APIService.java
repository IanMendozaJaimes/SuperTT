package com.equipo.superttapp.util;

import com.equipo.superttapp.projects.model.Proyecto;
import com.equipo.superttapp.projects.model.Traduccion;
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
    // Crear proyecto
    @POST("/proyectos")
    Call<Proyecto> createProyecto(@Body Proyecto proyecto);
    // Edicion de un proyecto
    @PUT("/proyectos/{idProyecto}")
    Call<Traduccion> editProyecto(@Path("idProyecto") Integer idProyecto, @Body Proyecto proyecto);
    // Elimina un proyecto
    @DELETE("/proyectos/{idProyecto}")
    Call<Traduccion> deleteProyecto(@Path("idProyecto") Integer idProyecto);
    // Obtiene las traducciones asociadas a un proyecto
    @GET("/proyectos/{idProyecto}/traducciones")
    Call<List<Traduccion>> getTraduccionesByProyecto(@Path("idProyecto") Integer idProyecto);
    // Creacion de una traduccion
    @POST("/traducciones")
    Call<Traduccion> createTraduccion(@Body Traduccion traduccion);
    // Edicion de un traduccion
    @PUT("/traducciones/{idTraducion}")
    Call<Traduccion> editTraduccion(@Path("idTraducion") Integer idTraducion, @Body Traduccion traduccion);
    // Elimina una traduccion
    @DELETE("/traducciones/{idTraducion}")
    Call<Traduccion> deleteTraduccion(@Path("idTraducion") Integer idTraducion);
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
    Call<List<Proyecto>> getProyectosByUsuario(@Path("idUsuario") Integer idUsuario);
}
