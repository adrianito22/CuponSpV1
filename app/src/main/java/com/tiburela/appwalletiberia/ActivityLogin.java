package com.tiburela.appwalletiberia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.ui.ActivRecuperaPasword;

public class ActivityLogin extends AppCompatActivity {
    EditText useredit;
    EditText paswordedit;
    TextView recuperaTextview;
   Button ocultaYmuestrbtn;

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

                };



                mAuth.signInWithEmailAndPassword(useredit.getText().toString(), paswordedit.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {

                                Variables.correoThisUserand_Emisor=useredit.getText().toString();

                            Variables.mailFormtToFrtransacc= correoformatedo(Variables.correoThisUserand_Emisor);

                                vamosActivity();

                            } else {

                                Toast.makeText(ActivityLogin.this, "por favor verifica tu email", Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            Toast.makeText(ActivityLogin.this, "ocurrio un error", Toast.LENGTH_SHORT).show();
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





}