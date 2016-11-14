package com.quicklistv_01.Class;

/**
 * Created by Aaron on 10/7/2016.
 */
public class Alumno {

    private Integer id;
    private String nombre;
    private Integer asistencia;
    private String curso;

    public Alumno(Integer id, Integer asistencia) {
        this.id = id;
        this.asistencia = asistencia;
    }

    public Alumno(Integer id, String nombre) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Integer asistencia) {
        this.asistencia = asistencia;
    }
}
