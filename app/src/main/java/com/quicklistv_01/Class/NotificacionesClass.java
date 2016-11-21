package com.quicklistv_01.Class;

/**
 * Created by Aaron on 21/11/2016.
 */

public class NotificacionesClass {
    public Integer ID;
    public String nombre;
    public String curso_alumno;
    public String DNI;
    public String assist;
    public String tel;
    public String email;
    public String direccion;
    public String nacionalidad;

    public NotificacionesClass(Integer ID, String nombre, String curso_alumno, String DNI, String assist, String email, String tel, String direccion, String nacionalidad) {
        this.ID = ID;
        this.nombre = nombre;
        this.curso_alumno = curso_alumno;
        this.DNI = DNI;
        this.assist = assist;
        this.email = email;
        this.tel = tel;
        this.direccion = direccion;
        this.nacionalidad = nacionalidad;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCurso_alumno() {
        return curso_alumno;
    }

    public void setCurso_alumno(String curso_alumno) {
        this.curso_alumno = curso_alumno;
    }

    public String getAssist() {
        return assist;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
