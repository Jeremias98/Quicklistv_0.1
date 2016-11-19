package com.quicklistv_01.Class;

/**
 * Created by Aaron on 18/11/2016.
 */

public class CursosFav {

    private String nombre;
    private Integer id;

    public CursosFav(String nombre) {
        this.nombre = nombre;
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
}
