package com.tiburela.appwalletiberia.ui.transaccionesListView;


import android.util.Log;

import androidx.annotation.Keep;

@Keep

public class ItemHomeModel  implements Comparable<ItemHomeModel> {


    public String id_value;
    public String fecha;
    public String enviaorecibe;
    public double transaccionValor;
    public String nombreRecibe; //identifcador de si es tarjeta creada propia o preefinida
    public String nombreEnvia;
    public int indexbyDefaultMode;

    public  String horaActual;
    public  String  horaeMillisegundos;




    public ItemHomeModel(String id_value, String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia,String horaActual, String horaeMillisegundos) {

        this.id_value=id_value;
        this.fecha = fecha;
        this.enviaorecibe = enviaorecibe;
        this.transaccionValor = transaccionValor;
        this.nombreRecibe =nombreRecibe;
        this.nombreEnvia =nombreEnvia;
        this.indexbyDefaultMode =indexbyDefaultMode;

        this.horaActual =horaActual;
        this.horaeMillisegundos =horaeMillisegundos;

    }


 /*
    @Override
    public int compareTo(ItemHomeModel object) {
        int compareage = ((ItemHomeModel)object).horaeMillisegundos;
       // return this.horaeMillisegundos - compareage;
        return compareage-this.horaeMillisegundos;

    }
    */
 @Override
 public int compareTo( ItemHomeModel object) {
     return horaeMillisegundos.compareTo(object.horaeMillisegundos);
 }




    @Override public String toString()
    {

       Log.d("dgdg","hora actual es "+horaActual);

        return "[ rollno=" + horaActual  + "]";
    }
}
