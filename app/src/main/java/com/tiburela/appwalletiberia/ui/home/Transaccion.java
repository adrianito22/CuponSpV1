package com.tiburela.appwalletiberia.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tiburela.appwalletiberia.R;

import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Transaccion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Transaccion extends Fragment {
    EditText editTextNumberDecimal;
    private String current = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Transaccion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Transaccion.
     */
    // TODO: Rename and change types and number of parameters
    public static Transaccion newInstance(String param1, String param2) {
        Transaccion fragment = new Transaccion();
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
        View vista=inflater.inflate(R.layout.fragment_transaccion, container, false);

        editTextNumberDecimal=vista.findViewById(R.id.editTextTextPersonName3);
        textocambia();

        return vista;
    }






    private void textocambia() {
        editTextNumberDecimal.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    // [your_edittext].removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted;
                    editTextNumberDecimal.setText(formatted);
                    editTextNumberDecimal.setSelection(formatted.length());

                    editTextNumberDecimal.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }



}