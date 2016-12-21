package com.quicklistv_01.SegundaPantallas;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.quicklistv_01.Adapters.StatsAdapter;
import com.quicklistv_01.Fragments.MensualFragment;
import com.quicklistv_01.Fragments.SemanalFragment;
import com.quicklistv_01.R;

public class StatsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private StatsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_stats);
        SetearToolbar();
        CrearPestañas();


    }
    public void CrearPestañas(){
        //Casteando los objetos
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.vpStats);
        //instanciando el adaptador
        adapter = new StatsAdapter(getSupportFragmentManager());
        //Agregandole fragments al ViewPager
        adapter.addFragments(new SemanalFragment(), "Semanal");
        adapter.addFragments(new MensualFragment(), "Mensual");
        //Seteando adaptador
        viewPager.setAdapter(adapter);
        //Seteando ViewPager al TabLayout
        tabLayout.setupWithViewPager(viewPager);
        //Estableciendo color del texto.
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.white));
    }
    public void SetearToolbar(){
        //Obtenemos data del intent
        Intent i = getIntent();
        final String name = i.getStringExtra("Curso");
        //Seteamos nombre del curso en la toolbar
        getSupportActionBar().setTitle(name);

    }
}
