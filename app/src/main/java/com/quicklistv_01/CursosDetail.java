package com.quicklistv_01;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.CursosFav;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.Class.ListaFavoritos;
import com.quicklistv_01.Fragments.Cursos;
import com.quicklistv_01.SegundaPantallas.Alumnos;
import com.quicklistv_01.SegundaPantallas.CalendarioModificaciones;
import com.quicklistv_01.SegundaPantallas.StatsActivity;
import com.quicklistv_01.SegundaPantallas.VerSubGrupos;

public class CursosDetail extends AppCompatActivity {

    ImageButton btn_favo;
    private  String data;
    TextView curso;
    LinearLayout tomar, cambiar, crear, mostrar;

    // Variables globales
    private Global globalData;

    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos_detail);


        globalData = (Global) getApplicationContext();

        Intent i = getIntent();

        curso =(TextView) findViewById(R.id.curso);
        final String name = i.getStringExtra("Nombre");
        id = globalData.getIdCurrentGrupo();
        curso.setText(globalData.getNameCurrentGrupo());
        btn_favo = (ImageButton) findViewById(R.id.btn_favorito);
        tomar = (LinearLayout) findViewById(R.id.lyTomar);
        cambiar = (LinearLayout) findViewById(R.id.lyMod);
        crear = (LinearLayout) findViewById(R.id.lySub);
        mostrar = (LinearLayout) findViewById(R.id.lyStats);

        tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CursosDetail.this, Alumnos.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CursosDetail.this, new Pair<View, String>(findViewById(R.id.tomar),
                            "trans_animation"));
                    android.support.v13.app.ActivityCompat.startActivity(CursosDetail.this, intent, options.toBundle());
                }
                else{
                    //intent.putExtra("ID", id);
                    globalData.setIdCurrentGrupo(id);
                    globalData.setNameCurrentGrupo(name);
                    startActivity(intent);
                }

            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CursosDetail.this, VerSubGrupos.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CursosDetail.this, new Pair<View, String>(findViewById(R.id.tomar),
                            "trans_animation"));
                    android.support.v13.app.ActivityCompat.startActivity(CursosDetail.this, intent, options.toBundle());
                }
                else{
                    startActivity(intent);
                }




            }
        });
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CursosDetail.this, CalendarioModificaciones.class);
                intent.putExtra("Curso", name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CursosDetail.this, new Pair<View, String>(findViewById(R.id.tomar),
                            "trans_animation"));
                    android.support.v13.app.ActivityCompat.startActivity(CursosDetail.this, intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }

            }
        });
        btn_favo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    ListaFavoritos db = new ListaFavoritos(getBaseContext());
                    data = globalData.getNameCurrentGrupo();
                    db.insertar(globalData.getIdCurrentGrupo(), globalData.getNameCurrentGrupo());
                    Toast.makeText(CursosDetail.this, "Se agregó a favoritos", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(CursosDetail.this, "Hubo un error a agregar a favoritos.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CursosDetail.this, StatsActivity.class);
                intent.putExtra("Curso", name);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CursosDetail.this, Home.class);
        startActivity(intent);
    }

    //Creamos una instanancia de la aplicacion para que pueda ser invocada desde una clase.
    public static void createInstancealtern(Activity activity, Curso title, View view) {
        Intent intent = getLaunchIntentaltern(activity, title);
        //activity.startActivity(intent);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, new Pair<View, String>(view.findViewById(R.id.tvCurso),
                    "trans_animation"));
            android.support.v13.app.ActivityCompat.startActivity(activity, intent, options.toBundle());
        }
        else{
            activity.startActivity(intent);
        }
    }


    public static Intent getLaunchIntentaltern(Context context, Curso curso) {
        Intent intent = new Intent(context, CursosDetail.class);
        intent.putExtra("Nombre", curso.getNombre());
        intent.putExtra("ID", curso.getId());
        return intent;
    }
    
}
