package com.tiburela.appwalletiberia.ui.transaccionesListView;

public class TransacionesClass {



    //      public ItemHomeModel(String id, String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia) {


    public String fecha;
    public String enviaorecibe;
    public double transaccionValor;
    public String nombreRecibe;
    public String nombreEnvia;
    public String id;


    public TransacionesClass(String id,String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia) {
        this.fecha = fecha;
        this.enviaorecibe = enviaorecibe;
        this.transaccionValor = transaccionValor;
        this.nombreRecibe = nombreRecibe;
        this.nombreEnvia = nombreEnvia;
        this.id = id;

    }



}