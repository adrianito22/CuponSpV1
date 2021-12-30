package com.tiburela.appwalletiberia;

public class UsuarioCliente {
    public  String idUser;
    public  String nombre;
    public  String apellido;
    public  String correoElectronico;
    public  double saldoActual;
    public  int transaccionValor;
    public  String fechaTransaccion;
    public boolean numeroVerificado;
    public  boolean estaBloqueado;

public UsuarioCliente(){


}

    public UsuarioCliente(String correoElectronico ,String idUser, String nombre, String apellido ,double saldoActual,int transaccionValor,String fechaTransaccion, boolean numeroVerificado,boolean estaBloqueado){
        this.idUser = idUser;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.saldoActual = saldoActual;
        this.transaccionValor = transaccionValor;
        this.fechaTransaccion = fechaTransaccion;
       this. numeroVerificado=numeroVerificado;
        this. estaBloqueado=estaBloqueado;


    }



}
