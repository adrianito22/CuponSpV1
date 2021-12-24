package com.tiburela.appwalletiberia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityDataBase extends AppCompatActivity {
    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myRef;
  EditText editxtdata;
     Button btSubirData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("Data Users"); //referencia al nodo o path donde vamos a escribir...


       // myRef.setValue("Hello, World!");

     //   public void  writeNewUser(String idUser, String nombre, String apellido, String correoElectronico,int saldoActual,int transaccionValor,String fechaTransaccion){


        writeNewUser("usuario Id","Adriano","Vicente","adrianovicente@gmail.com",100,10,"11 de diciembre del 2021",false);

        actualizaSaldoUser();

    }







private void listenerDtabase() { //



   /***El método onDataChange() de esta clase se activa cuando se adjunta el objeto de escucha
    *  y cada vez que cambian los datos, incluidos los secundarios.
    */

    // Read from the database
    myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            String value = dataSnapshot.getValue(String.class);
          //  Log.d(TAG, "Value is: " + value);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
           // Log.w(TAG, "Failed to read value.", error.toException());
        }
    });

}




    public void  writeNewUser(String idUser, String nombre, String apellido, String correoElectronico,long saldoActual,int transaccionValor,String fechaTransaccion,boolean numeroVerificado){

            UsuarioCliente usuarioClienteoBJ = new UsuarioCliente(idUser,nombre,apellido,correoElectronico,saldoActual,transaccionValor,fechaTransaccion,numeroVerificado);

        myRef.child("users").child(idUser).setValue(usuarioClienteoBJ);



    }


    public void actualizaSaldoUser(){

        myRef.child("users").child("usuario id").child("saldoActual").setValue(1000);
    }



}