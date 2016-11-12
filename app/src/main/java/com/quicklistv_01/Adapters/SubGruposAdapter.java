package com.quicklistv_01.Adapters;

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

import com.quicklistv_01.Class.SubGrupos;
import com.quicklistv_01.R;

import java.util.List;


public class SubGruposAdapter extends RecyclerView.Adapter<SubGruposAdapter.subGruposViewHolder> implements RecyclerItemClickListener.OnItemClickListener,ItemClickListener {

    private List<SubGrupos> grupos;
    boolean isPressed=false;
    private final Context context;

    public SubGruposAdapter(List<SubGrupos> grupos, Context context) {
        this.grupos = grupos;
        this.context = context;
    }



    public static class subGruposViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private TextView tvGrupo;
        public ItemClickListener listener;
        private ImageButton imageButton;
        private PopupMenu popupMenu;


        public subGruposViewHolder(View itemView,ItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            tvGrupo = (TextView) itemView.findViewById(R.id.tvGrupo);
            this.imageButton = (ImageButton) itemView.findViewById(R.id.fav);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }

    }
    @Override
    public subGruposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grupos_list, parent, false);
        return new subGruposViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(final SubGruposAdapter.subGruposViewHolder holder, int position) {

        SubGrupos grupo = grupos.get(position);

        holder.tvGrupo.setText(grupo.getSubgrupo());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.popupMenu = new PopupMenu(v.getContext(), v);
                createMenu(holder.popupMenu.getMenu());

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle click here
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //handle long click here
                return false;
            }
        });


    }
    public void createMenu(Menu menu){
        menu.add("Tomar asistencia")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // do whatever you want
                        return false;
                    }
                });
        menu.add("Editar grupo")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // do whatever you want
                        return false;
                    }
                });
        menu.add("Eliminar grupo")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // do whatever you want
                        return false;
                    }
                });

    }

    @Override
    public int getItemCount() {
        return grupos.size();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
