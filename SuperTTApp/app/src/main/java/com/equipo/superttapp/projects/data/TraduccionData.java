package com.equipo.superttapp.projects.data;

import com.google.gson.annotations.SerializedName;

public class TraduccionData {
    @SerializedName("id")
    private Integer id;
    @SerializedName("idProyecto")
    private Integer idProyecto;
    @SerializedName("nombre")
    private String fecha;
    @SerializedName("traduccion")
    private String ecuacion;
    @SerializedName("calificacion")
    private Double calificacion;
    @SerializedName("imgUrl")
    private String url;
    @SerializedName("resultCode")
    private Integer resultCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEcuacion() {
        return ecuacion;
    }

    public void setEcuacion(String ecuacion) {
        this.ecuacion = ecuacion;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }
}
