package com.tiburela.appwalletiberia.ui.transaccionesListView;


public class ItemHomeModel {

    public String id_value;
    public String fecha;
    public String enviaorecibe;
    public double transaccionValor;
    public String nombreRecibe; //identifcador de si es tarjeta creada propia o preefinida
    public String nombreEnvia;
    public int indexbyDefaultMode;



    public ItemHomeModel(){


    }



    public ItemHomeModel(String id_value, String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia) {

        this.id_value=id_value;
        this.fecha = fecha;
        this.enviaorecibe = enviaorecibe;
        this.transaccionValor = transaccionValor;
        this.nombreRecibe =nombreRecibe;
        this.nombreEnvia =nombreEnvia;
        this.indexbyDefaultMode =indexbyDefaultMode;
    }

}
