package com.tiburela.ecuavisit.uiFragments.home;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.ecuavisit.Activitys.MainActivityCenter;
import com.tiburela.ecuavisit.LoginAndRegistro.RegistroCuentaGoogle;
import com.tiburela.ecuavisit.R;
import com.tiburela.ecuavisit.models.UsuarioCliente;
import com.tiburela.ecuavisit.variablesGlobales.Variables;

import java.util.ArrayList;

@Keep

public class HomeFragment extends Fragment{
    String   userIDCurrentUser;
    DatabaseReference ref;
String textoSaludoActual="Hola";

        TextView textviewSaludo;

   SearchView searchView;
    ListView listView;
    double totalCuentaQueEnvia=0;
    Button  pagarqrCode2;

    DatabaseReference  mDatabase2 ;

   Button sendBtn;

String correoActualToSend ="";

    ArrayList<String> list;

    ArrayList<String> list2Mails;


    ArrayAdapter<String > adapter;

    View root;


    boolean hay_Dinero=false;
    boolean existeMail=true;

   Button btnEnviar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();

        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.home_fragment, container, false);
        textviewSaludo=(TextView) root.findViewById(R.id.textviewSaludo);
       // String textoSaludo= " Hey "+ Variables.globalUsuarioClienteObj.getNombre()+", Que destino crees que te espera hoy ?";

       // textoSaludoActual=textoSaludo;
       // textviewSaludo.setText(textoSaludo);



        iniiclizzaCurrentUserNode();



        return root;
    }



    @Override
    public void onStart() {

        super.onStart();




       if(Variables.currentSaludo!=null){
           textviewSaludo.setText( Variables.currentSaludo);

       }




    }





    private void iniiclizzaCurrentUserNode(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){


            userIDCurrentUser=user.getUid();
            // String nameCurretnUSER=ref.getST
            ref= FirebaseDatabase.getInstance().getReference().child("Clientes").child(userIDCurrentUser);

            //vamos a datachange

            addPostEventListener(ref);



        }



    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("taskdata","se ejecuto onresumne");




    }




    @Override
    public void onPause() {
        super.onPause();

    }



    private void addPostEventListener(DatabaseReference mPostReference) {

        // [START post_value_event_listener]


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI



             //   UsuarioCliente user=dataSnapshot.getValue(UsuarioCliente.class);

                Variables.globalUsuarioClienteObj = dataSnapshot.getValue(UsuarioCliente.class);

                Log.i("solodataaqui","midata eel nombre es  "+Variables.globalUsuarioClienteObj.getNombre());



            if(textoSaludoActual.equals("Hola")){  //obtenemos el primer nombre

                String arr[] =  Variables.globalUsuarioClienteObj.getNombre().split(" ");

                String firstWord = arr[0];

                String textoSaludo= " Hey "+ firstWord+", Que oferta crees que te espera hoy ?";


                Variables.currentSaludo=textoSaludo;
                textoSaludoActual=textoSaludo;
                textviewSaludo.setText(textoSaludo);


            }



            }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", error.toException());
                Log.i("ladtaxzz","HAY ERROR "+error.toException());

            }


        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

    }

    public class MyAsyncTasksx extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance

        }

        @Override
        protected String doInBackground(String... params) {

            // implement API in background and store the response in current variable
            String current = "";
            try {

              //  verificasiDebenhoyUpdate();

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }


        @Override
        protected void onPostExecute(String s) {

            Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


}