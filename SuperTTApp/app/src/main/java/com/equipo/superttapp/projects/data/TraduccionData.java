package com.equipo.superttapp.projects.data;

public class TraduccionData {
    private Integer id;
    private Integer idProyecto;
    private String fecha;
    private String ecuacion;
    private Integer calificacion;
    private String url;

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

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
