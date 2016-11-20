package com.quicklistv_01.Class;

/**
 * Created by Aaron on 18/11/2016.
 */

public class CursosFav {

    private Integer id;
    private String nombre;
    private boolean favorito;

    public CursosFav(Integer id, String nombre, Boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.favorito = favorito;
    }

    public CursosFav(Integer id, String nombre) {
        this.id = id;
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

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}
