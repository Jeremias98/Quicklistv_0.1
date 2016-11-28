package com.quicklistv_01.Class;

public class AlumnoAsistencia {
    Integer id;
    String alumno, asistencia;

    public AlumnoAsistencia(Integer id, String alumno, String asistencia) {
        this.id = id;
        this.alumno = alumno;
        this.asistencia = asistencia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}
