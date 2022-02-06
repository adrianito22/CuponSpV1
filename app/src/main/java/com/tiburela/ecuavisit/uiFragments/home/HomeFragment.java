package com.tiburela.ecuavisit.uiFragments.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;

import com.tiburela.ecuavisit.R;

import java.util.ArrayList;

@Keep

public class HomeFragment extends Fragment{

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

            Toast.makeText(getActivity(), "no hay nada aqui hey", Toast.LENGTH_SHORT).show();
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.home_fragment, container, false);







        return root;
    }



    @Override
    public void onStart() {

        super.onStart();
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






}