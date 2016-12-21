package com.quicklistv_01.Adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.Fragments.FinalLista;
import com.quicklistv_01.Fragments.TomaAlumno;

import java.util.ArrayList;
import java.util.List;

public class AlumnosViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> nombres = new ArrayList<>();
    private List<Alumno> alumnos = new ArrayList<>();
    private Integer cantidadAlumnos = 0;

    private int pages;

    private ArrayList<Integer> ids;
    private Context context;

    public AlumnosViewPagerAdapter(android.support.v4.app.FragmentManager fm, Context context, ArrayList<Integer> ids, ArrayList<String> names) {

        super(fm);

        for (Integer i : ids) {
            cantidadAlumnos += 1;
        }

        this.ids = ids;
        this.nombres = names;
        // Se agrega al final un valor nulo para saber que es el final
        this.nombres.add(names.size(), null);
        this.context = context;

        pages = getCount();
        Log.d("Dato", "del pages" + pages);

    }

    @Override
    public int getCount() {

        return nombres.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return nombres.get(position);

    }

    @Override
    public Fragment getItem(int position) {

        if (nombres.get(position) == null) {
            Fragment fragment1 = new FinalLista();
            return fragment1;
        }
        else {
            Fragment fragment = new TomaAlumno();
            Bundle args = new Bundle();
            args.putString(TomaAlumno.ARG_SECTION_NAME, getPageTitle(position).toString());
            fragment.setArguments(args);
            Log.d("Dato", "del GetItem" + position);
            return fragment;
        }

    }

    public Integer getAlumnoId(Integer position) {
        return ids.get(position);
    }

    public List<Alumno> getAlumnosAsistencia() {
        return this.alumnos;
    }

    public void setAsistencia(Integer id, Integer asistencia) {
        this.alumnos.add(new Alumno(id, asistencia));
    }

    public String getAsistencia(Integer currentItem) {

        String asistencia = "";

        if (this.alumnos.size() > 0) {
            asistencia = this.alumnos.get(currentItem).getAsistencia().toString();
        }

        return asistencia;

    }

    public Integer getAssist(Integer currentItem){


        Integer asistencia = 0;
        if (this.alumnos.size() > 0){
            asistencia = this.alumnos.get(currentItem).getAsistencia();
        }
        Log.d("TAG" , "---DATA ASISTENCIA --- " + asistencia.toString() + " DEL ITEM : " + currentItem.toString());
        return asistencia;
    }

    public Integer getCantidadAlumnos() {
        return cantidadAlumnos;
    }
}
