package com.tiburela.appwalletiberia.ui.Configuraciones;


import static android.content.Context.WINDOW_SERVICE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;
import com.tiburela.appwalletiberia.ActivityLogin;
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.FragmetCamara;
import com.tiburela.appwalletiberia.R;
import com.tiburela.appwalletiberia.UsuarioCliente;
import com.tiburela.appwalletiberia.ui.home.HomeFragment;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class Configuraciones_y_acerca extends Fragment {

    TextView correoAquix;

    TextView nombre;
    String nombreString;
    String apellido;
    String nombreYapellido;


    View view;
    String correoThisUser="";


    private ImageView qrCodeIV;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Configuraciones_y_acerca() {
        // Required empty public constructor
    }

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





         view = inflater.inflate(R.layout.preferencia_fragment, container, false);



        FrameLayout fl = (FrameLayout) view.findViewById(R.id.nav_host_fragment); //eliminamos la vista

        if(fl != null) {
            fl.removeAllViews();

        }



        // initializing all variables.
        qrCodeIV = view.findViewById(R.id.idIVQrcode);
        generateQrBtn = view.findViewById(R.id.idBtnGenerateQR);



        generateqrbtnEvnt();

        dataUser(Variables.mailFormtToFrtransacc);

        inicilizaymuestradat();


        return view;


    }

    private void generateqrbtnEvnt() {




                if (TextUtils.isEmpty(Variables. correoThisUserand_Emisor)) {

                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.
                    Toast.makeText(getActivity(), "Hubo un error al generar el Qr", Toast.LENGTH_SHORT).show();
                } else {
                    // below line is for getting
                    // the windowmanager service.
                    WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);

                    // initializing a variable for default display.
                    Display display = manager.getDefaultDisplay();

                    // creating a variable for point which
                    // is to be displayed in QR Code.
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and
                    // height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height.
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    qrgEncoder = new QRGEncoder( Variables.correoThisUserand_Emisor, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrcode in the form of bitmap.
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        qrCodeIV.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString());
                    }
                }



}

    @Override
    public void onResume() {

       /*
        FrameLayout fl = (FrameLayout) view.findViewById(R.id.nav_host_fragment); //eliminamos la vista

        if(fl != null) {
            fl.removeAllViews();

        }
        */


        super.onResume();




        FragmentManager manager=getActivity().getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.nav_host_fragment);

        HomeFragment homr =new HomeFragment();


        if (homr != null) {  //SI NO ES NULLO
            manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.remove(homr);
            transaction.commit();

        }





    }

    private void inicilizaymuestradat(){
        correoAquix=view.findViewById(R.id.correoAquix);
        nombre=view.findViewById(R.id.nombre);

        correoAquix.setText(Variables.correoThisUserand_Emisor);



    }



    private void dataUser (String  userNodeReceptor){

        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/users/"+userNodeReceptor);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UsuarioCliente post = dataSnapshot.getValue(UsuarioCliente.class);
                // ..
            //    Variables.totalCuentaReceptor =post.saldoActual;

                nombreString= post.nombre;
                apellido= post.apellido;

                nombreYapellido=nombreString+" "+apellido;
                //    Variables.seObtuvoNombres=true;
                nombre.setText(nombreYapellido);


                //muestrNombreYapellido();
                Log.i("RRRRRRRdddfRT","XXDF MBIEN SE LLAMO " +nombreYapellido);

                // String dfdf= post.nombre;

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        collection.addValueEventListener(postListener);

    }



}