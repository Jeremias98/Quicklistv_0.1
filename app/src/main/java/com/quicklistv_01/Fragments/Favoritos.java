package com.quicklistv_01.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicklistv_01.Adapters.FavoritosAdapter;
import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.CursosFav;
import com.quicklistv_01.Class.DBHelper;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.R;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends Fragment {
    public static Context context;

    public Favoritos() {
    }

    public FavoritosAdapter adaptador;
    TextView tvCurso;
    RecyclerView listaCursos;
    private List<CursosFav> curso;
    ArrayList<String> c = new ArrayList<>();
    String a[] = {};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favoritos, container, false);
        listaCursos = (RecyclerView) rootView.findViewById(R.id.rvFavoritos);
        tvCurso = (TextView) rootView.findViewById(R.id.tvCurso);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        inciarDatos();
        iniciarAdaptador();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaCursos.setLayoutManager(llm);

        listaCursos.setItemAnimator(new DefaultItemAnimator());
        listaCursos.addItemDecoration(new DividerItemDecoration(getContext()));
        listaCursos.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.barra));


        return rootView;
    }
    public void inciarDatos() {

        DBHelper helper = new DBHelper(getContext());
        c = helper.llenar();
        curso = new ArrayList<>();

        for (String nombre : a) {
            c.add(nombre);
        }
        for (String elemento : c) {
            curso.add(new CursosFav(elemento));

        }


    }

    private void iniciarAdaptador() {

        adaptador = new FavoritosAdapter(curso, getContext());
        listaCursos.setAdapter(adaptador);

    }


public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);
}


}
