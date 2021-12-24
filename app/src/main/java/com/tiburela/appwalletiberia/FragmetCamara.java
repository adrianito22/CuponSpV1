package com.tiburela.appwalletiberia;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.ui.transaccion.TransaccionFragment;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmetCamara#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmetCamara extends Fragment {
    private ScannerLiveView camera;
    private TextView scannedTV;
     String correo;
    String nombre="";
    String apellido="";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmetCamara() {
        // Required empty public constructor
    }

    public static FragmetCamara newInstance(String param1, String param2) {
        FragmetCamara fragment = new FragmetCamara();
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
        View view = inflater.inflate(R.layout.fragment_qr_camera, container, false);

// initialize scannerLiveview and textview.
        scannedTV =view. findViewById(R.id.idTVscanned);
        camera = (ScannerLiveView)view. findViewById(R.id.camview);
        // check permission method is to check that the
        // camera permission is granted by user or not.
        // request permission method is to request the
        // camera permission if not given.

        if (!checkPermission()) {
            requestPermission();

            // if permission is already granted display a toast message
        }
        camaralistener();






        return view;
    }









    private boolean checkPermission() {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getActivity(), VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // this method is to request
        // the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA, VIBRATE}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called when user
        // allows the permission to use camera.
        if (grantResults.length > 0) {
            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (cameraaccepted && vibrateaccepted) {
                Toast.makeText(getActivity(), "Permisso accedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permiso denegado \n tu no puedes prover acceso", Toast.LENGTH_SHORT).show();
            }
        }
    }





    @Override
    public void onPause() {
        // on app pause the
        // camera will stop scanning.
        camera.stopScanner();
        super.onPause();
    }




    @Override
    public void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        // 0.5 is the area where we have
        // to place red marker for scanning.
        decoder.setScanAreaPercent(0.8);
        // below method will set secoder to camera.
        camera.setDecoder(decoder);
        camera.startScanner();




    }



    private void camaralistener(){
        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                // method is called when scanner is started
                Toast.makeText(getActivity(), "Scanner Empezado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                // method is called when scanner is stopped.
                Toast.makeText(getActivity(), "Scanner Stopped", Toast.LENGTH_SHORT).show();
            }




            @Override
            public void onScannerError(Throwable err) {
                // method is called when scanner gives some error.
                Toast.makeText(getActivity(), "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onCodeScanned(String data) {
                // method is called when camera scans the
                // qr code and the data from qr code is
                // stored in data in string format.
                scannedTV.setText(data);

                Toast.makeText(getActivity(), "el codigo es "+data, Toast.LENGTH_SHORT).show();

                //FORMATEAMOS ESTE CORREO Y LO BUSCAMOS EN FIREBASE..
                Variables.correoDestinatario=data;
/**
 *                 //aqui ya dbemos de tener el corre del emisor y del recpetor..
 * el correo del emisor ya lo tenemos anteriormente ...en login activity..
 * ahora despues abrimos fragment Transaccion fragment y desde alli envciamos
 * */

                habreFragment();

            }
        });
    }




    private void habreFragment() {


        TransaccionFragment mFragment= new TransaccionFragment(); //la remplazamos..

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, mFragment)
                .commit();

    }




}