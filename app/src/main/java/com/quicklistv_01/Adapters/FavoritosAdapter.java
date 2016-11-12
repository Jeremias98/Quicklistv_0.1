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
import com.quicklistv_01.Class.ListaFavoritos;
import com.quicklistv_01.CursosDetail;
import com.quicklistv_01.R;
import com.quicklistv_01.SegundaPantallas.TomaAsistencia;
import com.quicklistv_01.SegundaPantallas.VerSubGrupos;

import java.util.List;



public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.favoritosViewHolder>
        implements RecyclerItemClickListener.OnItemClickListener, ItemClickListener {


        private List<Curso> cursos;
        boolean isPressed = false;
        private final Context context;

        public static class favoritosViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            private TextView tvCurso;
            public com.quicklistv_01.Adapters.ItemClickListener listener;
            private ImageButton imageButton;
            private PopupMenu popupMenu;

            public favoritosViewHolder(View itemView, ItemClickListener listener) {
                super(itemView);
                this.listener = listener;
                tvCurso = (TextView) itemView.findViewById(R.id.tvCurso);

                int position = getAdapterPosition();
                this.imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
                itemView.setOnClickListener(this);

            }
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, getAdapterPosition());
            }
        }


        public FavoritosAdapter(List<Curso> cursos, Context context) {
            this.cursos = cursos;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return cursos.size();
        }



        @Override
        public favoritosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cursos_list, viewGroup, false);
            return new favoritosViewHolder(v, this);
        }



    @Override
        public void onBindViewHolder(final favoritosViewHolder holder, int position) {
            Curso curso = cursos.get(position);

            holder.tvCurso.setText(curso.getNombre());
            final String  data = holder.tvCurso.getText().toString();
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.popupMenu = new PopupMenu(v.getContext(), v);
                    createMenu(holder.popupMenu.getMenu(), data);

                    holder.popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                        @Override
                        public void onDismiss(PopupMenu menu) {
                            holder.popupMenu = null;
                        }
                    });
                    holder.popupMenu.show();

                }

            });
            if (holder.popupMenu != null) {
                holder.popupMenu.dismiss();
            }



        }
    public void createMenu(Menu menu, final String data){
        menu.add("Tomar asistencia")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        TomaAsistencia.createInstancealtern(
                                (Activity) context);
                        return false;
                    }
                });
        menu.add("Eliminar de favoritos")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ListaFavoritos db = new ListaFavoritos(context);
                        db.eliminar(data);
                        return false;
                    }
                });
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

