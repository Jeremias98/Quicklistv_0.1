package com.quicklistv_01.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicklistv_01.Class.AlumnoAsistencia;
import com.quicklistv_01.R;
import com.quicklistv_01.SegundaPantallas.ListaAsistencia;

import java.util.List;

public class ListaAsistenciaAdapter extends RecyclerView.Adapter<ListaAsistenciaAdapter.ListasViewHolder> {
    private List<AlumnoAsistencia> asistencias;
    private final Context context;

    public ListaAsistenciaAdapter( List<AlumnoAsistencia> asistencias, Context context) {
        this.context = context;
        this.asistencias = asistencias;
    }

    @Override
    public ListasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.asistencia_list,parent,false);
        return new ListasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListasViewHolder holder, int position) {
        AlumnoAsistencia alumno = asistencias.get(position);
        holder.alumno.setText(alumno.getAlumno());
        holder.asistencia.setText(alumno.getAsistencia());

    }

    @Override
    public int getItemCount() {
        return asistencias.size();
    }

    public static class ListasViewHolder extends RecyclerView.ViewHolder {
        private TextView asistencia, alumno;

        public ListasViewHolder(View itemView) {
            super(itemView);
            alumno = (TextView) itemView.findViewById(R.id.tvAlumno);
            asistencia = (TextView) itemView.findViewById(R.id.tvAsistencia);
        }
    }
}
