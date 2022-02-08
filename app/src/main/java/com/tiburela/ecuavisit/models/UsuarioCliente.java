package com.tiburela.ecuavisit.models;

public class UsuarioCliente {

private String Nombre;

    private String apellido;
    private String numeroTelefonico;
    private String password;
    private String correoElectronico;
    private int useridCategory; //usaurio 1 es un suaurio normal..
    private int nivelDeVerificacion; //si es 0 aun no a confirmado correo..
    private String photoProfileUrl;


public UsuarioCliente(){

}


    public UsuarioCliente(String Nombre,String apellido, String numeroTelefonico, String correoElectronico, int useridCategory, int nivelDeVerificacion, String password, String photoProfileUrl) {
        this.Nombre = Nombre;
        this.apellido = apellido;

        this.numeroTelefonico = numeroTelefonico;
        this.correoElectronico = correoElectronico;
        this.useridCategory = useridCategory;
        this.nivelDeVerificacion = nivelDeVerificacion;
        this.password = password;
        this.photoProfileUrl=photoProfileUrl;

    }



    public int getNivelDeVerificacion() {
        return nivelDeVerificacion;
    }


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getPassword() {
        return password;
    }

    public void setPasword(String password) {
        this.password = password;
    }


    public String getPhotoProfileUrl() {
        return photoProfileUrl;
    }

    public void setPhotoProfileUrl(String photoProfileUrl) {
        this.photoProfileUrl = photoProfileUrl;
    }



    public void setNivelDeVerificacion(int nivelDeVerificacion) {
        this.nivelDeVerificacion = nivelDeVerificacion;
    }



    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getUseridCategory() {
        return useridCategory;
    }

    public void setUseridCategory(int useridCategory) {
        this.useridCategory = useridCategory;
    }



}
