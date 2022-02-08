package com.tiburela.ecuavisit.LoginAndRegistro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiburela.ecuavisit.Activitys.LoginActivity;
import com.tiburela.ecuavisit.Activitys.MainActivityCenter;
import com.tiburela.ecuavisit.R;
import com.tiburela.ecuavisit.models.UsuarioCliente;
import com.tiburela.ecuavisit.variablesGlobales.Variables;


public class RegistroActivity extends AppCompatActivity implements View.OnTouchListener {

    View root;
  String contrasena_string;
    private EditText mEditText;
String stringNumeroTelefonico;
    private DatabaseReference myDatabaseReference;
    boolean estamostrandoPasword=false;

    Button ocultaYmuestrbtn2;


    EditText ediNomnre;

    EditText contrasena_Editxt;
EditText ediApellido;
EditText numeroTelefonico;
EditText correo;
Button btnRegistrarse;
String email="";
String userIDCurrentUser;

LinearLayout laynombre,layoutapellido,layoutcorreo,layoutpassword,layoutnumerotelefonico;


private FirebaseAuth mAuth;


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;

public RegistroActivity() {
// Required empty public constructor
}

    public RegistroActivity(EditText editText) {
        mEditText = editText;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registrarse);

        inicilizaViews();

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        myDatabaseReference=FirebaseDatabase.getInstance().getReference("Clientes");




        agregatouchs();


        eventoBtns();


        textwatchadd();

        mAuth = FirebaseAuth.getInstance();


}






private void agregatouchs(){

    ediNomnre.setOnTouchListener(this);
     contrasena_Editxt.setOnTouchListener(this);

     ediApellido.setOnTouchListener(this);
     numeroTelefonico.setOnTouchListener(this);
     correo.setOnTouchListener(this);


}





public void paso2(){

ActionCodeSettings actionCodeSettings =
ActionCodeSettings.newBuilder()
        // URL you want to redirect back to. The domain (www.example.com) for this
        // URL must be whitelisted in the Firebase Console.
        .setUrl("http://tiburela.com")
        // This must be true
        .setHandleCodeInApp(true)
        .setIOSBundleId("http://tiburela.com")
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

            Log.i("debugenado app","sucefull");

          //  Log.d(TAG, "Email sent.");
        }
    }
});
}



public void obtieneTexto(){

    //vamos a validar un numero telefonico.. 09 o 902588885 con 8 numeros....
    //si no no es


    stringNumeroTelefonico=numeroTelefonico.getText().toString();
    email =correo.getText().toString();
    contrasena_string=contrasena_Editxt.getText().toString();


    if(! ediNomnre.getText().toString().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
        ediNomnre.setError("introduce un nombre valido");
        ediNomnre.requestFocus();
        return;



    }
    if(! ediApellido.getText().toString().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
        ediApellido.setError("introduce un apellido valido");
        ediApellido.requestFocus();
        return;

    }


    if (email.isEmpty()) {
        correo.setError("Correo es requerido");
        correo.requestFocus();
        return;
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        correo.setError("Por favor ingrese un correo valido");
        correo.requestFocus();

        return;
    }

    //private void creaNuevoUser(String nombre,String apellido,String correoElectronico,int userIdCategory,int nivelVerificacion,String password){








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

      //  String substring0=stringNumeroTelefonico.substring(0);
      //  String substring1=stringNumeroTelefonico.substring(1);

        Log.i("subrstring","el priemro substring es "+stringNumeroTelefonico.substring(0));

        Log.i("subrstring","el segundo substring es "+stringNumeroTelefonico.substring(1));

//0986222384
        if( stringNumeroTelefonico.charAt(0) != '0' || stringNumeroTelefonico.charAt(1) != '9'){ //el rpimero substring tiene que ser cero y el 2segundo 9
         //   Toast.makeText(this, "escribe un numero en este formato 09xxxxxxxx", Toast.LENGTH_SHORT).show();
            numeroTelefonico.setError("Escribe un numero en este formato 09xxxxxxxx");
            numeroTelefonico.requestFocus();
            return;

        }
        //tienq que tener.....//inserte el numero en este formato..



    }






        if (contrasena_string.isEmpty()) {
            contrasena_Editxt.setError("contrasena es requerida");
            contrasena_Editxt.requestFocus();
            return;
        }

        if (contrasena_string.length() < 6) {
            contrasena_Editxt .setError("el tamano minimo de contrasena es 6");
            contrasena_Editxt .requestFocus();
            return;
        }




        mAuth.createUserWithEmailAndPassword(email, contrasena_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             //   progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification();

                     userIDCurrentUser=user.getUid();

                    Toast.makeText(RegistroActivity.this, "registro exitoso", Toast.LENGTH_SHORT).show();
                  //  finish();

                   Variables. correoCurrent=email;
                   Variables.paswordTemporal=contrasena_string;
                   Variables.mailTemporal=email;


                   //muestra animcion de registrando

                    muestraSheetCorreoEnviado();


                        //agregamos un nuevo user
                    creaNuevoUser(ediNomnre.getText().toString(),ediApellido.getText().toString(),numeroTelefonico.getText().toString(),email,1,0,contrasena_string,"");





                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(RegistroActivity.this, "Tu correo ya esta registrado", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegistroActivity.this,LoginActivity.class));


                    } else {
                        Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });




        if(email.length()>5){ //obtenemos el texto y lo gaudamos en el string

            paso2();

        }else{


            Toast.makeText(RegistroActivity.this, "coloca un correo valido", Toast.LENGTH_SHORT).show();

        }





    }



    private void inicilizaViews(){

        ediNomnre=findViewById(R.id.correouser);
        ediApellido=findViewById(R.id.contrasenauser);
        numeroTelefonico=findViewById(R.id.numeroTelefonico);
        correo=findViewById(R.id.correoEdixt);
         btnRegistrarse=findViewById(R.id.btinicirsesion);
        contrasena_Editxt=findViewById(R.id.ediTextContrasena);
        ocultaYmuestrbtn2=findViewById(R.id.ocultaYmuestrbtn2);


               laynombre=findViewById(R.id.laynombre);
                layoutapellido=findViewById(R.id.layoutapellido);
                layoutcorreo=findViewById(R.id.layoutcorreo);
                layoutpassword=findViewById(R.id.layoutpassword);
                layoutnumerotelefonico=findViewById(R.id.layoutnumerotelefonico);



    }

    private void eventoBtns(){
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                obtieneTexto();


            }
        });


        ocultaYmuestrbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!estamostrandoPasword) {
                    contrasena_Editxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    estamostrandoPasword = true;


                    ocultaYmuestrbtn2.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24a);


                } else { //escondemos

                    contrasena_Editxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ocultaYmuestrbtn2.setBackgroundResource(R.drawable.ic_baseline_visibility_24);

                    estamostrandoPasword = false;


                }


            }
        });
    }




private void textwatchadd(){

    ediNomnre.addTextChangedListener(new CustomTextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           // Log.i("misala",";adataes ex como esta ");

        }
    });
}


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

    String tagCurrentview=view.getTag().toString();


        Log.i("misala","el tag selecionado es "+tagCurrentview);


            if(MotionEvent.ACTION_UP == motionEvent.getAction()) {
                Log.i("assdfmisala","es action UP");


                switch(tagCurrentview){
                    case"nombre":
                        Log.i("assdfmisala","se llamo el nombre");


                        // laynombre,layoutapellido,layoutcorreo,layoutpassword,layoutnumerotelefonico;

                        laynombre.setBackgroundResource(R.drawable.roundz_select);

                        layoutapellido.setBackgroundResource(R.drawable.roundz);
                        layoutcorreo.setBackgroundResource(R.drawable.roundz);
                        layoutpassword.setBackgroundResource(R.drawable.roundz);
                        layoutnumerotelefonico.setBackgroundResource(R.drawable.roundz);


                        Log.i("misala","selec el selecionado es "+tagCurrentview);


                        break;


                    case"apellido":
                        laynombre.setBackgroundResource(R.drawable.roundz);
                        layoutapellido.setBackgroundResource(R.drawable.roundz_select);
                        layoutcorreo.setBackgroundResource(R.drawable.roundz);
                        layoutpassword.setBackgroundResource(R.drawable.roundz);
                        layoutnumerotelefonico.setBackgroundResource(R.drawable.roundz);

                        break;



                    case"correo":

                        laynombre.setBackgroundResource(R.drawable.roundz);
                        layoutapellido.setBackgroundResource(R.drawable.roundz);
                        layoutcorreo.setBackgroundResource(R.drawable.roundz_select);
                        layoutpassword.setBackgroundResource(R.drawable.roundz);
                        layoutnumerotelefonico.setBackgroundResource(R.drawable.roundz);

                        break;



                    case"numerotelefonico":

                        laynombre.setBackgroundResource(R.drawable.roundz);
                        layoutapellido.setBackgroundResource(R.drawable.roundz);
                        layoutcorreo.setBackgroundResource(R.drawable.roundz);
                        layoutpassword.setBackgroundResource(R.drawable.roundz);
                        layoutnumerotelefonico.setBackgroundResource(R.drawable.roundz_select);


                        break;

                    case"password":

                        laynombre.setBackgroundResource(R.drawable.roundz);
                        layoutapellido.setBackgroundResource(R.drawable.roundz);
                        layoutcorreo.setBackgroundResource(R.drawable.roundz);
                        layoutpassword.setBackgroundResource(R.drawable.roundz_select);
                        layoutnumerotelefonico.setBackgroundResource(R.drawable.roundz);

                        break;

                }
            }

        return false;
    }



    private void muestraSheetCorreoEnviado(){

            final BottomSheetDialog bottomSheetDialognota = new BottomSheetDialog(RegistroActivity.this);
            bottomSheetDialognota.setContentView(R.layout.botton_shett_nota);

            TextView note = bottomSheetDialognota.findViewById(R.id.txtNotaAqui);
            TextView tituloAqui= bottomSheetDialognota.findViewById(R.id.tituloAqui);
            TextView subtitulo= bottomSheetDialognota.findViewById(R.id.subtitulo);

        Button btCerrar = bottomSheetDialognota.findViewById(R.id.btCerrar);

            // LinearLayout layoutcambiardata = bottomSheetDialog.findViewById(R.id.layoutcambiardata);

        String nota= "Hemos enviado un correo electronico a "+Variables. correoCurrent;
        String titulo= " ! Registro Exitoso !" ;
        String subtituloString="Se necesita confirmacion de correo Eelectronico";

        String btnTexto="Entendido";
        btCerrar.setText(btnTexto);


        note .setText(nota);
        tituloAqui.setText(titulo);
        subtitulo.setText(subtituloString);


            btCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     startActivity(new Intent(RegistroActivity.this, LoginActivity.class));

                    bottomSheetDialognota.dismiss();

                }
            });


            bottomSheetDialognota.show();

        }

    private void corregirCorreoBootonSheet(){

        final BottomSheetDialog bottomSheetDialognota = new BottomSheetDialog(RegistroActivity.this);
        bottomSheetDialognota.setContentView(R.layout.botton_shett_nota);

        TextView note = bottomSheetDialognota.findViewById(R.id.txtNotaAqui);
        Button btCerrar = bottomSheetDialognota.findViewById(R.id.btCerrar);
        // LinearLayout layoutcambiardata = bottomSheetDialog.findViewById(R.id.layoutcambiardata);

        String nota= "Introduce el correo electronico Aqui"+Variables. correoCurrent;

        String titulo= "Asegurate de Escribirlo Correctamente";


        note .setText(nota);


        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialognota.dismiss();
            }
        });




        bottomSheetDialognota.show();
    }


private void creaNuevoUser(String nombre,String apellido,String numeroTelefonico,String correoElectronico,int userIdCategory,int nivelVerificacion,String password,String photourl){
    //cremoa un objeto
    UsuarioCliente userClienteObj= new UsuarioCliente(nombre,apellido,numeroTelefonico,correoElectronico,userIdCategory,nivelVerificacion,password,photourl);


    myDatabaseReference.child(userIDCurrentUser).setValue(userClienteObj);

}


}