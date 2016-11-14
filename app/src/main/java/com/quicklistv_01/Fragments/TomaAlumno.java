package com.quicklistv_01.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.quicklistv_01.Adapters.AlumnosViewPagerAdapter;
import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TomaAlumno extends Fragment {

    public List<Alumno> alumnoList = new ArrayList<>();

    private Button btn_presente;
    private Button btn_ausente;
    private Button btn_tarde;
    public ViewPager mViewPager;
    public AlumnosViewPagerAdapter adapter;

    // Variables globales
    private Global globalData;

    public static final String ARG_SECTION_NAME = "section_name";

    public TomaAlumno() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalData = (Global) getActivity().getApplication().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Bundle args = getArguments();

        View rootView = inflater.inflate(R.layout.fragment_toma_alumno, container, false);
        View view = inflater.inflate(R.layout.fragment_toma_asistencia, container, false);

        ((TextView) rootView.findViewById(R.id.nombre_apellido)).setText(
                args.getString(ARG_SECTION_NAME));

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ((TextView) rootView.findViewById(R.id.fecha)).setText(
                currentDateTimeString);

        String grupoActual = globalData.getNameCurrentGrupo();
        ((TextView) rootView.findViewById(R.id.curso)).setText(
                grupoActual);

        mViewPager = (ViewPager) getActivity().findViewById(R.id.container);

        adapter = (AlumnosViewPagerAdapter) mViewPager.getAdapter();

        btn_presente = (Button) rootView.findViewById(R.id.btn_presente);
        btn_ausente = (Button) rootView.findViewById(R.id.btn_ausente);
        btn_tarde = (Button) rootView.findViewById(R.id.btn_tarde);

        btn_presente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Presente", Toast.LENGTH_SHORT).show();
                adapter.setAsistencia(adapter.getAlumnoId(mViewPager.getCurrentItem()), 1);
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
        });

        btn_ausente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Ausente", Toast.LENGTH_SHORT).show();
                adapter.setAsistencia(adapter.getAlumnoId(mViewPager.getCurrentItem()), 2);
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
        });

        btn_tarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Tarde", Toast.LENGTH_SHORT).show();
                adapter.setAsistencia(adapter.getAlumnoId(mViewPager.getCurrentItem()), 3);
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
        });

        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
