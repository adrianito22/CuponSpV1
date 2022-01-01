package com.tiburela.appwalletiberia;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
@Keep
public class RegistroActivity extends AppCompatActivity {
    EditText contrasena_Editxt;
    boolean estamostrandoPasword=false;

    Button ocultaYmuestrbtn;

    String contrasena_string;
    EditText ediNomnre;
    EditText ediApellido;
    EditText ediNumeroTelefono;
    EditText correo;
    Button btnRegistrarse;
    String email="";

    EditText numerotelfEdixtz;

    String pathUser="";

    DatabaseReference myRef;

    FirebaseDatabase database;

    private FirebaseAuth mAuth;
    boolean existeMail=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registrarse);

        inicilizaViews();
        eventoBtns();
        mAuth = FirebaseAuth.getInstance();



        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("Data Users"); //referencia al nodo o path donde vamos a escribir...



    }

    public void paso2(){   //este hacerlo mas delante

        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("http://tiburela.com")
                        // This must be true
                        .setHandleCodeInApp(true)
                      //  .setIOSBundleId("http://tiburela.com")
                        .setAndroidPackageName(
                                "com.tiburela.appwalletiberia",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();




        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Log.i("debugenadoapp","sucefull");

                            //  Log.d(TAG, "Email sent.");
                        }else{

                            Log.i("debugenadoapp","no se envio");



                        }
                    }
                });
    }


    public void obtieneTexto(){

        email =correo.getText().toString();
        contrasena_string=contrasena_Editxt.getText().toString();



        if (email.isEmpty()) {
            correo.setError("Correo es requerido");
            correo.requestFocus();
            return;
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            correo.setError("Por favor ingrese un correo valido");
            correo.requestFocus();



            return;
        }else{

          if(  checkifmailexist(email)){

              return;
          }

        }

        if (contrasena_string.isEmpty()) {
            contrasena_Editxt.setError("Contraseña es requerida");
            contrasena_Editxt.requestFocus();
            return;
        }




        if (contrasena_string.length() < 8) {
            contrasena_Editxt .setError("el tamaño minimo de contraseña es 8 caracteres");
            contrasena_Editxt .requestFocus();
            return;
        }




        if (ediNomnre.getText().toString().length() < 2) {
            ediNomnre .setError("inserte un nombre valido ");
            ediNomnre .requestFocus();
            return;
        }


        if (ediApellido.getText().toString().length() < 2) {
            ediApellido .setError("inserte un Apellido valido ");
            ediApellido .requestFocus();
            return;
        }




/*
        if (numerotelfEdixtz.getText().toString().length() < 9) {
            numerotelfEdixtz .setError("inserte un numero valido");
            numerotelfEdixtz .requestFocus();
            return;
        }

        */


        esNumeros(contrasena_string);









if(!validaseguridaContrasena(contrasena_string)) { //mas validaciones extra d contrasena

    return;

}



        mAuth.createUserWithEmailAndPassword(email, contrasena_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //   progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                             // String idUser, String nombre, String apellido, String correoElectronico,int saldoActual,int transaccionValor,String fechaTransaccion){

                                writeNewUser(correo.getText().toString(),user.getUid(),ediNomnre.getText().toString(),ediApellido.getText().toString(),0.00,0,"vacio",false,false);

                            //  abrefragment();

                                abreActivity();


                                Toast.makeText(RegistroActivity.this, "Registro exitoso ,Revisa tu correo para verificar la cuenta ", Toast.LENGTH_SHORT).show();




                            }else {


                                Toast.makeText(RegistroActivity.this, "Ocurrio un error ", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });


                    ///   finish();
                    ///   startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegistroActivity.this, "tu ya estas registrado", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });






    }



    private void inicilizaViews(){

        ediNomnre=findViewById(R.id.nameEdixt);
        ediApellido=findViewById(R.id.apellidoEdixt);
        ediNumeroTelefono=findViewById(R.id.numerotelfEdixt);
        correo=findViewById(R.id.correoEdixt);
        btnRegistrarse=findViewById(R.id.btnRegistrarse);

        contrasena_Editxt=findViewById(R.id.ediTextContrasena);

        ocultaYmuestrbtn=findViewById(R.id.ocultaYmuestrbtn2);

        numerotelfEdixtz=findViewById(R.id.numerotelfEdixtz);


    }

    private void eventoBtns() {
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                Log.i("sepulso","se puslo btn");

                        obtieneTexto();


            }
        });


        ocultaYmuestrbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!estamostrandoPasword) {
                    contrasena_Editxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    estamostrandoPasword = true;


                    ocultaYmuestrbtn.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24a);


                } else { //escondemos

                    contrasena_Editxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ocultaYmuestrbtn.setBackgroundResource(R.drawable.ic_baseline_visibility_24);

                    estamostrandoPasword = false;


                }


            }
        });

    }






  private void abrefragment() {


      FrameLayout fl = (FrameLayout) findViewById(R.id.fragmentId); //eliminamos la vista
      fl.removeAllViews();
      LoginaAffterLinkSendFr mFragment= new LoginaAffterLinkSendFr(); //la remplazamos..
      FragmentManager fragmentManager = getSupportFragmentManager();

      Bundle bundle = new Bundle();
      bundle.putString("user", email);
      bundle.putString("pasword", contrasena_string);


// set Fragmentclass Arguments
      mFragment.setArguments(bundle);

      fragmentManager.beginTransaction()
              .replace(R.id.fragmentId, mFragment).commit();

    }



    private void abreActivity(){

        Intent intent = new Intent(RegistroActivity.this,ActivityLogin.class);
        intent.putExtra("user",email);
        intent.putExtra("pasword",contrasena_string);

        startActivity(intent);


    }




    private boolean checkifmailexist(String mail){

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.fetchSignInMethodsForEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {

                            Log.e("TAG", "Is New User!");
                        }

                        else {

                            existeMail=true;
                            Toast.makeText(RegistroActivity.this, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "Is Old User!");
                        }

                    }
                });


return existeMail;

    }




    // revisa que cumpla con lo necesario

    private boolean validaseguridaContrasena(String contrasenaAqui){

        boolean listo=true;


        if(!contrasenaAqui.equals(contrasenaAqui.toLowerCase())&&isNumeric(contrasenaAqui)){

        }else {

              contrasena_Editxt.setError("Incluye al menos  una letra Mayuscula  y un numero ");
              contrasena_Editxt.requestFocus();
            listo=false;
            return listo;

        }


         if( !contrasenaAqui.equals(contrasenaAqui.toLowerCase())){


         }

         else{

             contrasena_Editxt.setError("Incluye al menos  una letra Mayuscula ");
             contrasena_Editxt.requestFocus();

             listo=false;
             return listo;

         }



if(isNumeric(contrasenaAqui)){




}else{

    contrasena_Editxt.setError("Incluye al menos  una numero");
    contrasena_Editxt.requestFocus();

    listo=false;
    return listo;


}

        return listo;

    }






    public static boolean isNumeric(String str) {

        boolean hayNumero=false;

        char[] ch = str.toCharArray();


        for(int indice=0; indice<ch.length; indice++){

            String actualstr= String.valueOf(ch[indice]);

            try {
                Integer.parseInt(actualstr);
                hayNumero=true;

                //   Double.parseDouble(actualstr);
                break;

            } catch(NumberFormatException e){
                hayNumero=false;

            }

        }

        return hayNumero;
    }




    public boolean esNumeros(String str){
        boolean haynumeros=false;


       if( str.matches(".*\\d.*")){
           haynumeros=true;


       }else{

           contrasena_Editxt.setError("Incluye al menos un numero");
           contrasena_Editxt.requestFocus();


       }


    return haynumeros;
    }


private void PasaconfirmacionTelf(){


        FrameLayout fl = (FrameLayout) findViewById(R.id.fragmentId); //eliminamos la vista
        fl.removeAllViews();
        VerificacionTelefFragm mFragment= new VerificacionTelefFragm(); //la remplazamos..
        FragmentManager fragmentManager = getSupportFragmentManager();



    //   Bundle bundle = new Bundle();
    //  bundle.putString("user", email);
    //   bundle.putString("pasword", contrasena_string);

// set Fragmentclass Arguments
    //  mFragment.setArguments(bundle);

      fragmentManager.beginTransaction()
           .replace(R.id.fragmentId, mFragment).commit();


}



    public void  writeNewUser(String correoElectronico,String idUser, String nombre, String apellido ,double saldoActual,int transaccionValor,String fechaTransaccion,boolean numeroVerificado,boolean establoqueado){

        UsuarioCliente usuarioClienteoBJ = new UsuarioCliente(correoElectronico,idUser,nombre,apellido,saldoActual,transaccionValor,fechaTransaccion,numeroVerificado,establoqueado);

        myRef.child("users").child(correoformatedo(correoElectronico)).setValue(usuarioClienteoBJ);


          pathUser=correoformatedo(correoElectronico);



    }


      //remplzandoarrobaypunto


    private String correoformatedo(String correoAqui) {
   String terminado=correoAqui.replaceAll("@", "0101010101010101");
   String String2terminado=terminado.replaceAll("\\.", "0101010101010");

   return String2terminado;


    }





}