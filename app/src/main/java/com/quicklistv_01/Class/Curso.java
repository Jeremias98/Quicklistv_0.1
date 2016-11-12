package com.quicklistv_01.Class;

/**
 * Created by Aaron on 3/7/2016.
 */
public class Curso {

    private Integer id;
    private String nombre;
    private boolean favorito;

    public Curso(Integer id, String nombre, Boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.favorito = favorito;
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

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}
