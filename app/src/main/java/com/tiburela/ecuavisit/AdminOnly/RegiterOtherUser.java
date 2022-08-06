package com.tiburela.ecuavisit.AdminOnly;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.ecuavisit.Activitys.LoginActivity;
import com.tiburela.ecuavisit.Activitys.MainActivityCenter;
import com.tiburela.ecuavisit.LoginAndRegistro.RegistroActivity;
import com.tiburela.ecuavisit.R;
import com.tiburela.ecuavisit.models.UsuarioCliente;
import com.tiburela.ecuavisit.variablesGlobales.Variables;

public class RegiterOtherUser extends AppCompatActivity {
    boolean ExisteNode=false;

    EditText nombre;
    EditText apellido;
    EditText correo;
    EditText contrasena;
    Button btinicirsesion;
    private FirebaseAuth mAuth;
    private DatabaseReference myDatabaseReference;
    private boolean sellamoComprobacion=false;

    String userIDCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter_other_user);

        findViewsIds();
        eventoRegistrarse();
        mAuth = FirebaseAuth.getInstance();


    }


private void findViewsIds() {
     nombre=findViewById(R.id.nombre);
     apellido=findViewById(R.id.apellido);
     correo=findViewById(R.id.correo);
     contrasena=findViewById(R.id.contrasena);
    btinicirsesion =findViewById(R.id.btinicirsesion);


}

private void eventoRegistrarse() {

    btinicirsesion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            obtieneTexto() ;



        }
    });









}

    public void obtieneTexto(){

        //vamos a validar un numero telefonico.. 09 o 902588885 con 8 numeros....
        //si no no es

        if(! nombre.getText().toString().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
            nombre.setError("introduce un nombre valido");
            nombre.requestFocus();
            return;



        }
        if(! apellido.getText().toString().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
            apellido.setError("introduce un apellido valido");
            apellido.requestFocus();
            return;

        }


        if (correo.getText().toString().isEmpty()) {
            correo.setError("Correo es requerido");
            correo.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches()) {
            correo.setError("Por favor ingrese un correo valido");
            correo.requestFocus();

            return;
        }

        //private void creaNuevoUser(String nombre,String apellido,String correoElectronico,int userIdCategory,int nivelVerificacion,String password){


        if (contrasena.getText().toString().isEmpty()) {
            contrasena.setError("contrasena es requerida");
            contrasena.requestFocus();
            return;
        }

        if (contrasena.getText().toString().length() < 6) {
            contrasena.setError("el tamano minimo de contrasena es 6");
            contrasena .requestFocus();
            return;
        }




        mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //   progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification();

                    userIDCurrentUser=user.getUid();

                    //  finish();

                   // Variables. correoCurrent=email;
                   // Variables.paswordTemporal=contrasena_string;
                    //Variables.mailTemporal=email;


                    //muestra animcion de registrando

                   // muestraSheetCorreoEnviado();

                    chekIfUserExistDataBaseandCreateUser() ;


                    ////verificamos que no este el usuario...
                    //agregamos un nuevo user


                    Toast.makeText(RegiterOtherUser.this, "registro exitoso", Toast.LENGTH_SHORT).show();


                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegiterOtherUser.this, "Este ccorreo ya esta registrado", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegiterOtherUser.this, LoginActivity.class));


                    } else {
                        Toast.makeText(RegiterOtherUser.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



/*
        if(email.length()>5){ //obtenemos el texto y lo gaudamos en el string

            paso2();

        }else{


            Toast.makeText(RegiterOtherUser.this, "coloca un correo valido", Toast.LENGTH_SHORT).show();

        }

*/



    }

    private void creaNuevoUser(String nombre,String apellido,String numeroTelefonico,String correoElectronico,int userIdCategory,int nivelVerificacion,String password,String photourl){
        //cremoa un objeto
        UsuarioCliente userClienteObj= new UsuarioCliente(nombre,apellido, "090000xxx",correoElectronico,userIdCategory,nivelVerificacion,password,photourl);

        myDatabaseReference= FirebaseDatabase.getInstance().getReference("Clientes");

        myDatabaseReference.child(userIDCurrentUser).setValue(userClienteObj);

    }






    private boolean chekIfUserExistDataBaseandCreateUser(){ //verificamos si agregamos el usuario actyual a la base de datos
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userIDCurrentUser=user.getUid();

        }

        myDatabaseReference.orderByKey().equalTo(userIDCurrentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    if(!sellamoComprobacion){
                        sellamoComprobacion=true;   //existe el snapshot


                        Intent intent = new Intent(RegiterOtherUser.this, MainActivityCenter.class);
                        startActivity(intent);



                    }



                } else {

                    if(!sellamoComprobacion){
                        sellamoComprobacion=true; //no existe ,lo creamos..


                        creaNuevoUser(nombre.getText().toString(),apellido.getText().toString(),"0xxxxxxxxx",correo.getText().toString(),1,0,contrasena.getText().toString(),"");


                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return ExisteNode;
    }


}