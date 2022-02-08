package com.tiburela.ecuavisit.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.tiburela.ecuavisit.LoginAndRegistro.RegistroActivity;
import com.tiburela.ecuavisit.R;
import com.tiburela.ecuavisit.variablesGlobales.Variables;

public class SubeLugares extends AppCompatActivity {
    String stringNumeroTelefonico;

    EditText ediNombreEstablecimiento;
    EditText numeroTelefonico;
    EditText direccionEdi;
    EditText decripcionEdi;

    Button btnContinuar;
    String userIDCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sube_lugares);
    }





    public void obtieneTexto(){



        stringNumeroTelefonico=numeroTelefonico.getText().toString();


        if(! ediNombreEstablecimiento.getText().toString().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
            ediNombreEstablecimiento.setError("introduce un nombre valido");
            ediNombreEstablecimiento.requestFocus();
            return;

        }





        if(stringNumeroTelefonico.isEmpty()){
            numeroTelefonico.setError("Numero telefonico es requerido");
            numeroTelefonico.requestFocus();
            return;

        }


        else { //aqui validamos el formato del numero

            if(stringNumeroTelefonico.length()!=10){

                numeroTelefonico.setError("Asegurate de insertar un Numero Valido");
                numeroTelefonico.requestFocus();
                return;

            }


            if( stringNumeroTelefonico.charAt(0) != '0' || stringNumeroTelefonico.charAt(1) != '9'){ //el rpimero substring tiene que ser cero y el 2segundo 9
                //   Toast.makeText(this, "escribe un numero en este formato 09xxxxxxxx", Toast.LENGTH_SHORT).show();
                numeroTelefonico.setError("Escribe un numero en este formato 09xxxxxxxx");
                numeroTelefonico.requestFocus();
                return;

            }


        }


        if(!(direccionEdi.getText().toString().length() >5)){

            direccionEdi.setError("Por favor escribe una direccion menos corta ");
            direccionEdi.requestFocus();
            return;
        }
        if(!(decripcionEdi.getText().toString().length() >10)){

            decripcionEdi.setError("Por favor escribe una descripcion  menos corta ");
            decripcionEdi.requestFocus();
            return;
        }




    }




private void findviewId() {

      btnContinuar = findViewById(R.id.btnContinuar);
     ediNombreEstablecimiento= findViewById(R.id.nombreEstabecimientoEdi);
     numeroTelefonico= findViewById(R.id.numeroTelefonicoEdi);
     direccionEdi= findViewById(R.id.direccionEdi);
     decripcionEdi= findViewById(R.id.decripcionEdi);


}

private void eventoBtns(){
    btnContinuar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            obtieneTexto();


        }
    });



}


}