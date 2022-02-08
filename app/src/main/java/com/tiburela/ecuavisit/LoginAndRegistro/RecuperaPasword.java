package com.tiburela.ecuavisit.LoginAndRegistro;

import static android.content.ContentValues.TAG;

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
import com.tiburela.ecuavisit.Activitys.LoginActivity;
import com.tiburela.ecuavisit.R;

public class RecuperaPasword extends AppCompatActivity {
EditText correoOlvidado;
Button btn_enviarconstrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_pasword);

        correoOlvidado=findViewById(R.id.correoOlvidado);
        btn_enviarconstrasena=findViewById(R.id.btn_enviarconstrasena);

        btn_enviarconstrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email =correoOlvidado.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    correoOlvidado.setError("Por favor ingrese un correo valido");
                    correoOlvidado.requestFocus();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress =email;

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");

                                    Toast.makeText(RecuperaPasword.this, "tu contrasena fue enviada, revisa tu correo ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RecuperaPasword.this, LoginActivity.class));


                                }else {

                                    checkExistMailUser(emailAddress);


                                }
                            }
                        });


            }
        });

    }

    private void checkExistMailUser(String email) {
         FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance(); //verificamos que si esta autentificado..

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {

                            Toast.makeText(RecuperaPasword.this, "no existe una cuenta con este correo", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "Is New User!");
                        }

                    }
                });

    }


}