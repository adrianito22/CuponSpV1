package com.tiburela.appwalletiberia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro extends Fragment {

    View root;
  EditText contrasena_Editxt;
  String contrasena_string;
EditText ediNomnre;


EditText ediApellido;
EditText ediNumeroTelefono;
EditText correo;
Button btnRegistrarse;
String email="";


private FirebaseAuth mAuth;


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";

// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;

public Registro() {
// Required empty public constructor
}

/**
* Use this factory method to create a new instance of
* this fragment using the provided parameters.
*
* @param param1 Parameter 1.
* @param param2 Parameter 2.
* @return A new instance of fragment Registro.
*/
// TODO: Rename and change types and number of parameters
public static Registro newInstance(String param1, String param2) {
Registro fragment = new Registro();
Bundle args = new Bundle();
args.putString(ARG_PARAM1, param1);
args.putString(ARG_PARAM2, param2);
fragment.setArguments(args);
return fragment;
}

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
if (getArguments() != null) {
mParam1 = getArguments().getString(ARG_PARAM1);
mParam2 = getArguments().getString(ARG_PARAM2);
}
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {


root = inflater.inflate(R.layout.registrarse, container, false);
// Inflate the layout for this fragment

inicilizaViews();
eventoBtns();
mAuth = FirebaseAuth.getInstance();

return root;


}



//








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


                 ///   finish();
                 ///   startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));



                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getActivity(), "tu ya estas registrado", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });




        if(email.length()>5){ //obtenemos el texto y lo gaudamos en el string

            paso2();

        }else{


            Toast.makeText(getActivity(), "coloca un correo valido", Toast.LENGTH_SHORT).show();

        }





    }



    private void inicilizaViews(){

        ediNomnre=root.findViewById(R.id.nameEdixt);
        ediApellido=root.findViewById(R.id.apellidoEdixt);
         ediNumeroTelefono=root.findViewById(R.id.numerotelfEdixt);
        correo=root.findViewById(R.id.correoEdixt);
         btnRegistrarse=root.findViewById(R.id.btnRegistrarse);




    }

    private void eventoBtns(){
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                obtieneTexto();


            }
        });
    }






}