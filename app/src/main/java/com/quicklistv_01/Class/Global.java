package com.quicklistv_01.Class;

import java.util.ArrayList;

/**
 * Created by Jeremías on 31/10/2016.
 */
public class Global extends AppController {

    // Sesion
    private Integer userID;
    private String userName;
    private String url;
    private Integer idCurrentGrupo;
    private String nameCurrentGrupo;

    // Lista de cursos
    private ArrayList<Integer> idGrupos;
    private ArrayList<String> nameGrupos;

    // Era para los favoritos
    private ArrayList<Integer> idGruposSinAdmin;
    private ArrayList<String> nameGruposSinAdmin;

    // Alumnos' stuff
    private ArrayList<Integer> idAlumnosEnGrupo;
    private ArrayList<String> nameAlumnosEnGrupo;
    private ArrayList<String> dniAlumnosEnGrupo;
    private ArrayList<String> telefonoAlumnosEnGrupo;
    private ArrayList<String> celularAlumnosEnGrupo;
    private ArrayList<String> emailAlumnosEnGrupo;
    private ArrayList<String> sexoAlumnosEnGrupo;
    private ArrayList<String> nacionalidadAlumnosEnGrupo;
    private ArrayList<String> direccionAlumnosEnGrupo;

    // Ausentes con tres faltas seguidas
    private ArrayList<Integer> idAlumnosAusentesRecurrentes;
    private ArrayList<String> nombreAlumnosAusentesRecurrentes;
    private ArrayList<String> telefonoAlumnosAusentesRecurrentes;
    private ArrayList<String> celularAlumnosAusentesRecurrentes;
    private ArrayList<String> emailAlumnosAusentesRecurrentes;

    private boolean agregado = false;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public ArrayList<Integer> getIdGrupos() { return idGrupos; }

    public void setIdGrupos(ArrayList<Integer> idGrupos) { this.idGrupos = idGrupos; }

    public ArrayList<String> getNameGrupos() { return nameGrupos; }

    public void setNameGrupos(ArrayList<String> nameGrupos) { this.nameGrupos = nameGrupos; }

    public ArrayList<Integer> getIdGruposSinAdmin() {
        return idGruposSinAdmin;
    }

    public void setIdGruposSinAdmin(ArrayList<Integer> idGruposSinAdmin) {
        this.idGruposSinAdmin = idGruposSinAdmin;
    }

    public ArrayList<String> getNameGruposSinAdmin() {
        return nameGruposSinAdmin;
    }

    public void setNameGruposSinAdmin(ArrayList<String> nameGruposSinAdmin) {
        this.nameGruposSinAdmin = nameGruposSinAdmin;
    }

    public ArrayList<Integer> getIdAlumnosEnGrupo() {
        return idAlumnosEnGrupo;
    }

    public void setIdAlumnosEnGrupo(ArrayList<Integer> idAlumnosEnGrupo) {
        this.idAlumnosEnGrupo = idAlumnosEnGrupo;
    }

    public ArrayList<String> getNameAlumnosEnGrupo() {
        return nameAlumnosEnGrupo;
    }

    public void setNameAlumnosEnGrupo(ArrayList<String> nameAlumnosEnGrupo) {
        this.nameAlumnosEnGrupo = nameAlumnosEnGrupo;
    }

    public boolean isAgregado() {
        return agregado;
    }

    public void setAgregado(boolean agregado) {
        this.agregado = agregado;
    }

    public Integer getIdCurrentGrupo() {
        return idCurrentGrupo;
    }

    public void setIdCurrentGrupo(Integer idCurrentGrupo) {
        this.idCurrentGrupo = idCurrentGrupo;
    }

    public String getNameCurrentGrupo() {
        return nameCurrentGrupo;
    }

    public void setNameCurrentGrupo(String nameCurrentGrupo) {
        this.nameCurrentGrupo = nameCurrentGrupo;
    }

    public ArrayList<Integer> getIdAlumnosAusentesRecurrentes() {
        return idAlumnosAusentesRecurrentes;
    }

    public void setIdAlumnosAusentesRecurrentes(ArrayList<Integer> idAlumnosAusentesRecurrentes) {
        this.idAlumnosAusentesRecurrentes = idAlumnosAusentesRecurrentes;
    }

    public ArrayList<String> getNombreAlumnosAusentesRecurrentes() {
        return nombreAlumnosAusentesRecurrentes;
    }

    public void setNombreAlumnosAusentesRecurrentes(ArrayList<String> nombreAlumnosAusentesRecurrentes) {
        this.nombreAlumnosAusentesRecurrentes = nombreAlumnosAusentesRecurrentes;
    }

    public ArrayList<String> getTelefonoAlumnosAusentesRecurrentes() {
        return telefonoAlumnosAusentesRecurrentes;
    }

    public void setTelefonoAlumnosAusentesRecurrentes(ArrayList<String> telefonoAlumnosAusentesRecurrentes) {
        this.telefonoAlumnosAusentesRecurrentes = telefonoAlumnosAusentesRecurrentes;
    }

    public ArrayList<String> getCelularAlumnosAusentesRecurrentes() {
        return celularAlumnosAusentesRecurrentes;
    }

    public void setCelularAlumnosAusentesRecurrentes(ArrayList<String> celularAlumnosAusentesRecurrentes) {
        this.celularAlumnosAusentesRecurrentes = celularAlumnosAusentesRecurrentes;
    }

    public ArrayList<String> getEmailAlumnosAusentesRecurrentes() {
        return emailAlumnosAusentesRecurrentes;
    }

    public void setEmailAlumnosAusentesRecurrentes(ArrayList<String> emailAlumnosAusentesRecurrentes) {
        this.emailAlumnosAusentesRecurrentes = emailAlumnosAusentesRecurrentes;
    }

    public ArrayList<String> getDniAlumnosEnGrupo() {
        return dniAlumnosEnGrupo;
    }

    public void setDniAlumnosEnGrupo(ArrayList<String> dniAlumnosEnGrupo) {
        this.dniAlumnosEnGrupo = dniAlumnosEnGrupo;
    }

    public ArrayList<String> getTelefonoAlumnosEnGrupo() {
        return telefonoAlumnosEnGrupo;
    }

    public void setTelefonoAlumnosEnGrupo(ArrayList<String> telefonoAlumnosEnGrupo) {
        this.telefonoAlumnosEnGrupo = telefonoAlumnosEnGrupo;
    }

    public ArrayList<String> getEmailAlumnosEnGrupo() {
        return emailAlumnosEnGrupo;
    }

    public void setEmailAlumnosEnGrupo(ArrayList<String> emailAlumnosEnGrupo) {
        this.emailAlumnosEnGrupo = emailAlumnosEnGrupo;
    }

    public ArrayList<String> getCelularAlumnosEnGrupo() {
        return celularAlumnosEnGrupo;
    }

    public void setCelularAlumnosEnGrupo(ArrayList<String> celularAlumnosEnGrupo) {
        this.celularAlumnosEnGrupo = celularAlumnosEnGrupo;
    }

    public ArrayList<String> getSexoAlumnosEnGrupo() {
        return sexoAlumnosEnGrupo;
    }

    public void setSexoAlumnosEnGrupo(ArrayList<String> sexoAlumnosEnGrupo) {
        this.sexoAlumnosEnGrupo = sexoAlumnosEnGrupo;
    }

    public ArrayList<String> getNacionalidadAlumnosEnGrupo() {
        return nacionalidadAlumnosEnGrupo;
    }

    public void setNacionalidadAlumnosEnGrupo(ArrayList<String> nacionalidadAlumnosEnGrupo) {
        this.nacionalidadAlumnosEnGrupo = nacionalidadAlumnosEnGrupo;
    }

    public ArrayList<String> getDireccionAlumnosEnGrupo() {
        return direccionAlumnosEnGrupo;
    }

    public void setDireccionAlumnosEnGrupo(ArrayList<String> direccionAlumnosEnGrupo) {
        this.direccionAlumnosEnGrupo = direccionAlumnosEnGrupo;
    }
}
