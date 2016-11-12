package com.quicklistv_01.SegundaPantallas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.quicklistv_01.Adapters.SubGruposAdapter;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.SubGrupos;
import com.quicklistv_01.R;

import java.util.ArrayList;
import java.util.List;

public class VerSubGrupos extends AppCompatActivity {
    private List<SubGrupos> grupos;
    RecyclerView rvgrupos;
    FloatingActionButton btn;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_sub_grupos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn = (FloatingActionButton) findViewById(R.id.agregar);
        rvgrupos =(RecyclerView)findViewById(R.id.rvSubGrupos);

        LinearLayoutManager llm = new LinearLayoutManager(VerSubGrupos.this);
        iniciarDatos("Taller");
        iniciarAdaptador();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvgrupos.setLayoutManager(llm);
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.dialog_crear, null);
        rvgrupos.addItemDecoration(new DividerItemDecoration(getBaseContext()));
        rvgrupos.addItemDecoration( new DividerItemDecoration(VerSubGrupos.this, R.drawable.barra));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                final EditText et1 = (EditText) inflator.findViewById(R.id.edNuevoGrupo);
                AlertDialog.Builder builder = new AlertDialog.Builder(VerSubGrupos.this)
                        .setView(inflator)
                        .setTitle("Nuevo grupo")
                        .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(VerSubGrupos.this, "Grupo creado correctamente", Toast.LENGTH_SHORT).show();
                                //data = et1.getText().toString();
                                //iniciarDatos(data);

                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


    }
    public void iniciarDatos(String data){
        grupos = new ArrayList<>();
        grupos.add(new SubGrupos(data));
    }
    public SubGruposAdapter adaptador;

    private void iniciarAdaptador(){
        adaptador = new SubGruposAdapter(grupos, getBaseContext());
        rvgrupos.setAdapter(adaptador);
    }
    public static void createInstancealtern(Activity activity) {
        Intent intent = getLaunchIntentaltern(activity);
        activity.startActivity(intent);
    }
    public static Intent getLaunchIntentaltern(Context context) {
        Intent intent = new Intent(context, VerSubGrupos.class);
        return intent;
    }

}
