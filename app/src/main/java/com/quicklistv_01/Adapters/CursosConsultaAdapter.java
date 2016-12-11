package com.quicklistv_01.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.R;
import com.quicklistv_01.SegundaPantallas.ListaAsistencia;

import java.util.List;

public class CursosConsultaAdapter extends RecyclerView.Adapter<CursosConsultaAdapter.cursosConsultaViewHolder>
        implements RecyclerItemClickListener.OnItemClickListener, ItemClickConsulta {

    private List<Curso> cursos;
    private final Context context;
    private Global globalData;

    public static class cursosConsultaViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView tvCurso;
        public ItemClickConsulta listener;
        private ImageButton imageButton;
        private PopupMenu popupMenu;

        public cursosConsultaViewHolder(View itemView, ItemClickConsulta listener) {
            super(itemView);
            this.listener = listener;
            tvCurso = (TextView) itemView.findViewById(R.id.tvCurso);
            this.imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            listener.onItemClick(v, getAdapterPosition());

        }
    }


    public CursosConsultaAdapter(List<Curso> cursos, Context context) {
        this.cursos = cursos;
        this.context = context;
        globalData = (Global) context.getApplicationContext();
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }


    @Override
    public cursosConsultaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cursos_list, viewGroup, false);
        return new cursosConsultaViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(final cursosConsultaViewHolder holder, final int position) {
        Curso curso = cursos.get(position);

        holder.tvCurso.setText(curso.getNombre());
    }



    @Override
    public void onItemClick(View view, int position) {

        globalData.setIdCurrentGrupo(cursos.get(position).getId());
        globalData.setNameCurrentGrupo(cursos.get(position).getNombre());

        ListaAsistencia.createConsultaIntance(
                (Activity) context, cursos.get(position), view);


    }
}
interface ItemClickConsulta{
    void onItemClick(View view, int position);
}
