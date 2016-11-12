package com.quicklistv_01.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.R;

import java.util.List;

public class AlumnosAdaptador extends RecyclerView.Adapter<AlumnosAdaptador.alumnosViewHolder>
        implements RecyclerItemClickListener.OnItemClickListener, ItemClickListener {
    private List<Alumno> alumnos;
    boolean isPressed = false;
    private final Context context;

    public AlumnosAdaptador(List<Alumno> alumnos, Context context) {
        this.context = context;
        this.alumnos = alumnos;
    }



    @Override
    public alumnosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alumnos_list, parent, false);
        return new alumnosViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(alumnosViewHolder holder, int position) {
        Alumno alumno = alumnos.get(position);
        holder.tvAlumno.setText(alumno.getNombre());

    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public static class alumnosViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView tvAlumno;
        public ItemClickListener listener;

        public alumnosViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            tvAlumno = (TextView) itemView.findViewById(R.id.tvAlumno);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }

}

