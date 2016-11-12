package com.quicklistv_01.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.CursosDetail;
import com.quicklistv_01.R;
import com.quicklistv_01.SegundaPantallas.TomaAsistencia;
import com.quicklistv_01.SegundaPantallas.VerSubGrupos;

import java.util.List;

public class CursoAdaptador extends RecyclerView.Adapter<CursoAdaptador.cursosViewHolder>
        implements RecyclerItemClickListener.OnItemClickListener, ItemClickListener {

    private List<Curso> cursos;
    private final Context context;

    public static class cursosViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView tvCurso;
        public ItemClickListener listener;
        private ImageButton imageButton;
        private PopupMenu popupMenu;

        public cursosViewHolder(View itemView, ItemClickListener listener) {
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


    public CursoAdaptador(List<Curso> cursos, Context context) {
        this.cursos = cursos;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }



    @Override
    public cursosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cursos_list, viewGroup, false);
        return new cursosViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(final cursosViewHolder cursosViewHolder, int position) {
       Curso curso = cursos.get(position);

        cursosViewHolder.tvCurso.setText(curso.getNombre());
        cursosViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursosViewHolder.popupMenu = new PopupMenu(v.getContext(), v);
                createMenu(cursosViewHolder.popupMenu.getMenu());

                cursosViewHolder.popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        cursosViewHolder.popupMenu = null;
                    }
                });
                cursosViewHolder.popupMenu.show();

            }

        });
        if (cursosViewHolder.popupMenu != null) {
            cursosViewHolder.popupMenu.dismiss();
        }






    }
    public void createMenu(Menu menu){
        menu.add("Tomar asistencia")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        TomaAsistencia.createInstancealtern(
                                (Activity) context);
                        return false;
                    }
                });
        /*menu.add("Agregar a favoritos")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent();

                        return false;
                    }
                });*/
        menu.add("Ver subgrupos")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        VerSubGrupos.createInstancealtern(
                                (Activity) context);
                        return false;
                    }
                });

    }



    @Override
    public void onItemClick(View view, int position) {

        CursosDetail.createInstancealtern(
                (Activity) context, cursos.get(position), view);


    }




}
interface ItemClickListener {
    void onItemClick(View view, int position);

}