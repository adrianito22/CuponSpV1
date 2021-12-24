package com.tiburela.appwalletiberia.ui.Tarjetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tiburela.appwalletiberia.R;


public class Tarjetas extends Fragment {
int continente_selecionado=0;


    public Tarjetas() {
        // Required empty public constructor
    }


    public static Tarjetas newInstance(String param1, String param2) {
        Tarjetas fragment = new Tarjetas();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_cards, container, false);
        // Inflate the layout for this fragment


        return root;


    }


}