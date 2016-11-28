package com.quicklistv_01.SegundaPantallas;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.quicklistv_01.Adapters.AlumnosAdaptador;
import com.quicklistv_01.Adapters.ListaAsistenciaAdapter;
import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.Class.AlumnoAsistencia;
import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.R;

import java.util.ArrayList;
import java.util.List;


public class ListaAsistencia extends AppCompatActivity {
    RecyclerView rvLista;
    private List<AlumnoAsistencia> alumnos;
    private Global globalData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asistencia);
        globalData = (Global) getApplicationContext();
        rvLista = (RecyclerView) findViewById(R.id.rvLista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iniciarDatos();
        iniciarAdaptador();
        Intent i = getIntent();
        ActionBar actionBar = getSupportActionBar();
        final String nombre = i.getStringExtra("Nombre");
        actionBar.setTitle(nombre);

        LinearLayoutManager llm = new LinearLayoutManager(ListaAsistencia.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvLista.setLayoutManager(llm);
        rvLista.addItemDecoration(new DividerItemDecoration(getBaseContext()));
        rvLista.addItemDecoration( new DividerItemDecoration(ListaAsistencia.this, R.drawable.barra));
    }


    public void iniciarDatos(){

        alumnos = new ArrayList<>();
        alumnos.add(new AlumnoAsistencia(1, "Aaron", "Tarde"));
        alumnos.add(new AlumnoAsistencia(2, "Martin", "Presente"));

    }

    public ListaAsistenciaAdapter adaptador;

    private void iniciarAdaptador(){
        adaptador = new ListaAsistenciaAdapter( alumnos, getApplicationContext());
        rvLista.setAdapter(adaptador);
    }




    public static void createConsultaIntance(Activity activity, Curso title, View view) {
        Intent intent = getLaunchIntent(activity, title);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, new Pair<View, String>(view.findViewById(R.id.tvCurso),
                    "trans_animation"));
            android.support.v13.app.ActivityCompat.startActivity(activity, intent, options.toBundle());
        }
        else{
            activity.startActivity(intent);
        }
    }


    public static Intent getLaunchIntent(Context context, Curso curso) {
        Intent intent = new Intent(context, ListaAsistencia.class);
        intent.putExtra("Nombre", curso.getNombre());
        intent.putExtra("ID", curso.getId());
        return intent;
    }
}
