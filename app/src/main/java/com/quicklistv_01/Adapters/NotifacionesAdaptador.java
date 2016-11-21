package com.quicklistv_01.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicklistv_01.Class.Global;
import com.quicklistv_01.Class.NotificacionesClass;
import com.quicklistv_01.R;
import com.quicklistv_01.SegundaPantallas.AlumnosDetail;

import java.util.List;


public class NotifacionesAdaptador extends RecyclerView.Adapter<NotifacionesAdaptador.notificacionesViewHolder>
        implements RecyclerItemClickListener.OnItemClickListener, ItemClickListenerNotificaciones{

    private List<NotificacionesClass> alumnos;
    private final Context context;
    private Global globalData;


    public NotifacionesAdaptador(Context context, List<NotificacionesClass> alumnos) {
        this.context = context;
        this.alumnos = alumnos;
        globalData = (Global) context.getApplicationContext();
    }

    public static class notificacionesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private TextView tvAlumno;
        public ItemClickListenerNotificaciones listener;

        public notificacionesViewHolder(View itemView, ItemClickListenerNotificaciones listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            tvAlumno = (TextView) itemView.findViewById(R.id.tvAlumno);

        }


        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }

    }






    @Override
    public notificacionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alumnos_list, parent, false);
        return new NotifacionesAdaptador.notificacionesViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(NotifacionesAdaptador.notificacionesViewHolder holder, int position) {

        NotificacionesClass alumno = alumnos.get(position);
        holder.tvAlumno.setText(alumno.getNombre());

    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }
    @Override
    public void onItemClick(View view, int position) {
        AlumnosDetail.createInstancealtern(
                (Activity) context, alumnos.get(position), view);

    }





}
interface ItemClickListenerNotificaciones{
    void onItemClick(View view, int position);
}
