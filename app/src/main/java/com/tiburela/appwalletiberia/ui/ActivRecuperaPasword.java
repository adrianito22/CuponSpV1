package com.tiburela.appwalletiberia.ui;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.tiburela.appwalletiberia.ActivityLogin;
import com.tiburela.appwalletiberia.R;

public class ActivRecuperaPasword extends AppCompatActivity {

    EditText ediCorreo;
    Button btnContrasena;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activ_recupera_pasword);

        inicilizaViews();

        eventoBtn();
    }

    private void inicilizaViews() {

        ediCorreo = findViewById(R.id.ediCorreo);
        btnContrasena = findViewById(R.id.btnContrasena);

    }


    private void eventoBtn() {


        btnContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ediCorreo.getText().toString().length() ==0){
                    ediCorreo.setError("este espacio no puede estar vacio");
                    ediCorreo.requestFocus();
                    return;
                }


                else if (!Patterns.EMAIL_ADDRESS.matcher(ediCorreo.getText().toString()).matches()){

                    ediCorreo.setError("el correo no es valido");
                    ediCorreo.requestFocus();

                    return;


                }


                checkifmailexistAndSendMail(ediCorreo.getText().toString());






            }
        });


    }





    private void checkifmailexistAndSendMail(String mail){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.fetchSignInMethodsForEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {

                           // ediCorreo.setError("el correo no es valido");
                           // ediCorreo.requestFocus();

                            Toast.makeText(ActivRecuperaPasword.this, "No existe una cuenta con este correo ", Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "Is New User!");
                        }


                        else {

                            auth.sendPasswordResetEmail(ediCorreo.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(ActivRecuperaPasword.this, "Contraseña enviada, revisa tu correo  ", Toast.LENGTH_SHORT).show();

                                                Intent intencion= new Intent(ActivRecuperaPasword.this, ActivityLogin.class);
                                                startActivity(intencion);



                                            }
                                        }
                                    });

                            Log.e("TAG", "Is Old User!");
                        }

                    }
                });
    }


}