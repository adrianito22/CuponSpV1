package com.tiburela.appwalletiberia.ui.transaccionesListView;

import static com.tiburela.appwalletiberia.DataFirerbase.Variables.pathEmisor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.appwalletiberia.DataFirerbase.Variables;
import com.tiburela.appwalletiberia.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class TransaccionesFragment extends Fragment {
  View root;
    RecyclerView recyclerPersonajes;
    private AdaptadorPersonajes adapter;
    List<String> milista;
    int id =0;
    int indice0=0;

    TextView textonadaporaqui;

    ArrayList<String> listadata;
 private final String separador="+";

    ArrayList<ItemHomeModel> lista_lecciones_items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.lista_partidas_creadas, container, false);


        recyclerPersonajes = root.findViewById(R.id.RecyclerId);

        textonadaporaqui = root.findViewById(R.id.textonadaporaqui);

        inicilizalistas();

        obtenUusariosList();

     //   llenaData();




        return root;



    }


///obtenemos data de lista



    private void obtenUusariosList(){

        milista =new ArrayList<String>();

        /**
         * parecq q una no es necesaria
         * */

        DatabaseReference  mDatabase2 = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userReference = mDatabase2.child("transacciones/"+Variables.mailFormtToFrtransacc);


        Log.i("somomasladataesdf","el path consultado es  "+Variables.mailFormtToFrtransacc);


//        Log.i("vlaoreshaffy",Variables.pathEmisor);

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<HashMap<String, String>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, String>>() {};
                Map<String, String> objectHashMap = dataSnapshot.getValue(objectsGTypeInd);


                 if(objectHashMap==null || objectHashMap.size()==0 ) {
                                Log.i("ladataesdf","el size  es nulo y es igual a  0");

                     textonadaporaqui.setVisibility(TextView.VISIBLE);



                 }else{ //SI HAY DATA
                     Log.i("ladataesdf","el size no es nulo y es mayor a 0");


                     ArrayList<String> objectArrayList = new ArrayList<String>(objectHashMap.values());

                     Log.i("TAGgffg","el size es "+objectArrayList.size());   //gives the value for given keyname
                     Log.i("TAGgffg","elvalue de exte indice es "+objectArrayList.get(0));   //gives the value for given keyname

                     for (int indice =0; indice < objectArrayList.size(); indice++) {

                         String wordStr = objectArrayList.get(indice);

                         Log.i("contadorbucle","se ejecuto este bucle");

                         Log.i("zzzzxfdsd","la data es "+wordStr);


                         if (wordStr.contains(",")) {

                             String[] one = wordStr.split(Pattern.quote(","));
                             //    public ItemHomeModel(String id_value, String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia) {

                             lista_lecciones_items.add(new ItemHomeModel(one[0], one[1], one[2], Double.parseDouble(one[3]), one[4],one[4]));

                         }

                         contruirRecicler();


                     }


                 }







              /*
                if (getActivity()!=null){
                    adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                }
*/


                //  adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
                // listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        userReference.addValueEventListener(postListener); //psoiblemnte lo eliminemos

    }



    private void contruirRecicler(){




        adapter = new AdaptadorPersonajes(lista_lecciones_items, getActivity());


        Log.i("xxxitemdesczzz","el tamano de lista lecciones  "+ lista_lecciones_items.size());


        // initializing our adapter class with our array list and context.
        //   recyclerViewAdapter = new AdaptadorPersonajes(lista_lecciones_items, getActivity());

        // below line is to set layout manager for our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        // setting layout manager for our recycler view.
        recyclerPersonajes.setLayoutManager(manager);

        // below line is to set adapter
        // to our recycler view.




        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Selección: ", Toast.LENGTH_SHORT).show();

                int indice_selecionado = recyclerPersonajes.getChildAdapterPosition(view);

                // String tag = ((TextView) recyclerPersonajes.findViewHolderForAdapterPosition(indice_selecionado).itemView.findViewById(R.id.id_nombre_partida)).getTag().toString();

            //    String tagPertenecePertenece = lista_lecciones_items.get(recyclerPersonajes.getChildAdapterPosition(view)).getTag_pertenece();
            //    String id_current = lista_lecciones_items.get(recyclerPersonajes.getChildAdapterPosition(view)).getId_value();
             //   String estado_current = lista_lecciones_items.get(recyclerPersonajes.getChildAdapterPosition(view)).getEstado_Partida_current();
            //    int indice_bydefult_Mode_item = lista_lecciones_items.get(recyclerPersonajes.getChildAdapterPosition(view)).getIndexbyDefaultMode();


              //  determina_aacual_pertenece(tagPertenecePertenece, id_current, estado_current, indice_bydefult_Mode_item);


                /*
                view=recyclerPersonajes.getChildAt(indice_selecionado); // This will give you entire row(child) from RecyclerView
                if(view!=null)
                {
                }
*/


            }
        });


        recyclerPersonajes.setAdapter(adapter);


    }



    //vamos a ddarle un id buscando el id actual..

    private void generaIdNameNode(){
        // idNodeName



    }

    private void generaIDnaME(){

        DatabaseReference  mDatabase2 = FirebaseDatabase.getInstance().getReference();



        DatabaseReference userReference = mDatabase2.child("Data Users/transacciones/"+ Variables.pathReceptor);



        ValueEventListener postListener = new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                for (DataSnapshot  item_snapshot : dataSnapshot.getChildren()) {

                    TransacionesClass resultObj = item_snapshot.getValue(TransacionesClass.class);

                    ItemHomeModel resultObj2 = item_snapshot.getValue(ItemHomeModel.class);



                    Log.d("item id",item_snapshot.child("id"+id).getValue().toString());




                    //  for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {


                    String id2 = resultObj2.id_value;
                    String fecha2 =resultObj2.fecha;
                    String enviaorecibe2= resultObj2.enviaorecibe;
                    double transaccionValor2 = resultObj2.transaccionValor;
                    String nombreRecibe2 =resultObj2.nombreRecibe;
                    String nombreEnvia2= resultObj2.nombreEnvia;

                    //      public ItemHomeModel(String id, String fecha, String enviaorecibe, double transaccionValor, String nombreRecibe, String nombreEnvia) {

                    String id = resultObj.id;
                    String fecha =resultObj.fecha;
                    String enviaorecibe= resultObj.enviaorecibe;
                    double transaccionValor = resultObj.transaccionValor;
                    String nombreRecibe =resultObj.nombreRecibe;
                    String nombreEnvia= resultObj.nombreEnvia;


                    listadata.add(id+separador+fecha+separador+enviaorecibe+separador+transaccionValor+separador+nombreRecibe+separador+nombreEnvia);

                    // lista_lecciones_items.add(new ItemHomeModel(id, fecha, enviaorecibe, transaccionValor, nombreRecibe, nombreEnvia));

                    lista_lecciones_items.add(new ItemHomeModel(id2, fecha2, enviaorecibe2, transaccionValor2, nombreRecibe2, nombreEnvia2));



                }




              /*
                if (getActivity()!=null){
                    adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                }
*/


                //  adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
                // listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        userReference.addValueEventListener(postListener); //psoiblemnte lo eliminemos

    }





  private void inicilizalistas(){
      lista_lecciones_items = new ArrayList<ItemHomeModel>();
      listadata=new ArrayList<String>(); //inbicilizamos lista



  }

    @Override
    public void onResume() {
        super.onResume();

        FrameLayout fl = (FrameLayout) root.findViewById(R.id.nav_host_fragment); //eliminamos la vista

        if(fl != null) {
            fl.removeAllViews();

        }
    }



}