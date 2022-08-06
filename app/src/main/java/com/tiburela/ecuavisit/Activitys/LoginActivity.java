package com.tiburela.ecuavisit.Activitys;

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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.ecuavisit.LoginAndRegistro.RecuperaPasword;
import com.tiburela.ecuavisit.LoginAndRegistro.RegistroActivity;
import com.tiburela.ecuavisit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.tiburela.ecuavisit.models.UsuarioCliente;
import com.tiburela.ecuavisit.variablesGlobales.Variables;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    Button ocultaMuestraTexto ;
    FirebaseUser userFcaebook ;
    boolean estamostrandoPasword=false;

    SignInButton signInButtonGoogle;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    String userIDCurrentUser;
    boolean ExisteNode=false;
    FirebaseDatabase firebaseDatabase;
    private boolean sellamoComprobacion=false;
    TextView textviewOlvidastePass;
    // private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference myDatabaseReference;
    private CallbackManager mCallbackManager;

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
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true); //anterior

        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Clientes"); //anterior
        facebookLoginActivity();

        //iniloizamos vistas
        findVIewID();


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))

                .requestEmail()
                .build();

        Variables.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        eventoBnt();


    }


    private void autentificacionconmail(String correo, String password) {
        if (correo == null) {
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


                        Variables.mailTemporal = "";
                        Variables.paswordTemporal = "";

                        startActivity(new Intent(LoginActivity.this, MainActivityCenter.class));


                    } else {

                        if (Variables.paswordTemporal.length() > 2) {
                            //completa...

                            correouser.setText(Variables.mailTemporal);
                            contrasenauser.setText(Variables.paswordTemporal);

                            //reseetamos variables
                            Variables.mailTemporal = "";
                            Variables.paswordTemporal = "";

                        }

                        //mostramos....que no ha verificado email..
                        muestraSheetCorreoEnviado();

                        //  Toast.makeText(LoginActivity.this, "por favor verifica tu correo", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    //si no essucefull y existe el correo

                    if (checkExistMailUser(correo)) {

                        Toast.makeText(LoginActivity.this, "contraseña incorrecta", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(LoginActivity.this, "No existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                    }


                }


            }
        });

    }

    private boolean checkExistMailUser(String email) {

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            existeUser = false;
                            Toast.makeText(LoginActivity.this, "no existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "Is New User!");
                        } else {
                            existeUser = true;

                            Log.e("TAG", "Is Old User!");
                        }

                    }
                });

        return existeUser;
    }

    private void eventoBnt() {


        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });


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


                autentificacionconmail(correouser.getText().toString(), contrasenauser.getText().toString());

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


        textviewOlvidastePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RecuperaPasword.class));


            }
        });



        ocultaMuestraTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!estamostrandoPasword) {
                    contrasenauser.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    estamostrandoPasword = true;


                    ocultaMuestraTexto.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24a);


                } else { //escondemos

                    contrasenauser.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ocultaMuestraTexto.setBackgroundResource(R.drawable.ic_baseline_visibility_24);

                    estamostrandoPasword = false;


                }



            }
        });






    }


    private void findVIewID() {
        correouser = findViewById(R.id.nombre);
        contrasenauser = findViewById(R.id.apellido);
        btinicirsesion = findViewById(R.id.btinicirsesion);
        registratebtn = findViewById(R.id.registratebtn);

        signInButtonGoogle = findViewById(R.id.sign_in_buttonGoogle);
        textviewOlvidastePass=findViewById(R.id.textviewOlvidastePass);


        ocultaMuestraTexto=findViewById(R.id.ocultaYmuestrbtn2);

    }


    @Override
    protected void onStart() {
        super.onStart();


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this); //oibtrienwe ultimo siogning


        if (account != null&& Variables.mGoogleSignInClient!=null) { //el usario ya inicio sesion... ir a otra actividad..
            //  Variables.correoUserActual= account.getEmail();
            Toast.makeText(this, "se ejecuto este ", Toast.LENGTH_SHORT).show();


            Log.i("dataLoginxx", "se ejecuto este if ");
            // Log.i("dataLogin","el correo es  "+  Variables.correoUserActual);

            Intent intent = new Intent(LoginActivity.this, MainActivityCenter.class);
            startActivity(intent);

        }




        else   if (mAuth.getCurrentUser() != null) {

            Toast.makeText(this, "se ejecuto el else if  ", Toast.LENGTH_SHORT).show();

            Log.i("dataLoginxx", "se ejecuto el else if  ");

            /**por aqui verificar si hay confirmado el correo*/

            if (Variables.mailTemporal.length() > 1) {

                autentificacionconmail(Variables.mailTemporal, Variables.paswordTemporal);

            }


            else {
                Toast.makeText(this, "se ejecuto el else ultimo  ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivityCenter.class);
                startActivity(intent);
            }
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





    private void signInGoogle() {



        Intent signInIntent = Variables. mGoogleSignInClient.getSignInIntent();


        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.i("logingoogle","se puslo singin metodo2");



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.i("defugero", "firebaseAuthWithGoogle:" + account.getId());
                Log.i("defugero", "firebaseAuthWithGoogle:" + account.getDisplayName());




                firebaseAuthWithGoogle(account.getIdToken());
                Log.i("defugero","se jecuito el try");

            } catch (ApiException e) {

                Log.i("defugero","se produjo un error ");
                Log.i("defugero","se produjo un error es "+e);
                // Google Sign In failed, update UI appropriately
                Log.w("defugero", "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]



    private void firebaseAuthWithGoogle(String idToken) {  //se logea correctamente
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            Log.i("dataLogin","se ejecuto firebaseAuthWithGoogle()  succes");

                            FirebaseUser userGoogle = mAuth.getCurrentUser();
                            //Variables.correoUserActual=user.getEmail();

                            Log.i("logingoogle","se ejecuto este metodo");


                            //vamnos a la aactividad centrer

                            startActivity(new Intent(LoginActivity.this,MainActivityCenter.class));


                            /**verificar si por aqui creamos el nuevo nodo
                             * */

                           // creaNuevoUser(ediNomnre.getText().toString(),ediApellido.getText().toString(),numeroTelefonico.getText().toString(),email,1,0,contrasena_string,"");
                            chekIfUserExistDataBaseandCreateUser(userGoogle.getEmail(),userGoogle.getDisplayName(),"");

                                //verificamos si existe un



                            // updateUI(user);
                        } else {
                            Log.i("dataLogin","se ejecuto firebaseAuthWithGoogle() else failure ");

                            Log.i("logingoogle","ocurrio un errro "+task.getException());

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]



    private void creaNuevoUser(String nombre,String apellido,String numeroTelefonico,String correoElectronico,int userIdCategory,int nivelVerificacion,String password,String photourl){
        //cremoa un objeto
        UsuarioCliente userClienteObj= new UsuarioCliente(nombre,apellido,numeroTelefonico,correoElectronico,userIdCategory,nivelVerificacion,password,photourl);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userIDCurrentUser=user.getUid();

        }


        myDatabaseReference.child(userIDCurrentUser).setValue(userClienteObj);

    }


    private boolean chekIfUserExistDataBaseandCreateUser(String mail, String name,String secondName){ //verificamos si agregamos el usuario actyual a la base de datos
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userIDCurrentUser=user.getUid();

        }

        myDatabaseReference.orderByKey().equalTo(userIDCurrentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    if(!sellamoComprobacion){
                        sellamoComprobacion=true;


                        //existe

                        Intent intent = new Intent(LoginActivity.this, MainActivityCenter.class);
                        startActivity(intent);
                    }



                } else {

                    if(!sellamoComprobacion){
                        sellamoComprobacion=true;


                        //no existe

                        //si nos registramos con cuenta de google
                        if( Variables.mGoogleSignInClient!=null) { //se regsitor con google
                            creaNuevoUser(name,secondName,"",mail,0,0,"",""); //agregamos este usuario vacio..


                            Intent intent = new Intent(LoginActivity.this, MainActivityCenter.class);
                            startActivity(intent);
                        }

                        if( userFcaebook!=null) { //se regsitor con facebook
                            creaNuevoUser("Name","User","","gilberto@gmail.com",0,0,"",""); //agregamos este usuario vacio..


                            Intent intent = new Intent(LoginActivity.this, MainActivityCenter.class);
                            startActivity(intent);
                        }
                        else { //se registro con mail

                            creaNuevoUser("","","","",0,0,"",""); //agregamos este usuario vacio..


                            Intent intent = new Intent(LoginActivity.this, MainActivityCenter.class);
                            startActivity(intent);


                          //  Intent intent = new Intent(LoginActivity.this, RegistroCuentaGoogle.class);
                           // startActivity(intent);
                        }




                        //creamos un uservacio..



                    }



                    //Key does not exist
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return ExisteNode;
    }



    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);

        //  super.onBackPressed();
        //moveTaskToBack(true);
    }


    public  void signOutGoogleAccount() {

        // GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        // .requestEmail()
        //    .build();
        // GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        Variables. mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();

        //  startActivity(new Intent(LoginActivity.this, LoginActivity.class));

    }




private void facebookLoginActivity() {
   String EMAIL = "email";

// Initialize Facebook Login button
    mCallbackManager = CallbackManager.Factory.create();
    LoginButton loginButton = findViewById(R.id.login_buttonFacebook);
    loginButton.setReadPermissions(Arrays.asList(EMAIL));
    loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
          //  Log.d(TAG, "facebook:onSuccess:" + loginResult);
            Log.i("helloss", "facebook:onSuccess:" + loginResult);

            handleFacebookAccessToken(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "facebook:onCancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d(TAG, "facebook:onError", error);
        }
    });



}

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("helloss", "signInWithCredential:success");
                             userFcaebook = mAuth.getCurrentUser();


                            chekIfUserExistDataBaseandCreateUser("","","") ;

                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("helloss", "signInWithCredential:failure", task.getException());
                            // Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                   // Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_facebook]


}