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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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


public class RegistroCuentaGoogle extends AppCompatActivity implements View.OnTouchListener {

    View root;
  String contrasena_string;
EditText ediNomnre;
    private EditText mEditText;
String stringNumeroTelefonico;
    private DatabaseReference myDatabaseReference;
  //  EditText contrasena_Editxt;
    boolean estamostrandoPasword=false;

    Button ocultaYmuestrbtn2;



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


/*
    public RegistroCuentaGoogle(EditText editText) {
        mEditText = editText;
    }

*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_registro_google);

        inicilizaViews();


        llenaSomeEditext();


        myDatabaseReference=FirebaseDatabase.getInstance().getReference("Clientes");

      //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);



        agregatouchs();


        eventoBtns();


        textwatchadd();

        mAuth = FirebaseAuth.getInstance();


}






private void agregatouchs(){

    ediNomnre.setOnTouchListener(this);
    // contrasena_Editxt.setOnTouchListener(this);

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
   // contrasena_string=contrasena_Editxt.getText().toString();





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
    }else {

        //verificamos que sea el mnismo correo que el anterior..
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){

String correoOfEdit=correo.getText().toString();

if(!correoOfEdit.equals(account.getEmail())){

    correo.setError("correo debe ser el mismo de tu cuenta de google ");
    correo.requestFocus();
}


        }

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



        if(email.length()>5){ //obtenemos el texto y lo gaudamos en el string

            paso2();

        }else{


            Toast.makeText(RegistroCuentaGoogle.this, "coloca un correo valido", Toast.LENGTH_SHORT).show();

        }


    actualizaProfile(ediNomnre.getText().toString(),ediApellido.getText().toString(),numeroTelefonico.getText().toString(),email,1,0,"isGoogleAuth","");


    }

    private void inicilizaViews(){

        ediNomnre=findViewById(R.id.correouser);
        ediApellido=findViewById(R.id.contrasenauser);
        numeroTelefonico=findViewById(R.id.numeroTelefonico);
        correo=findViewById(R.id.correoEdixt);
         btnRegistrarse=findViewById(R.id.btinicirsesion);
      //  contrasena_Editxt=findViewById(R.id.ediTextContrasena);
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




        /*
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
   */




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






private void creaNuevoUser(String nombre,String apellido,String numeroTelefonico,String correoElectronico,int userIdCategory,int nivelVerificacion,String password,String photourl){
    //cremoa un objeto
    UsuarioCliente userClienteObj= new UsuarioCliente(nombre,apellido,numeroTelefonico,correoElectronico,userIdCategory,nivelVerificacion,password,photourl);


    myDatabaseReference.child(userIDCurrentUser).setValue(userClienteObj);

}




   private void  llenaSomeEditext(){
       GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       if(account!=null){


           correo.setText(account.getEmail());
           ediNomnre.setText(account.getGivenName());
           ediApellido.setText(account.getFamilyName());

           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
if(user!=null){
    userIDCurrentUser=user.getUid();

}


       }



   }


    private void actualizaProfile (String nombre,String apellido,String numeroTelefonico,String correoElectronico,int userIdCategory,int nivelVerificacion,String password,String photourl){
        try {
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Clientes").child(userIDCurrentUser);
            UsuarioCliente userClienteObj= new UsuarioCliente(nombre,apellido,numeroTelefonico,correoElectronico,userIdCategory,nivelVerificacion,password,photourl);
            Toast.makeText(this, "Registro Completado", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(RegistroCuentaGoogle.this, MainActivityCenter.class));
            ref.setValue(userClienteObj);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Se produjo un error", Toast.LENGTH_SHORT).show();

        }






    }





}