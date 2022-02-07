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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.tiburela.ecuavisit.LoginAndRegistro.RegistroActivity;
import com.tiburela.ecuavisit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.tiburela.ecuavisit.variablesGlobales.Variables;

public class LoginActivity extends AppCompatActivity {

    TextView registratebtn;
    private FirebaseAuth mAuth;
    final int SOLICITUD_CÓDIGO = 101;
 private boolean existeUser;

   private EditText correouser;
    private EditText contrasenauser;
 private Button btinicirsesion;

private boolean correoEstaConfirmado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance(); //verificamos que si esta autentificado..

        //iniloizamos vistas
             findVIewID();




               eventoBnt();


    }





private void autentificacionconmail( String correo,String password){
        if(correo==null){
            return;
        }
    mAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                if (mAuth.getCurrentUser().isEmailVerified()) {



                   /// Variables.correoThisUserand_Emisor=useredit.getText().toString();
                  //  Variables.mailFormtToFrtransacc= correoformatedo(Variables.correoThisUserand_Emisor);

                    //  checkUserExisteNodeReceptor();

                    //checkear si esta bloqueado....si no esta bloqueado ,,ingresamos....

                    //thisUserIsBlock(Variables.mailFormtToFrtransacc);


                    Variables.mailTemporal="";
                    Variables.paswordTemporal="";

                    startActivity(new Intent(LoginActivity.this,MainActivityCenter.class));


                } else {

                    if(Variables.paswordTemporal.length()>2){
                        //completa...

                        correouser.setText(Variables.mailTemporal);
                        contrasenauser.setText(Variables.paswordTemporal);

                        //reseetamos variables
                        Variables.mailTemporal="";
                        Variables.paswordTemporal="";

                    }

                    //mostramos....que no ha verificado email..
                    muestraSheetCorreoEnviado();

                  //  Toast.makeText(LoginActivity.this, "por favor verifica tu correo", Toast.LENGTH_SHORT).show();

                }




            }else {

                //si no essucefull y existe el correo

                if(checkExistMailUser(correo)) {

                    Toast.makeText(LoginActivity.this, "contraseña incorrecta", Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(LoginActivity.this, "No existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                }



            }


        }
    });

}

    private boolean checkExistMailUser(String email){

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            existeUser=false;
                            Toast.makeText(LoginActivity.this, "no existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "Is New User!");
                        } else {
                            existeUser=true;

                            Log.e("TAG", "Is Old User!");
                        }

                    }
                });

        return existeUser;
    }

private void eventoBnt() {
    btinicirsesion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) { //validamos que el correo sea el correcto..


            if (correouser.getText().toString().isEmpty()) {
                correouser.setError("Este espacio no puede estar vacio");
                correouser.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correouser.getText().toString()).matches()) {
                correouser.setError("por favor introduce un correo valido");
                correouser.requestFocus();
                return;
            }




            if (contrasenauser.getText().toString().isEmpty()) {
                contrasenauser.setError("contrasena no puede estar vacio");
                contrasenauser.requestFocus();
                return;
            }




            autentificacionconmail(correouser.getText().toString(),contrasenauser.getText().toString());

        }
    });





    //evento de registrobtn
    registratebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intencion = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intencion);

        }
    });



}


private void findVIewID(){
    correouser=findViewById(R.id.correouser);
    contrasenauser=findViewById(R.id.contrasenauser);
    btinicirsesion= findViewById(R.id.btinicirsesion);
    registratebtn = findViewById(R.id.registratebtn);

}



    @Override
    protected void onStart() {
        super.onStart();


    if (mAuth.getCurrentUser() != null) {

        autentificacionconmail(Variables.mailTemporal,Variables.paswordTemporal);

    }


    }

    private void muestraSheetCorreoEnviado(){

        final BottomSheetDialog bottomSheetDialognota = new BottomSheetDialog(LoginActivity.this);
        bottomSheetDialognota.setContentView(R.layout.botton_shett_nota);

        TextView note = bottomSheetDialognota.findViewById(R.id.txtNotaAqui);
        TextView tituloAqui= bottomSheetDialognota.findViewById(R.id.tituloAqui);

        Button btCerrar = bottomSheetDialognota.findViewById(R.id.btCerrar);

        // LinearLayout layoutcambiardata = bottomSheetDialog.findViewById(R.id.layoutcambiardata);

        String nota= "Revisa  "+Variables. correoCurrent+ " y da click en el link de confirmacion";
        String titulo= "Tu correo aun no ha sido confirmado ";

        note .setText(nota);
        tituloAqui.setText(titulo);


        //tambien cambiamos la imagen


        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  startActivity(new Intent(RegistroActivity.this, LoginActivity.class));

                bottomSheetDialognota.dismiss();

            }
        });


        bottomSheetDialognota.show();

    }

}