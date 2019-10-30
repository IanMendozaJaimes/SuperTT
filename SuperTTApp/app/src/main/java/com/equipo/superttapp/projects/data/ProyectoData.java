package com.equipo.superttapp.projects.data;

import com.google.gson.annotations.SerializedName;

public class ProyectoData {
    @SerializedName("id")
    private Integer id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("fechaModificacion")
    private String fecha;
    @SerializedName("calificacion")
    private Double calificacion;
    @SerializedName("usuario")
    private Integer idUsuario;
    @SerializedName("resultCode")
    private Integer resultCode;

    public ProyectoData() {
    }

    public ProyectoData(Integer id, String nombre, String fecha, Double calificacion, Integer idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.idUsuario = idUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }
}
