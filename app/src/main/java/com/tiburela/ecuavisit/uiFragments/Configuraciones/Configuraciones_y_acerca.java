package com.tiburela.ecuavisit.uiFragments.Configuraciones;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.ecuavisit.Activitys.LoginActivity;
import com.tiburela.ecuavisit.Activitys.MainActivityCenter;
import com.tiburela.ecuavisit.AdminOnly.RegiterOtherUser;
import com.tiburela.ecuavisit.LoginAndRegistro.RegistroCuentaGoogle;
import com.tiburela.ecuavisit.R;
import com.tiburela.ecuavisit.models.UsuarioCliente;
import com.tiburela.ecuavisit.variablesGlobales.Variables;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Configuraciones_y_acerca#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Configuraciones_y_acerca extends Fragment {
View rootviw;
    Button cerrarsesionBtn;
    Button button5;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
String userIDCurrentUser;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Configuraciones_y_acerca() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Configuraciones_y_acerca.
     */
    // TODO: Rename and change types and number of parameters
    public static Configuraciones_y_acerca newInstance(String param1, String param2) {
        Configuraciones_y_acerca fragment = new Configuraciones_y_acerca();
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
        // Inflate the layout for this fragment
        rootviw= inflater.inflate(R.layout.fragment_configuraciones_y_acerca, container, false);


        button5=(Button)rootviw.findViewById(R.id.button5);


        cerrarsesionBtn=(Button)rootviw.findViewById(R.id.cerrarsesionBtn);

        cerrarsesionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){

                    DatabaseReference ref;

                    userIDCurrentUser=user.getUid();
                    // String nameCurretnUSER=ref.getST
                    ref= FirebaseDatabase.getInstance().getReference().child("Clientes").child(userIDCurrentUser);

                    //vamos a datachange

                    addPostEventListener(ref);


                }


            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), RegiterOtherUser.class));

            }
        });

        return rootviw;
    }


    private void addPostEventListener(DatabaseReference mPostReference) {
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                Variables.globalUsuarioClienteObj = dataSnapshot.getValue(UsuarioCliente.class);


                //  Toast.makeText(MainActivityCenter.this, "EL NOMBRE ES "+usuarioClienteObjecCurrent.getNombre(), Toast.LENGTH_SHORT).show();
                if( Variables.globalUsuarioClienteObj.getPassword().equals("isGoogleAuth")){

                  //  Toast.makeText(getActivity(), "usuario con googel acount", Toast.LENGTH_SHORT).show();

                    if(  Variables. mGoogleSignInClient!=null){
                        Variables. mGoogleSignInClient.signOut();
                        FirebaseAuth.getInstance().signOut();

                    }



                    startActivity(new Intent(getActivity(),LoginActivity.class));

                  //  ((LoginActivity)getActivity()).signOutGoogleAccount();





                }else {
                  //  Toast.makeText(getActivity(), "usuario con pasword y contrasena ", Toast.LENGTH_SHORT).show();


                    ((MainActivityCenter)getActivity()).SaelUseryPasword();



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }


        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }






}