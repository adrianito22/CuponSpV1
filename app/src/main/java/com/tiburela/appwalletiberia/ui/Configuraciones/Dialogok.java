package com.tiburela.appwalletiberia.ui.Configuraciones;

import static com.tiburela.appwalletiberia.DataFirerbase.Variables.pathEmisor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.R;
import com.tiburela.appwalletiberia.UsuarioCliente;
import com.tiburela.appwalletiberia.ui.home.HomeFragment;
import com.tiburela.appwalletiberia.ui.transaccionesListView.ItemHomeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Dialogok extends DialogFragment {
    int indiceX = 0;

    boolean secreonodoEmisor = false;
    boolean secreonodoReceptor = false;


    Button okbtn2;
    boolean seanadioValues = false;
    boolean seanadioValues2 = false;


    boolean hayNodos = false;

    String dataToListTransaccion = "";
    int indice0 = 0;

    String timeIdCurrent = "";

    ArrayList<String> listaTransacciones;
    List<String> listatsubir;
    boolean seHizoTransaccion = false;
    DatabaseReference myRef2;
    TextView txtseguro;

    TextView ediDestinatarioNombre;
    TextView ediCorreoDestinatario;
    TextView btnSi;
    TextView btnNo;
    ImageView imagernAqui2;
    FirebaseDatabase database;
    private String current = "";
    private DatabaseReference mDatabase;
    double totalCuentaReceptor = 0;
    private View rootView;
    LinearLayout linear_layout_01;
    ConstraintLayout layout;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.venatanagame3, container, false);
        findViewsByIds();

       // okbtn2.setVisibility(Button.INVISIBLE);


       // timeIdCurrent = "id" + String.valueOf(System.currentTimeMillis());


        //obtenemos la lista
        evento_botones();

        //ocultamos imagen
       // imagernAqui.setVisibility(ImageView.GONE);

      //  dataUsuarioEnvia(Variables.pathEmisor);
      //  dataUsuarioReceptor(Variables.pathReceptor);


       // mDatabase = FirebaseDatabase.getInstance().getReference("Data Users");


       // String fraseyvalor = "vas enviar " + Variables.transaccionvalorString + " dolares" + " a " + Variables.nombreyApelelidoEmisor;


      //  ediDestinatarioNombre.setText(fraseyvalor);
       // ediCorreoDestinatario.setText(Variables.correoDestinatario);


        return rootView;

    }


    private void findViewsByIds() {

        layout= rootView.findViewById(R.id.constraintLayout);
        linear_layout_01 = rootView.findViewById(R.id.linear_layout_01);

        imagernAqui2 = rootView.findViewById(R.id.imagernAqui2);
        txtseguro = rootView.findViewById(R.id.txtseguro);

        okbtn2 = rootView.findViewById(R.id.okbtn2);

    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i("verde", "no cerrar");

        // Add you codition
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //   setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme); //posiblemente volver activar


    }


    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }


    @Override
    public void onResume() {
        super.onResume();


        //    anulacion del boton back para no cerrar.
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog,
                                 int keyCode, android.view.KeyEvent event) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    // To dismiss the fragment when the back-button is pressed.
                    Log.i("verde", "no cerrar");
                    // dismiss();
                    return true;
                }
                // Otherwise, do nothing else
                else
                    return false;
            }
        });


    }


    private void evento_botones() {


        okbtn2.setOnClickListener(new View.OnClickListener() { //no hacemos la transaccion y cerramos
            @Override
            public void onClick(View v) {

                Log.i("sfsdfsdfzza","se llamo ok boton");


              //  habreFragment();

                dismiss();

                seanadioValues = false;
                seanadioValues2 = false;

              //  dismiss();

            }
        });
    }




}
