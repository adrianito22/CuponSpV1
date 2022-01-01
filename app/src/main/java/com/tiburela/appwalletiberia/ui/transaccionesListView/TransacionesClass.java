package com.tiburela.appwalletiberia.ui.transaccionesListView;

import androidx.annotation.Keep;

@Keep

public class TransacionesClass {



    //      public ItemHomeModel(String id, String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia) {


    public String fecha;
    public String enviaorecibe;
    public double transaccionValor;
    public String nombreRecibe;
    public String nombreEnvia;
    public String id;

    public  String horaActual;
    public  String horaeMillisegundos;



    public TransacionesClass(String id,String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia,String horaActual, String horaeMillisegundos) {
        this.fecha = fecha;
        this.enviaorecibe = enviaorecibe;
        this.transaccionValor = transaccionValor;
        this.nombreRecibe = nombreRecibe;
        this.nombreEnvia = nombreEnvia;
        this.id = id;
        this.horaActual=horaActual;
       this. horaeMillisegundos=horaeMillisegundos;


    }



}