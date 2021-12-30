package com.tiburela.appwalletiberia.ui.home;

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
import com.tiburela.appwalletiberia.ui.transaccionesListView.ItemHomeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DialogSendUsd extends DialogFragment {
    int indiceX = 0;

    boolean secreonodoEmisor = false;
    boolean secreonodoReceptor = false;


    Button okbtn;
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
    ImageView imagernAqui;
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
        rootView = inflater.inflate(R.layout.venatanagame2, container, false);
        findViewsByIds();

        okbtn.setVisibility(Button.INVISIBLE);


        timeIdCurrent = "id" + String.valueOf(System.currentTimeMillis());


        //obtenemos la lista
        evento_botones();

        //ocultamos imagen
        imagernAqui.setVisibility(ImageView.GONE);

        dataUsuarioEnvia(Variables.pathEmisor);
        dataUsuarioReceptor(Variables.pathReceptor);


        mDatabase = FirebaseDatabase.getInstance().getReference("Data Users");


        String fraseyvalor = "Vas a enviar " + Variables.transaccionvalorString + " $ " + " a " + Variables.nombreyApellidoReceptor;


        ediDestinatarioNombre.setText(fraseyvalor);
        ediCorreoDestinatario.setText(Variables.correoDestinatario);


        return rootView;

    }


    private void findViewsByIds() {

        layout= rootView.findViewById(R.id.constraintLayout);
        linear_layout_01 = rootView.findViewById(R.id.linear_layout_01);
        ediDestinatarioNombre = rootView.findViewById(R.id.ediDestinatarioNombre);
        ;
        ediCorreoDestinatario = rootView.findViewById(R.id.ediCorreoDestinatario);
        ;
        btnSi = rootView.findViewById(R.id.btnSi);
        btnNo = rootView.findViewById(R.id.btnNo);

        imagernAqui = rootView.findViewById(R.id.imagernAqui);
        txtseguro = rootView.findViewById(R.id.txtseguro);

        okbtn = rootView.findViewById(R.id.okbtn);

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

        btnSi.setOnClickListener(new View.OnClickListener() { //hacemos la transaccion
            @Override
            public void onClick(View view) {
                //cuando se haga la transacion ponemops transaccion exitosa y un check list


                // dataUsuarioReceptor2( Variables.pathEmisor);//probando

                if (Variables.pathReceptor.equals(Variables.pathEmisor)) {

                    Toast.makeText(getActivity(), "no puedes tranferir a tu propia cuenta", Toast.LENGTH_SHORT).show();

Log.i("sfsdfsdfzza","se jecuto este if code989");
                    dismiss();
                    return;
                }



                if(!MirasitienesaldoSuficiente())  {

                    FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.nav_host_fragment); //eliminamos la vista


                    if(fl != null) {
                        fl.removeAllViews();
                    }

                    dismiss();



                    return;

                }




                FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.nav_host_fragment); //eliminamos la vista
                if(fl!= null) {
                    fl.removeAllViews();

                }


                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                Fragment currentFragment = (Fragment)fragmentManager.findFragmentById(R.id.nav_host_fragment);
                if(currentFragment instanceof HomeFragment){

                    Log.i("ggdgd","fragment xiste");

                  //  Toast.makeText(getActivity(), "fragment existe", Toast.LENGTH_SHORT).show();
                    //SearchFragment exists in backstack , process accordingly
                }

                else{

                    Log.i("ggdgd","fragment no xiste");

                    //SearchFragment not present in backstack
                }





                //aqui hacemos el calcuklo


                Variables.totalDespuesSendDestinatario_receptor = Variables.totalCuentaReceptor + Variables.montoAtransferirse;

                Variables.totalDespuesSendDestinatario_emisor = Variables.totalCuentaQueEnvia - Variables.montoAtransferirse;

                //actualizamo base de datos

                actualizaUsuarios(Variables.totalDespuesSendDestinatario_receptor, Variables.totalDespuesSendDestinatario_emisor);



                transaccionExitosa();




            }


            /**
             *     volver activar despues
             *
             * */









            //    TransaccionFragment frtransaccion = new TransaccionFragment();
            //  frtransaccion.testFunction(view);


        });


        btnNo.setOnClickListener(new View.OnClickListener() { //no hacemos la transaccion y cerramos
            @Override
            public void onClick(View v) {


                dismiss();

            }
        });


        okbtn.setOnClickListener(new View.OnClickListener() { //no hacemos la transaccion y cerramos
            @Override
            public void onClick(View v) {

                Log.i("sfsdfsdfzza","se llamo ok boton");


                habreFragment();

                dismiss();

                seanadioValues = false;
                seanadioValues2 = false;

              //  dismiss();

            }
        });
    }

/*
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

*/


    private void actualizaUsuarios(double saldoReceptor, double saldoEmisor) {


        dataUsuarioEnvia(Variables.pathEmisor);
        dataUsuarioReceptor(Variables.pathReceptor);


        //usuario que recibe dolares le sumamos
        mDatabase.child("users").child(Variables.pathReceptor).child("saldoActual").setValue(saldoReceptor);


//al usuario que envia dolares le restamos
        mDatabase.child("users").child(Variables.pathEmisor).child("saldoActual").setValue(saldoEmisor);


    }

    private void dataUsuarioEnvia(String userNodeEnvia) {
        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/users/" + userNodeEnvia);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UsuarioCliente post = dataSnapshot.getValue(UsuarioCliente.class);
                // .
                Variables.totalCuentaQueEnvia = post.saldoActual;

                ///PRIMERO COMPROBAR SI EXISTE ESTA RUTA
                if (dataSnapshot.hasChild("0")) {

                    Variables.idStringNode = post.idUser;


                } else {

                    Variables.idStringNode = "0";


                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        collection.addValueEventListener(postListener);

    }


    private void obtenUusariosListEmisor() {
        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference(); //desctivar este
        DatabaseReference userReference = mDatabase2.child("transacciones");

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.hasChild(Variables.pathEmisor) && !seanadioValues)) {

                    secreonodoEmisor = true;
                    seanadioValues = true;
                    anadenuevoVaLUE();
                } else { //si no existe..le anadimos la data a esta lista


                    if (!secreonodoEmisor) {
                        //si no le pasamos esta lista...
                        Map<String, Object> map2 = new HashMap<>();

                        creaNodeEmisors(map2);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }


    private void obtenUusariosListReceptor() {

        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference(); //desctivar este

        DatabaseReference userReference = mDatabase2.child("transacciones");

        ValueEventListener postListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if ((dataSnapshot.hasChild(Variables.pathReceptor) && !seanadioValues2)) {
                    secreonodoReceptor = true;
                    seanadioValues2 = true;
                    anadenuevoVaLUERecpetor();

                } else { //si no existe..le anadimos la data a esta lista

                    if (!secreonodoReceptor) {

                        //si no le pasamos esta lista...
                        Map<String, Object> map2 = new HashMap<>();

                        creaNodeRcpetorx(map2);

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        userReference.addValueEventListener(postListener); //psoiblemnte lo eliminemos


    }


    private void dataUsuarioReceptor(String userNodeReceptor) {


        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/users/" + userNodeReceptor);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UsuarioCliente post = dataSnapshot.getValue(UsuarioCliente.class);


                // nombre= post.nombre;
                //  apellido= post.apellido;

                //  nombreYapellido=nombre+" "+apellido;
                //   DataFirebase.seObtuvoNombres=true;

                //muestrNombreYapellido();
                Log.i("RRRRRRRRT", "XXDF MBIEN SE LLAMO ");


                // String dfdf= post.nombre;
                Log.i("sgdfgdf", "el valor es " + totalCuentaReceptor);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        collection.addValueEventListener(postListener);

    }

    private String correoformatedo(String correoAqui) {
        String terminado = correoAqui.replaceAll("@", "0101010101010101");
        String String2terminado = terminado.replaceAll("\\.", "0101010101010");

        return String2terminado;


    }


    private void transaccionExitosa() {

        seHizoTransaccion = true;

        iniciaBaseData();//ninciamos la base de datos de transacciones

        obtenUusariosListEmisor();

        // obtenUusariosListReceptor();

        recuperadataunavezNodeReceptor();




       // layout.setBackgroundResource(R.drawable.back_transaccion_exitosa);

        //linear_layout_01.setBackgroundResource(R.drawable.back_transaccion_exitosa);




        ediDestinatarioNombre.setVisibility(View.GONE);
        TextView textView10 = rootView.findViewById(R.id.textView10);
        ediCorreoDestinatario.setVisibility(View.GONE);
        btnNo.setVisibility(View.GONE);
        okbtn.setVisibility(View.VISIBLE);
        txtseguro.setText("Transaccion Exitosa");

        textView10.setVisibility(Button.GONE);

        txtseguro.setTextColor(Color.parseColor("#424242"));

        btnSi.setVisibility(Button.GONE);

        imagernAqui.setVisibility(ImageView.VISIBLE);


        Variables.transaccionexistosa=true;

    }


    private void habreFragment() {


        FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.nav_host_fragment); //eliminamos la vista

        if (fl != null) {
            fl.removeAllViews();

        }


        HomeFragment mFragment = new HomeFragment(); //la remplazamos..

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, mFragment)
                .commit();

/*
            Transaccion mFragment= new Transaccion(); //la remplazamos..

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, mFragment)
                    .commit();

*/
    }


    private boolean MirasitienesaldoSuficiente() {

        boolean esSaldoSuficiente = true;


        if (Variables.totalCuentaQueEnvia - Variables.montoAtransferirse < 0) {

            Toast.makeText(getActivity(), "Tu saldo es insuficiente", Toast.LENGTH_SHORT).show();

            esSaldoSuficiente = false;
        }


        return esSaldoSuficiente;

    }


    public void creaNodeEmisors(Map<String, Object> map2) {


        /***
         posiblemente mas adlenate  eliminemos un data base con unn solo objto  basta
         ***/

        secreonodoEmisor = true;

        String stringagregar = generandoDataToList("listaUser2", generfecha(), "Enviado", Variables.montoAtransferirse, Variables.nombreyApellidoReceptor);

        Log.i("somomasladataesdf","el nombre y appelido emisor es   "+Variables.nombreyApellidoReceptor);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


        String key = mDatabase.push().getKey();
        map2.put(key, stringagregar);


        mDatabase.child("transacciones").child(Variables.pathEmisor).setValue(map2); //creamos un nodo transaccion y le anadimos la info


        Log.i("ayiudasomomasladataesdf","en crea node emi el path es   "+Variables.nombreyApellidoReceptor);

    }

    public void creaNodeRcpetorx(Map<String, Object> map2) {
        /****
         posiblemente mas adlenate  eliminemos un data base con unn solo objto  basta
         */
        secreonodoReceptor = true;
        String stringagregar = generandoDataToList("listaUser2", generfecha(), "Recibido", Variables.montoAtransferirse, Variables.nombreyApellidoEmisor);


        Log.i("databbbxs","nombre y appelido emisor es   "+Variables.nombreyApellidoEmisor);


      //  Log.i("somomasladataesdf","el nombre y appelido repector es   "+Variables.nombreyApellidoReceptor);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        String key = mDatabase.push().getKey();
        map2.put(key, stringagregar);


        mDatabase.child("transacciones").child(Variables.pathReceptor).setValue(map2); //creamos un nodo transaccion y le anadimos la info


        Log.i("somomasladataesdf","en crea node rec el path es   "+Variables.pathReceptor);

        Log.i("ayiudasomomasladataesdf","en crea node emi el path es   "+Variables.nombreyApellidoEmisor);

    }


    public void creaTransaccionNodeReceptor() {
        /****
         posiblemente mas adlenate  eliminemos un data base con unn solo objto  basta
         */

        // iniciYllenacionLista();


        //  Variables.idStringNode=String.valueOf(Variables.idNode);

        myRef2 = database.getReference("Data Users"); //referencia al nodo o path donde vamos a escribir...

        //  myRef2 = database.getReference("Data Users/transacciones/"+Variables.pathReceptor); //referencia al nodo o path donde vamos a escribir...

        //    ItemHomeModel transacObj = new ItemHomeModel (id,fecha,enviaorecibe,transaccionValor,nombreRecibe,nombreEnvia);

        // myRef2.child(Variables.pathReceptor).setValue(Variables.listaDescrgadaRecetor);

        myRef2.child(Variables.pathReceptor);


    }





    private void iniciaBaseData() {
        database = FirebaseDatabase.getInstance();


    }


    private void dataUsuarioReceptor2(String userNodeReceptor) {


        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/transacciones/" + userNodeReceptor);
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // ItemHomeModel post = dataSnapshot.getValue(ItemHomeModel.class);

                if (!dataSnapshot.hasChild("id1")) {//si no existe este nodo ,le damos de valor 0


                    Variables.contadorNode = 0;

                    // run some code

                    Log.i("debugeoccc", "se eejecuto el if");

                } else {//si no la obtenemos
                    Log.i("debugeoccc", "se eejecuto el else");

                    ///creo que este siempre sera positivo


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        //    TransacionesClass resultObj = ds.getValue(TransacionesClass.class);

                        ItemHomeModel resultObj2 = ds.getValue(ItemHomeModel.class);

                        //  for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {


                        Variables.idStringActual = resultObj2.id_value;


                        Variables.contadorNode = Integer.valueOf(Variables.idStringActual.replaceAll("\\D+", ""));

                        Log.i("xxxladtaesrr", "la data es esta " + Variables.idStringActual);


                    }


                    Log.i("xxxitemdesc", dataSnapshot.child("id1").getValue().toString());


                    //     Variables.contadorNode = Integer.valueOf( Variables.idStringActual.replaceAll("\\D+",""));
                    Log.i("itemdescf", String.valueOf(Variables.contadorNode));


                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        collection.addValueEventListener(postListener);

    }









/*
    private void cuentanodes(String  userNodeReceptor){

    //    DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/transacciones/"+userNodeReceptor);
    //    ValueEventListener postListener = new ValueEventListener() {




            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ItemHomeModel post = dataSnapshot.getValue(ItemHomeModel.class);

                if (!dataSnapshot.hasChild("id0")) {//si no existe este nodo ,le damos de valor 0


                    Variables.contadorNode=0;

                    // run some code



                }
                else{//si no la obtenemos

                    Query query=dataSnapshot.child("childname").orderByKey().limitToLast(1);


                    Log.i("item desc",dataSnapshot.child("id0").getValue().toString());

                    //  Log.i("item descf",  Variables.idStringActual=post.getId_value());




                    Variables.contadorNode = Integer.valueOf( Variables.idStringActual.replaceAll("\\D+",""));
                    Log.i("item descf", String.valueOf(Variables.contadorNode)) ;


                }




            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        collection.addValueEventListener(postListener);

    }

*/


    private void iniciYllenacionLista() {

        //    public ItemHomeModel(String id_value, String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia) {


        String nameaqui = "adriano.fecha.envia.20.Adriano Recibe.Mark envia";
        listatsubir = new ArrayList<>();


        listatsubir.add(nameaqui);
        listatsubir.add(nameaqui);
        listatsubir.add(nameaqui);
        listatsubir.add(nameaqui);
        listatsubir.add(nameaqui);


        // DatabaseReference mDatabase;
// ...
        //  mDatabase = FirebaseDatabase.getInstance().getReference();


        //  mDatabase.child("users").child("listas").setValue(listatsubir);


    }


    private void creaLista() {

        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        listaTransacciones = new ArrayList<String>();

    }


    private String generandoDataToList(String id_value, String fecha, String enviaorecibe, double transaccionValor, String nombreRecpetor_O_emisor) {

        final String separador = ",";

        String datattransaccion = id_value + separador + fecha + separador + enviaorecibe + separador + transaccionValor + separador + nombreRecpetor_O_emisor;


        //VAMOS OBTENER LA LISTA......DESPUES LE ANADIMOS DATA


        return datattransaccion;


    }


    private String generfecha() {


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        String fecha = dateFormat.format(date);
        return fecha;

    }


    private void anadeOtrovalor() {

        //primero descargamos la lista.....
        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference();
        Log.i("pathdebuge", "el path recpetor es " + pathEmisor);
        DatabaseReference userReference = mDatabase2.child("Data Users/transacciones/" + Variables.pathEmisor);
        userReference.push().setValue("lineanueva");
        Map<String, Object> map = new HashMap<>();
        map.put("5", "comment55");

        //  userReference.child("list").child(list_id).updateChildren(map);

    }




    private void anadenuevoVaLUE(){
        String stringagregar=generandoDataToList("listaUser2",generfecha(),"Enviado",Variables.montoAtransferirse,Variables.nombreyApellidoReceptor);
        DatabaseReference  mDatabase2 = FirebaseDatabase.getInstance().getReference();

       // Log.i("pathdebuge","el path recpetor es "+ pathEmisor);
        DatabaseReference userReference = mDatabase2.child("transacciones/"+Variables.pathEmisor);

        String key = userReference.push().getKey();

        Map<String, Object> map2 = new HashMap<>();

        map2.put(key, stringagregar);

        userReference.updateChildren(map2);

    }





    private void anadenuevoVaLUERecpetor(){

        String stringagregar=generandoDataToList("listaUser2",generfecha(),"Recibido",Variables.montoAtransferirse,Variables.nombreyApellidoEmisor);
        DatabaseReference  mDatabase2 = FirebaseDatabase.getInstance().getReference();
        // Log.i("pathdebuge","el path recpetor es "+ pathEmisor);
        DatabaseReference userReference = mDatabase2.child("transacciones/"+Variables.pathReceptor);
        String key = userReference.push().getKey();
        Map<String, Object> map2 = new HashMap<>();
        map2.put(key, stringagregar);
        userReference.updateChildren(map2);

    }



public void recuperadataunavezNodeReceptor(){

    DatabaseReference  mDatabase2 = FirebaseDatabase.getInstance().getReference(); //desctivar este
    DatabaseReference userReference = mDatabase2.child("transacciones");

    userReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if ((dataSnapshot.hasChild(Variables.pathReceptor) && !seanadioValues2)) {
                secreonodoReceptor = true;
                seanadioValues2 = true;
                anadenuevoVaLUERecpetor();

            } else { //si no existe..le anadimos la data a esta lista

                if (!secreonodoReceptor) {
                    //si no le pasamos esta lista...
                    Map<String, Object> map2 = new HashMap<>();

                    creaNodeRcpetorx(map2);

                }


            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            // ...
        }
    });

}




}
