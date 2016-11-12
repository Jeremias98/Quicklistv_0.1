package com.quicklistv_01.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.quicklistv_01.R;

public class FinalLista extends Fragment {

    public FinalLista() {
        // Required empty public constructor
    }

    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        btn = (Button) inflater.inflate(R.layout.fragment_final_lista, container, false).findViewById(R.id.btnFinalizar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Se enviaron los datos de la toma de asistencia.", Toast.LENGTH_SHORT).show();
                Log.d("Tag", "Button 1 was pressed");
            }
        });

        return inflater.inflate(R.layout.fragment_final_lista, container, false);
    }

}
