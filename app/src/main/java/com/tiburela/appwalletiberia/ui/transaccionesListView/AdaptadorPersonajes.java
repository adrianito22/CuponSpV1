package com.tiburela.appwalletiberia.ui.transaccionesListView;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.tiburela.appwalletiberia.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class AdaptadorPersonajes
        extends RecyclerView.Adapter<AdaptadorPersonajes.ViewHolderPersonajes>
        implements View.OnClickListener{




    ArrayList<ItemHomeModel> listaPersonajes;
    private View.OnClickListener listener;

    public AdaptadorPersonajes(ArrayList<ItemHomeModel> listaPersonajes, FragmentActivity activity) {
        this.listaPersonajes = listaPersonajes;
    }



    @Override
    public ViewHolderPersonajes onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout=0;
        if (Utilidades.visualizacion==Utilidades.LIST){
            layout= R.layout.iem_list_lecciones_creadas;
        }

        View view= LayoutInflater.from(parent.getContext()).inflate(layout,null,false);

        view.setOnClickListener(this);

        return new ViewHolderPersonajes(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPersonajes holder, int position) {

        holder.enviaorecibe.setText(listaPersonajes.get(position).fecha);


       // holder.etiNombre.setText(listaPersonajes.get(position).fecha);


       // String cantidad="$ "+String.valueOf(listaPersonajes.get(position).transaccionValor);
        String cantidad= NumberFormat.getCurrencyInstance(Locale.US).format(listaPersonajes.get(position).transaccionValor);


        if(listaPersonajes.get(position).enviaorecibe.equals("Recibido")) {

            Log.i("zzxladataesdf","este es reicibido");

            holder.etiNombre.setText(listaPersonajes.get(position).nombreRecibe);
            holder.imgvIconSendOrecibe.setImageResource(R.drawable.recibido_icon);

            holder.iconotext.setText("+");

            holder.txt_cantidad_aqui.setText(cantidad);
            holder.txt_cantidad_aqui.setTextColor(Color.parseColor("#018065"));

            holder.iconotext.setTextColor(Color.parseColor("#018065"));


        }else{

            Log.i("zzxladataesdf","este es enviado");


            // holder.imgvIconSendOrecibe.setImageResource(R.drawable.enviado_icon);
           // holder.txt_cantidad_aqui.setText(cantidad);
           // holder.iconotext.setText("-");

            holder.etiNombre.setText(listaPersonajes.get(position).nombreRecibe);

            holder.imgvIconSendOrecibe.setImageResource(R.drawable.ic_baseline_arrow_forward_24xx);
            holder.txt_cantidad_aqui.setText(cantidad);
            holder.txt_cantidad_aqui.setTextColor(Color.parseColor("#db0b0d"));
            holder.iconotext.setText("-");
            holder.iconotext.setTextColor(Color.parseColor("#db0b0d"));
            holder.iconotext.setTextColor(Color.parseColor("#db0b0d"));


        }


      //  holder.layout_xw.setBackgroundColor(Color.parseColor(listaPersonajes.get(position).getColor()));
       // holder.layout_xw.setBackgroundResource(listaPersonajes.get(position).getTransaccionValor());


        if (Utilidades.visualizacion==Utilidades.LIST){

           holder.fecha.setText(listaPersonajes.get(position).fecha);

            holder.enviaorecibe.setText(listaPersonajes.get(position).enviaorecibe);



        }


        //aqui le ponemos el valor al eklemento...
    // holder.progress.setImageResource(listaPersonajes.get(position).getFoto());
  // holder.progress.setProgress(listaPersonajes.get(position).getPorcentajede_JUEGO());
       /// holder.progress.setProgress(25);



    }

    @Override
    public int getItemCount() {
        return listaPersonajes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderPersonajes extends RecyclerView.ViewHolder {

        TextView etiNombre, fecha, enviaorecibe;
        ImageView imgvIconSendOrecibe;
       LinearLayout layout_xw;
      TextView  txt_cantidad_aqui;

        TextView  iconotext;

        public ViewHolderPersonajes(View itemView) {
            super(itemView);
            etiNombre= (TextView) itemView.findViewById(R.id.noombreReceptor);

            iconotext= (TextView) itemView.findViewById(R.id.iconotext);


            if (Utilidades.visualizacion==Utilidades.LIST){
                fecha = (TextView) itemView.findViewById(R.id.fecha);
                enviaorecibe =(TextView) itemView.findViewById(R.id.enviaorecibe);
                layout_xw=(LinearLayout) itemView.findViewById(R.id.layout_xw);
                imgvIconSendOrecibe=(ImageView) itemView.findViewById(R.id.imgvIconSendOrecibe);

                txt_cantidad_aqui=(TextView) itemView.findViewById(R.id.txt_cantidad_aqui);

            }




        }
    }
}
