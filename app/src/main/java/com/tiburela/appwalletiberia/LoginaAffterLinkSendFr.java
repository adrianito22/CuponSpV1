package com.tiburela.appwalletiberia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginaAffterLinkSendFr#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LoginaAffterLinkSendFr extends Fragment {

    View rootView;

    EditText useredit;
    EditText paswordedit;
    Button btn_inicia;
    String usuario ;
    String contrasena ;
    // TODO: Rename parameter arguments, choose names that match
    private FirebaseAuth mAuth;


    public static LoginaAffterLinkSendFr newInstance(String param1, String param2) {
        LoginaAffterLinkSendFr fragment = new LoginaAffterLinkSendFr();
        return fragment;
    }

    public LoginaAffterLinkSendFr() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            usuario = getArguments().getString("user");
            contrasena = getArguments().getString("pasword");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView=inflater.inflate(R.layout.fragment_blank, container, false);
        inivistas();
        mAuth = FirebaseAuth.getInstance();

        colocauserycontrasena();
        eventoBtn();


        return rootView;
    }


    private void inivistas(){
        useredit=rootView.findViewById(R.id.useredit);
        paswordedit=rootView.findViewById(R.id.paswordedit);
        btn_inicia=rootView.findViewById(R.id.btn_inicia);



    }



    private void colocauserycontrasena(){

        Log.i("usergr","el usuario es "+usuario);
        Log.i("usergr","la contrasena es "+ contrasena);


        useredit.setText(usuario);
        paswordedit.setText(contrasena);


    }


    private void eventoBtn(){
       btn_inicia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signInWithEmailAndPassword(useredit.getText().toString(),paswordedit.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                if(mAuth.getCurrentUser().isEmailVerified())  {




                        Toast.makeText(getActivity(), "ingresamos al panel", Toast.LENGTH_SHORT).show();
                }


                 else{

                    Toast.makeText(getActivity(), "por favor verifica tu email", Toast.LENGTH_SHORT).show();

                }



            }else {

                Toast.makeText(getActivity(), "ocurrio un error", Toast.LENGTH_SHORT).show();



            }



                   }
               });



           }
       });



    }

}