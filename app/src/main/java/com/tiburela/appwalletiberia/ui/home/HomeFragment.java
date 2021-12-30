package com.tiburela.appwalletiberia.ui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.FragmetCamara;
import com.tiburela.appwalletiberia.R;
import com.tiburela.appwalletiberia.UsuarioCliente;
import com.tiburela.appwalletiberia.ui.Configuraciones.Dialogok;
import com.tiburela.appwalletiberia.ui.transaccion.TransaccionFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment implements View.OnClickListener , TextWatcher {

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


        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.home_fragment, container, false);




        inicializaViews();


        AgregaDataToList();

      //  iniiclizaadapter();

      //  mDatabase2 = FirebaseDatabase.getInstance().getReference();



        eventobtn();

      //  searchView.setIconifiedByDefault(false);
        ///oculñtamos icono de busqueda



        return root;
    }

    private void inicializaViews() {

        searchView = (SearchView) root.findViewById(R.id.searchView);
        listView = (ListView) root.findViewById(R.id.lv1);

        pagarqrCode2= root.findViewById(R.id.pagarqrCode2);

        sendBtn=root.findViewById(R.id.sendBtn);


        sendBtn.setEnabled(false);



    }


    @Override
    public void onStart() {

        super.onStart();
    }





    @Override
    public void onResume() {
        super.onResume();


        if(Variables.transaccionexistosa) {

            Dialogok dialog = new Dialogok();
            dialog.show(getActivity().getSupportFragmentManager(), "image_dialogx");

            Variables.transaccionexistosa=false;


        }






        Log.i("taskdata","se ejecuto onresumne");




       mDatabase2 = FirebaseDatabase.getInstance().getReference();

       iniiclizaadapter();


        obtenUusariosList();


        listView.setVisibility(ListView.INVISIBLE);


        dataUsuarioEnvia(generaPathByMail(Variables.correoThisUserand_Emisor));








        TransaccionFragment homef=new TransaccionFragment();

        FragmentManager manager=getActivity().getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.nav_host_fragment);


        if (homef != null) {  //SI NO ES NULLO


            manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.remove(homef);
            transaction.commit();



        }





        //   fl.removeAllViews();
        //    LoginaAffterLinkSendFr mFragment= new LoginaAffterLinkSendFr(); //la remplazamos..
        //    FragmentManager fragmentManager = getSupportFragmentManager();


    }




    @Override
    public void onPause() {
        super.onPause();

    }







    @Override
    public void onClick(View view) {
        int tag= (int) view.getTag();


    }





    /***
     *crear las transacciones.......... inserta el numero telefonico ,correo o sleiona un usuario para enviar dolares...
     *
     * buscamos el correo en nuestra base de datos y lño actualizamos....
     *
     *
     */



    private void habreFragment() {

        FrameLayout fl = (FrameLayout) root.findViewById(R.id.nav_host_fragment); //eliminamos la vista

        if(fl != null) {
            fl.removeAllViews();

        }






        TransaccionFragment mFragment= new TransaccionFragment(); //la remplazamos..

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




    private boolean validacampos(){

        Boolean estamoslistos =true;



        if( correoActualToSend.length() ==0 ){


            estamoslistos=false;

        }


        else if (!Patterns.EMAIL_ADDRESS.matcher(correoActualToSend).matches()){
            estamoslistos=false;


        }

        else if(!checkifmailexist(correoActualToSend)){ //si el usuario existe

            Toast.makeText(getActivity(), "No existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

            estamoslistos=false;

        }


        return estamoslistos;


    }



    private boolean checkifmailexist(String mail){


        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.fetchSignInMethodsForEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean isNewUser=false;

                        try {

                         isNewUser = task.getResult().getSignInMethods().isEmpty();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }




                        if (isNewUser) {
                            existeMail=false;


                            //   Toast.makeText(getActivity(), "No existe una cuenta con este correo", Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "Is New User!");
                        }

                        else {

                            //usuario que recibe los dolares....




                            existeMail=true;





                        }

                    }
                });


        return existeMail;

    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private void iniiclizaadapter(){

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String item = (String) listView.getItemAtPosition(position);

              //  int posicion=listView.getCount();
                int posicion2=listView.getPositionForView(view);

            //    Toast.makeText(getActivity(),"tu posicion 1 es  : " + posicion,Toast.LENGTH_SHORT).show();

              //  Toast.makeText(getActivity(),"tu poscion 2 es : " + posicion2,Toast.LENGTH_SHORT).show();

                //da valores a los nombres


                Variables. correoDestinatario=list2Mails.get(posicion2);


                correoActualToSend =list2Mails.get(posicion2);


                Log.i("degubegofrezer","se llamo setOnItemClickListener");

                if( validacampos()){//si esta todo en orden mostramos un dialog fragment.....y le pasamo el correo ,si el usuario cumple los requistos para enviar dolares..

                    // habredialog();
                    habreFragment() ;
                    //el correo


                }else{

                    Toast.makeText(getActivity(), "lo siento, no puedes hacer esta transaccion", Toast.LENGTH_SHORT).show();

                }







            }
        });



        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listView.setVisibility(ListView.VISIBLE);

              //  searchView.setIconifiedByDefault(false);


            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //your code here
                listView.setVisibility(ListView.INVISIBLE);



                return false;
            }
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //cuando el usuario p`resiona mostramos esta lista...

               // Toast.makeText(getActivity(), "a presionado este ",Toast.LENGTH_LONG).show();






                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(getActivity(), "No encontrado",Toast.LENGTH_LONG).show();
                }
                return false;
            }







            @Override
            public boolean onQueryTextChange(String newText) {
                   adapter.getFilter().filter(newText);



                       if( Patterns.EMAIL_ADDRESS.matcher(newText).matches() ) {

                            //cuando tengamos el formato del correo .activamos el boton..


                           sendBtn.setTextColor(Color.parseColor("#ffffff"));
                           sendBtn.setBackgroundResource(R.drawable.activado_btn);

                           sendBtn.setEnabled(true);


                       }
                       else{


                           sendBtn.setTextColor(Color.parseColor("#FFC5C4C4"));
                           sendBtn.setBackgroundResource(R.drawable.descativado_btn);
                           sendBtn.setEnabled(false);

                          // searchView.setIconifiedByDefault(true);



                       }





                return false;
            }
        });
    }









    private void AgregaDataToList(){ //y tambien iniciliza adpater

        list=new ArrayList<>();
        list.add("Apple");
        list.add("Banana");



        list2Mails=new ArrayList<>();
        list2Mails.add("Apple");
        list2Mails.add("Banana");

        }



        //PRIVATE VOID OBTNEMOS NOMBRES Y APELLIDOS Y LO PONEMOS UNA LIST..
    private void obtenUusariosList(){


        mDatabase2 = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userReference = mDatabase2.child("Data Users/users");



        //        displayUserDetails(userReference);
      //  userReference.addListenerForSingleValueEvent(new ValueEventListener() { //si onfunciona este

        ValueEventListener postListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 list = new ArrayList<>();
                 list2Mails=new ArrayList<>();

                list.clear();
                list2Mails.clear();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    UsuarioCliente Result = ds.getValue(UsuarioCliente.class);

                    String nombre = String.valueOf(Result.nombre);
                    String apellido = String.valueOf(Result.apellido);
                    String email= String.valueOf(Result.correoElectronico);

                    Log.i("ladtaesrr", "la data es esta " + nombre);
                    Log.i("ladtaesrr", "la data es esta " + apellido);

                    list.add(nombre+" "+apellido);

                    list2Mails.add(email);
                }



                if (getActivity()!=null){
                    adapter = new  ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                }



                 //  adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
               // listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        userReference.addValueEventListener(postListener); //psoiblemnte lo eliminemos

    }


    private void eventobtn(){
        pagarqrCode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                FrameLayout fl = (FrameLayout) root.findViewById(R.id.nav_host_fragment); //eliminamos la vista

                if(fl!= null) {
                    fl.removeAllViews();

                }

                //  FrameLayout fl = (FrameLayout) view.findViewById(R.id.nav_host_fragment); //eliminamos la vista
                //   fl.removeAllViews();
                FragmetCamara mFragment= new FragmetCamara(); //la remplazamos..
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                //   Bundle bundle = new Bundle();
                //  bundle.putString("user", email);
                //   bundle.putString("pasword", contrasena_string);


// set Fragmentclass Arguments
                //  mFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, mFragment).commit();


            }
        });







        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenmemos el correo a quien se enviara..

                Variables. correoDestinatario=searchView.getQuery().toString();

                Variables.UserFormateadoReceptor=searchView.getQuery().toString();



                if( validacampos()){//si esta todo en orden mostramos un dialog fragment.....y le pasamo el correo ,si el usuario cumple los requistos para enviar dolares..

                    // habredialog();
                    habreFragment() ;
                    //el correo


                }else{

                    Toast.makeText(getActivity(), "Lo sentimos no puedes realizar esta transaccion", Toast.LENGTH_SHORT).show();

                }







            }
        });

    }



//estalecenmos hint

private void dffd() {




}


    private void dataUsuarioEnvia(String userNodeEnvia){

        DatabaseReference collection = FirebaseDatabase.getInstance().getReference("Data Users/users/"+userNodeEnvia);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                UsuarioCliente post = dataSnapshot.getValue(UsuarioCliente.class);
                // ..
                totalCuentaQueEnvia=post.saldoActual;

                muestraSladoActual();

                // String dfdf= post.nombre;
                Log.i("sgdfgdf","el valor es "+totalCuentaQueEnvia );
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        collection.addValueEventListener(postListener);
    }


    private void muestraSladoActual(){

        TextView textSaldoAqui=root.findViewById(R.id.textSaldoActual);
        //muestra el saldo actual redondeado
       // double valorredondeado=Math.round(totalCuentaQueEnvia * 100.0) / 100.0;
        //textSaldoAqui.setText(String.valueOf(valorredondeado));

        String cantidad= NumberFormat.getCurrencyInstance(Locale.US).format(totalCuentaQueEnvia);

        textSaldoAqui.setText(cantidad);

    }


    private String generaPathByMail(String correoAqui) {

        String terminado=correoAqui.replaceAll("@", "0101010101010101");
        String String2terminado=terminado.replaceAll("\\.", "0101010101010");

        return String2terminado;


    }



}