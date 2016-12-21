package com.quicklistv_01.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.quicklistv_01.Adapters.SemanalAdapter;
import com.quicklistv_01.R;

import java.util.ArrayList;


public class SemanalFragment extends Fragment {

    static final ArrayList<String> nombres = new ArrayList<>();
    public SemanalFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_semanal, container, false);
        //Casteando objetos
        GridView gridView = (GridView) rootview.findViewById(R.id.gridView);


        AgregarValores("Aaron", 19, 3, 1);
        AgregarValores("Jeremias", 19, 4, 0);
        AgregarValores("Martin", 19, 0, 1);
        AgregarValores("Agustin", 5, 19, 1);
        IniciarGrid(gridView);

        return rootview;
    }

    public void IniciarGrid(GridView gridView){
        SemanalAdapter adapter = new SemanalAdapter(getActivity(), nombres);
        gridView.setAdapter(adapter);

    }
    public void AgregarValores (String nombres, Integer presentes, Integer ausentes, Integer tardes){
        this.nombres.add(nombres);
        this.nombres.add(presentes.toString());
        this.nombres.add(ausentes.toString());
        this.nombres.add(tardes.toString());
    }

}
