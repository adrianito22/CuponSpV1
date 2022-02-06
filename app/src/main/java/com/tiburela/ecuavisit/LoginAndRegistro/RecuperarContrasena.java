package com.tiburela.ecuavisit.LoginAndRegistro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.tiburela.ecuavisit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecuperarContrasena#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecuperarContrasena extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText ediCorreo;
    Button btnContrasena;
    View rootView;


    public RecuperarContrasena() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecuperarContrasena.
     */
    // TODO: Rename and change types and number of parameters
    public static RecuperarContrasena newInstance(String param1, String param2) {
        RecuperarContrasena fragment = new RecuperarContrasena();
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
        rootView = inflater.inflate(R.layout.fragment_recuperar_contrasena, container, false);


        return rootView;
    }


    private void inicilizaViews() {

        ediCorreo = rootView.findViewById(R.id.ediCorreo);
        btnContrasena = rootView.findViewById(R.id.btnContrasena);

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

                            Toast.makeText(getActivity(), "No existe una cuenta con este correo ", Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "Is New User!");
                        }


                        else {

                            auth.sendPasswordResetEmail(ediCorreo.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(getActivity(), "Contraseña enviada, revisa tu correo  ", Toast.LENGTH_SHORT).show();


                                            }
                                        }
                                    });

                            Log.e("TAG", "Is Old User!");
                        }

                    }
                });
    }


}