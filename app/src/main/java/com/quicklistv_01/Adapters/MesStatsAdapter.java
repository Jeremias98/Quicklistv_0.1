package com.quicklistv_01.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicklistv_01.Class.Meses;
import com.quicklistv_01.R;

import java.util.ArrayList;
import java.util.List;

public class MesStatsAdapter extends RecyclerView.Adapter<MesStatsAdapter.mesStatsViewHolder>
        implements RecyclerItemClickListener.OnItemClickListener, ItemClickMeses {


    private final Context context;
    private List<Meses> mes = new ArrayList<>();

    //Constructor donde le pasas los datos
    public MesStatsAdapter(Context context, List<Meses> mes) {
        this.context = context;
        this.mes = mes;
    }
    //Esto crea la vista del objeto xml que le pases.
    @Override
    public mesStatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meses_list, parent, false);
        return new mesStatsViewHolder(v, this);
    }
    //Este metodo es llamas el ViewHolder de arriba para obtener la vista y definir los meses
    @Override
    public void onBindViewHolder(mesStatsViewHolder holder, int position) {
        Meses meses = mes.get(position);
        holder.meses.setText(meses.getMeses());

    }
    //Este metodo indica cuantos elementos va a tener
    @Override
    public int getItemCount() {
        return mes.size();
    }


    @Override
    public void onItemClick(View view, int position) {


    }
    //El viewholder lo que hace s basicamente contruir los elementos del recycler.
    public static class mesStatsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView meses;
        private ItemClickMeses itemClickMeses; //Instanciamos el clicListener

        public mesStatsViewHolder(View itemView, ItemClickMeses listener) {
            super(itemView);
            this.itemClickMeses = listener;
            meses = (TextView) itemView.findViewById(R.id.tvMes);
            itemView.setOnClickListener(this);//Seteamos el clickListener


        }

        @Override
        public void onClick(View view) {
            itemClickMeses.onItemClick(view, getAdapterPosition());

        }
    }


}

interface ItemClickMeses {
    void onItemClick(View view, int position);
}
