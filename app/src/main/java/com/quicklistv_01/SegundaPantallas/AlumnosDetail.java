package com.quicklistv_01.SegundaPantallas;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.quicklistv_01.Class.NotificacionesClass;
import com.quicklistv_01.R;

public class AlumnosDetail extends AppCompatActivity {

    private TextView tvNombre, tvCurso, tvInan, tvTel, tvMail, tvDireccion,tvNacionalidad, tvDni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumos_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        final String nombre = i.getStringExtra("Nombre");
        final String ID = i.getStringExtra("ID");
        final String curso = i.getStringExtra("curso");
        final String inasistencias = i.getStringExtra("inasistencias");
        final String tel = i.getStringExtra("tel");
        final String mail = i.getStringExtra("mail");
        final String direc = i.getStringExtra("direc");
        final String nacionalidad = i.getStringExtra("nacionalidad");
        final String dni = i.getStringExtra("DNI");
        tvDni = (TextView) findViewById(R.id.tvDNI);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvCurso = (TextView) findViewById(R.id.tvcursos);
        tvInan = (TextView) findViewById(R.id.tvInan);
        tvTel = (TextView) findViewById(R.id.tvTel);
        tvMail = (TextView) findViewById(R.id.tvMail);
        tvDireccion = (TextView) findViewById(R.id.tvDirec);
        tvNacionalidad = (TextView) findViewById(R.id.tvNacionalidad);
        tvNombre.setText(nombre);
        tvCurso.setText("Curso: " + curso);
        tvInan.setText("Inasistencia: " + inasistencias);
        tvTel.setText("Telefono: " + tel);
        tvMail.setText("Email: " + mail);
        tvDireccion.setText("Dirección: " + direc);
        tvDni.setText("DNI: " + dni);
        tvNacionalidad.setText("Nacionalidad; " + nacionalidad);









    }




    public static void createInstancealtern(Activity activity, NotificacionesClass title, View view) {
        Intent intent = getLaunchIntentaltern(activity,title);
        activity.startActivity(intent);
    }


    public static Intent getLaunchIntentaltern(Context context, NotificacionesClass curso) {
        Intent intent = new Intent(context, AlumnosDetail.class);
        intent.putExtra("Nombre", curso.getNombre());
        intent.putExtra("ID", curso.getID());
        intent.putExtra("DNI", curso.getDNI());
        intent.putExtra("curso", curso.getCurso_alumno());
        intent.putExtra("inasistencias", curso.getAssist());
        intent.putExtra("tel", curso.getTel());
        intent.putExtra("mail", curso.getEmail());
        intent.putExtra("direc", curso.getDireccion());
        intent.putExtra("nacionalidad", curso.getNacionalidad());

        return intent;
    }
}
