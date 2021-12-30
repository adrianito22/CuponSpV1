package com.tiburela.appwalletiberia.ui.transaccion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.appwalletiberia.ActivityLogin;
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.R;
import com.tiburela.appwalletiberia.UsuarioCliente;
import com.tiburela.appwalletiberia.ui.home.DialogSendUsd;
import com.tiburela.appwalletiberia.ui.home.MoneyTextWatcher;

import java.text.NumberFormat;
import java.util.Locale;


public class TransaccionFragment extends Fragment {

    Handler handler;
    Runnable r;



   TextView correoAquiEdi;

   String nombre="";
    String apellido="";
    View vista;
    String nombreYapellido="";
    EditText editTextNumberDecimal;


    private String current = "";
    EditText   edi2;
   Button btnTransferir;
    private DatabaseReference mDatabase;
    double totalCuentaReceptor =0;
    double totalCuentaQueEnvia=0;
    double totalcantidadEnviar=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (getArguments() != null) {
            Bundle bundle = getArguments();


        }

   }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         vista=inflater.inflate(R.layout.transaccion_layout, container, false);

        editTextNumberDecimal=vista.findViewById(R.id.editTextTextPersonName3);
        edi2=vista.findViewById(R.id.edi2);

        btnTransferir=vista.findViewById(R.id.btnTransferir);


        correoAquiEdi=vista.findViewById(R.id.correoAquiEdi);

      //ponemos el correo a donde enviar

        correoAquiEdi.setText(Variables.correoDestinatario);

        btnTransferir();


        //le anadimosd aqui
        edi2.addTextChangedListener(new MoneyTextWatcher(edi2));

       // textocambia();


        crepaths();




        dataUsuarioEnvia(Variables.pathEmisor);
        dataUsuarioReceptor(Variables.pathReceptor);




        Log.i("datsu","la data es "+ Variables.UserFormateadoEmisor);

        Log.i("datsu","la data es DSGDFGD GFF "+ Variables.UserFormateadoReceptor);





        mDatabase = FirebaseDatabase.getInstance().getReference("Data Users");





        return vista;



    }









    /***
     *enviaremos desde cuenta adriano virey a adriapps10
     *adriano virey contiene 50dolares adriapps contiene 0 dolares
     *
     * */







    private void dataUsuarioEnvia(String userNodeEnvia){
        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/users/"+userNodeEnvia);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UsuarioCliente post = dataSnapshot.getValue(UsuarioCliente.class);
                // ..
              Variables.totalCuentaQueEnvia=post.saldoActual;

                muestraSladoActual();

                if(post.estaBloqueado){
                    Intent intencion= new Intent( getActivity(), ActivityLogin.class);
                    startActivity(intencion);

                }

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



    private void dataUsuarioReceptor(String  userNodeReceptor){


        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/users/"+userNodeReceptor);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UsuarioCliente post = dataSnapshot.getValue(UsuarioCliente.class);
                // ..
                Variables.totalCuentaReceptor =post.saldoActual;

                    nombre= post.nombre;
                    apellido= post.apellido;

                    nombreYapellido=nombre+" "+apellido;
                    Variables.seObtuvoNombres=true;

                    muestrNombreYapellido();
                    Log.i("RRRRRRRRT","XXDF MBIEN SE LLAMO " );





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




    private void ObtenDatosUnaVez(){

        //obtenemos la data del que envia y el que recibe

        mDatabase.child("users").child(Variables.UserFormateadoEmisor).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {


                    Log.d("firebase", String.valueOf(task.getResult().getValue()));


                }
            }
        });


    }


private void btnTransferir() {

    btnTransferir.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(edi2.getText().toString().trim().length() == 0){

                Toast.makeText(getActivity(), "este campo no puede estar vacio", Toast.LENGTH_SHORT).show();

                return;

            }



            if(Variables.pathReceptor.equals(Variables.pathEmisor)){

    Toast.makeText(getActivity(), "no puedes tranferir a la misma cuenta", Toast.LENGTH_SHORT).show();


    return;


}
            Variables.montoAtransferirse =  Double.parseDouble(edi2.getText().toString().substring(1));


            if( Variables.montoAtransferirse < 0.10){

                Toast.makeText(getActivity(), "La transferencia minima es de 10 centavos", Toast.LENGTH_SHORT).show();


                return;


            }







        //    Variables.totalDespuesSendDestinatario_receptor = totalCuentaReceptor + Double.parseDouble(edi2.getText().toString().substring(1));



     //       Variables. totalDespuesSendDestinatario_emisor=totalCuentaQueEnvia - Double.parseDouble(edi2.getText().toString().substring(1));

      //      actualizaUsuarios(Variables.totalDespuesSendDestinatario_receptor, Variables.totalDespuesSendDestinatario_emisor);





            //guardamos el valor de esta transaccion

            Variables.transaccionvalorString=edi2.getText().toString().substring(1);

                    Variables. montoThisTransaccion=Double.parseDouble(edi2.getText().toString().substring(1));

           //abrimos el dialog
            DialogSendUsd dialog = new DialogSendUsd();



            dialog.show(getActivity().getSupportFragmentManager(), "image_dialog");



        }
    });



}




////actualizamos los dos usuarios...

private void  actualizaUsuarios(double saldoReceptor,double saldoEmisor ){


    dataUsuarioEnvia(Variables.pathEmisor);
    dataUsuarioReceptor(Variables.pathReceptor);



    Log.i("debugeoxxcf","el saldo emisor es  "+saldoEmisor);
    Log.i("debugeoxxcf","el saldo receptor es  "+saldoReceptor);




    //usuario que recibe dolares le sumamos
    mDatabase.child("users").child(Variables.pathReceptor).child("saldoActual").setValue(saldoReceptor);


//al usuario que envia dolares le restamos
    mDatabase.child("users").child(Variables.pathEmisor).child("saldoActual").setValue(saldoEmisor);






}










    private void muestraSladoActual(){


       TextView textSaldoAqui=vista.findViewById(R.id.textSaldoAqui);
        //muestra el saldo actual redondeado
       // double valorredondeado=Math.round(Variables.totalCuentaQueEnvia * 100.0) / 100.0;
        //textSaldoAqui.setText(String.valueOf(valorredondeado));

        String cantidad= NumberFormat.getCurrencyInstance(Locale.US).format(Variables.totalCuentaQueEnvia);
        textSaldoAqui.setText(String.valueOf(cantidad));
    }



    private void muestrNombreYapellido(){


        Log.i("HOLA","SE LLMO ESTE METHODO");

        TextView   ediTnombreAqui=vista.findViewById(R.id.ediTnombreAqui);


        ediTnombreAqui.setText(nombreYapellido);

        Variables.nombreyApellidoReceptor = nombreYapellido;


    }



    /*
    public void testFunction(View view) {
        if(pathReceptor.equals(patEmisor)){

           // Toast.makeText(getActivity(), "no puedes tranferir a la misma cuenta", Toast.LENGTH_SHORT).show();


            return;


        }




        totalDespuesSendDestinatario_receptor = totalCuentaReceptor + Double.parseDouble(edi2.getText().toString().substring(1));

        totalDespuesSendDestinatario_emisor=totalCuentaQueEnvia - Double.parseDouble(edi2.getText().toString().substring(1));

        actualizaUsuarios(totalDespuesSendDestinatario_receptor,totalDespuesSendDestinatario_emisor);




    }


     */



   ///consegui los paths con ayuda de los correos(){




  private void crepaths () {

      Variables.pathEmisor= generaPathByMail(Variables.correoThisUserand_Emisor);
      Variables.pathReceptor= generaPathByMail(Variables.correoDestinatario);


  }




    private String generaPathByMail(String correoAqui) {


        String terminado=correoAqui.replaceAll("@", "0101010101010101");
        String String2terminado=terminado.replaceAll("\\.", "0101010101010");

        return String2terminado;

    }



}