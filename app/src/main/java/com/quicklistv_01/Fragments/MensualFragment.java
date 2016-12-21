package com.quicklistv_01.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quicklistv_01.Adapters.MesStatsAdapter;
import com.quicklistv_01.Class.Meses;
import com.quicklistv_01.R;

import java.util.ArrayList;
import java.util.List;

public class MensualFragment extends Fragment {


    private List<Meses> meses = new ArrayList<>();
    private RecyclerView rv;

    public MensualFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarMeses();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Inflando vista
        View holder = inflater.inflate(R.layout.fragment_mensual, container, false);

        //Casteando elementos
        rv = (RecyclerView) holder.findViewById(R.id.rvMeses);
        iniciarAdaptador();
        configurarAdaptador();
        SetearAdaptador();

        return holder;
    }


    private void cargarMeses() {
        meses.add(new Meses("Marzo"));
        meses.add(new Meses("Abril"));
        meses.add(new Meses("Mayo"));
        meses.add(new Meses("Junio"));
        meses.add(new Meses("Julio"));
        meses.add(new Meses("Agosto"));
        Log.d("TAG", "Cantidad de meses " + meses.size());

    }

    public void configurarAdaptador() {
        LinearLayoutManager lim = new LinearLayoutManager(getActivity());
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new com.quicklistv_01.Class.DividerItemDecoration(getContext()));
        rv.addItemDecoration(new com.quicklistv_01.Class.DividerItemDecoration(getActivity(), R.drawable.barra));
    }

    public MesStatsAdapter adapter;

    public void iniciarAdaptador() {
        adapter = new MesStatsAdapter(getContext(), meses);

    }

    public void SetearAdaptador() {
        rv.setAdapter(adapter);
    }


}
