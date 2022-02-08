package com.tiburela.ecuavisit.models;

public class AdEstablecimiento {

    private String nombreEstablecimiento ;
    private String Descripcion ;
    private String direccion ;
    private String ubicacionCordenaGoogleMap ;

    private String urlPhoto_SeparateBy_sign ;
    private String provincia ;
    private String ciudadOcanton ;
    private String numeroWhatsapp ;
    private String otroNumeroTelefonico ;





    public AdEstablecimiento(String nombreEstablecimiento, String descripcion, String direccion, String ubicacionCordenaGoogleMap, String urlPhoto_SeparateBy_sign, String provincia, String ciudadOcanton, String numeroWhatsapp, String otroNumeroTelefonico) {
        this.nombreEstablecimiento = nombreEstablecimiento;
        Descripcion = descripcion;
        this.direccion = direccion;
        this.ubicacionCordenaGoogleMap = ubicacionCordenaGoogleMap;
        this.urlPhoto_SeparateBy_sign = urlPhoto_SeparateBy_sign;
        this.provincia = provincia;
        this.ciudadOcanton = ciudadOcanton;
        this.numeroWhatsapp = numeroWhatsapp;
        this.otroNumeroTelefonico = otroNumeroTelefonico;
    }




    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbicacionCordenaGoogleMap() {
        return ubicacionCordenaGoogleMap;
    }

    public void setUbicacionCordenaGoogleMap(String ubicacionCordenaGoogleMap) {
        this.ubicacionCordenaGoogleMap = ubicacionCordenaGoogleMap;
    }

    public String getUrlPhoto_SeparateBy_sign() {
        return urlPhoto_SeparateBy_sign;
    }

    public void setUrlPhoto_SeparateBy_sign(String urlPhoto_SeparateBy_sign) {
        this.urlPhoto_SeparateBy_sign = urlPhoto_SeparateBy_sign;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudadOcanton() {
        return ciudadOcanton;
    }

    public void setCiudadOcanton(String ciudadOcanton) {
        this.ciudadOcanton = ciudadOcanton;
    }

    public String getNumeroWhatsapp() {
        return numeroWhatsapp;
    }

    public void setNumeroWhatsapp(String numeroWhatsapp) {
        this.numeroWhatsapp = numeroWhatsapp;
    }

    public String getOtroNumeroTelefonico() {
        return otroNumeroTelefonico;
    }

    public void setOtroNumeroTelefonico(String otroNumeroTelefonico) {
        this.otroNumeroTelefonico = otroNumeroTelefonico;
    }







}
