package com.tiburela.appwalletiberia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.ui.ActivRecuperaPasword;

public class ActivityLogin extends AppCompatActivity {
    EditText useredit;
    EditText paswordedit;
    TextView recuperaTextview;
   Button ocultaYmuestrbtn;
    boolean existeUser=false;



    boolean estaBloqueado=false;

    DatabaseReference mDatabase2 ;
    DatabaseReference userReference;

    boolean estamostrandoPasword=false;
    Button btn_inicia;
    String usuario ;
    String contrasena;
       ;
    Button btnRegistrarte;
    Button btnlogin;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciulizaViews();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String correo = extras.getString("user");
            String pasword = extras.getString("pasword");
            automcpletaEditext(pasword,correo);

        }


        mAuth = FirebaseAuth.getInstance();



        eventos();



       mDatabase2 = FirebaseDatabase.getInstance().getReference(); //desctivar este
       userReference = mDatabase2.child("Data Users").child("users");



    }

    private void iniciulizaViews(){
        btnRegistrarte=findViewById(R.id.btnRegistrarte);
        btnlogin=findViewById(R.id.btnlogin);

        useredit =findViewById(R.id.userEditext);
                paswordedit =findViewById(R.id.paswordEdtxt);


        recuperaTextview=findViewById(R.id.recuperaTextview);
        ocultaYmuestrbtn=findViewById(R.id.ocultaYmuestrbtn);


    }


    private void eventos(){


        ocultaYmuestrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!estamostrandoPasword)
                {
                    paswordedit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    estamostrandoPasword=true;


                    ocultaYmuestrbtn.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24a);


                }else{ //escondemos


                    paswordedit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    ocultaYmuestrbtn.setBackgroundResource(R.drawable.ic_baseline_visibility_24);

                    estamostrandoPasword=false;

                }


            }
        });






        recuperaTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intencion= new Intent(ActivityLogin.this, ActivRecuperaPasword.class);
                startActivity(intencion);



            }

        });










        btnRegistrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intencion= new Intent(ActivityLogin.this, RegistroActivity.class);
               startActivity(intencion);



            }

    });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validacampos()){
                    return;
                }

                checkExistMailUser(useredit.getText().toString());



                mAuth.signInWithEmailAndPassword(useredit.getText().toString(), paswordedit.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {

                                Variables.correoThisUserand_Emisor=useredit.getText().toString();
                            Variables.mailFormtToFrtransacc= correoformatedo(Variables.correoThisUserand_Emisor);

                             //  checkUserExisteNodeReceptor();

                                 //checkear si esta bloqueado....si no esta bloqueado ,,ingresamos....



                                //VA A ALA ACTIVIDAD SI USUARIO NO ESTA BLOQUEADO
                                thisUserIsBlock(Variables.mailFormtToFrtransacc);



                            } else {

                                Toast.makeText(ActivityLogin.this, "por favor verifica tu correo", Toast.LENGTH_SHORT).show();

                            }







                        }else {

                              //si no essucefull y existe el correo

                           if(checkExistMailUser(useredit.getText().toString())) {

                               Toast.makeText(ActivityLogin.this, "contraseña incorrecta", Toast.LENGTH_SHORT).show();

                           }else {

                               Toast.makeText(ActivityLogin.this, "No existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                           }



                        }


                    }
                });

            }
        });

    }


    private boolean validacampos(){

        Boolean estamoslistos =true;


        if(useredit.getText().toString().length() ==0 && paswordedit.getText().toString().length()==0  ){
            useredit.setError("el usuario no puede estar vacio");
            useredit.requestFocus();


            paswordedit.setError("la contrasena no  puede estar vacia");
            paswordedit.requestFocus();


            estamoslistos=false;


        }

        else if(useredit.getText().toString().length() ==0 ){
            useredit.setError("el usuario no puede estar vacio");
            useredit.requestFocus();

            estamoslistos=false;


        }



        else if(paswordedit.getText().toString().length() ==0 ){

            paswordedit.setError("la contrasena no  puede estar vacia");
            paswordedit.requestFocus();

            estamoslistos=false;


        }




        else if (!Patterns.EMAIL_ADDRESS.matcher(useredit.getText().toString()).matches()){

            useredit.setError("el correo no es valido");
            useredit.requestFocus();



            estamoslistos=false;


        }



   return estamoslistos;


    }

    private void vamosActivity(){

        Intent intencion = new Intent(ActivityLogin.this, MainActivityCenter.class);
        startActivity(intencion);


    }




    private void automcpletaEditext(String contrasena, String usuario){
        paswordedit.setText(contrasena);
        useredit.setText(usuario);
    }




    private String correoformatedo(String correoAqui) {
        String terminado=correoAqui.replaceAll("@", "0101010101010101");
        String String2terminado=terminado.replaceAll("\\.", "0101010101010");

        return String2terminado;
    }


    public boolean checkUserExisteNodeReceptor(){

       // DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference(); //desctivar este
       // DatabaseReference userReference = mDatabase2.child("Data Users").child("users");
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                if ((dataSnapshot.hasChild(correoformatedo(useredit.getText().toString())))) { //si existe este nodo el usairo esta registrado..

                    //tu cuenta si existe........

                    existeUser=true;

                }
                else
                    { //si no existe..le anadimos la data a esta lista

                    existeUser=false;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });


  return existeUser;
    }


    /*
    public void thisUserIsBlock(String path) {
        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference(); //desctivar este
        DatabaseReference userReference = mDatabase2.child("Data Users").child("users");
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.hasChild(path))) {

                    Log.i("verficvanxx", "este path ahora es  " + path);
                    Log.i("verficvanxx", "el valor de esta bloqueado es " +dataSnapshot.child(path).child("estaBloqueado").getValue());

                    estaBloqueado= (boolean) dataSnapshot.child(path).child("estaBloqueado").getValue();

                    Log.i("verficvanxadsfsdfx", "este path ahora es zxcxx  " + estaBloqueado);

                    if( estaBloqueado) {
                        Toast.makeText(ActivityLogin.this, "usuario  bloqueado Temporalmente", Toast.LENGTH_SHORT).show();
                        //esta bloqueado
                        btnlogin.setText("Usuario bloqueado")  ;
                        btnlogin.setTextColor(Color.parseColor("#5a5a5a"));
                        btnlogin.setEnabled(false);

                        btnRegistrarte.setTextColor(Color.parseColor("#5a5a5a"));
                        btnRegistrarte.setEnabled(false);

                    }
                    else { //no esta bloqueado
                        vamosActivity();
                    }


                } else { //si no existe..le anadimos la data a esta lista

                }





            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                // ...
            }
        });

    }
    */


    private void thisUserIsBlock(String path) {
        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference(); //desctivar este
        DatabaseReference userReference = mDatabase2.child("Data Users").child("users");
        ValueEventListener postListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if ((dataSnapshot.hasChild(path))) {

                    Log.i("verficvanxx", "este path ahora es  " + path);
                    Log.i("verficvanxx", "el valor de esta bloqueado es " +dataSnapshot.child(path).child("estaBloqueado").getValue());


                    String nombre= (String) dataSnapshot.child(path).child("nombre").getValue();
                    String apellido= (String) dataSnapshot.child(path).child("apellido").getValue();

                    Variables.nombreyApellidoEmisor=nombre+" "+apellido;;


                    estaBloqueado= (boolean) dataSnapshot.child(path).child("estaBloqueado").getValue();

                    Log.i("verficvanxadsfsdfx", "este path ahora es zxcxx  " + estaBloqueado);

                    if( estaBloqueado) {
                        Toast.makeText(ActivityLogin.this, "usuario  bloqueado Temporalmente", Toast.LENGTH_SHORT).show();
                        //esta bloqueado
                        btnlogin.setText("Usuario bloqueado")  ;
                      //  btnlogin.setTextColor(Color.parseColor("#5a5a5a"));
                        btnlogin.setEnabled(false);

                        btnRegistrarte.setTextColor(Color.parseColor("#5a5a5a"));
                        btnRegistrarte.setEnabled(false);

                    }

                    else {
                        btnlogin.setText("Iniciar sesion");
                      //  btnlogin.setTextColor(Color.parseColor("#5a5a5a"));
                        btnlogin.setEnabled(true);

                      //  btnRegistrarte.setTextColor(Color.parseColor("#5a5a5a"));
                        btnRegistrarte.setEnabled(true);
                        //no esta bloqueado
                        vamosActivity();

                    }


                } else { //si no existe..le anadimos la data a esta lista

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        userReference.addValueEventListener(postListener); //psoiblemnte lo eliminemos


    }

    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);

        //  super.onBackPressed();
       // moveTaskToBack(true);

    }


private boolean checkExistMailUser(String email){

    mAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                    boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                    if (isNewUser) {
                        existeUser=false;
                        Toast.makeText(ActivityLogin.this, "no existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                        Log.e("TAG", "Is New User!");
                    } else {
                        existeUser=true;

                        Log.e("TAG", "Is Old User!");
                    }

                }
            });

    return existeUser;
}




}