package com.quicklistv_01.SegundaPantallas;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.AlumnosAdaptador;
import com.quicklistv_01.Adapters.NotifacionesAdaptador;
import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.Class.NotificacionesClass;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notificaciones extends AppCompatActivity {
    private List<NotificacionesClass> alumnos;
    RecyclerView listaNotificaciones;
    public static String TAG = Alumnos.class.getSimpleName();
    private Global globalData;
    private ProgressDialog pDialog;
    ArrayList<Integer> arrayIds;
    ArrayList<String> arrayNames;
    ArrayList<String> arrayCurso;
    ArrayList<String> arrayTelefono;
    ArrayList<String> arrayDNI;
    ArrayList<String> arrayInasistencia;
    ArrayList<String> arrayEmail;
    ArrayList<String> arrayDireccion;
    ArrayList<String> arrayNacionalidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        globalData = (Global) getApplicationContext();
        listaNotificaciones = (RecyclerView) findViewById(R.id.rvNotificaciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager llm = new LinearLayoutManager(Notificaciones.this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);
        alumnosEnGrupo();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaNotificaciones.setLayoutManager(llm);

        listaNotificaciones.addItemDecoration(new DividerItemDecoration(getBaseContext()));
        listaNotificaciones.addItemDecoration(new DividerItemDecoration(Notificaciones.this, R.drawable.barra));
    }

    public void iniciarDatos() {

        alumnos = new ArrayList<>();
        arrayIds = new ArrayList<>();
        if (arrayIds.size() == 0) {

        } else {
            for (int i = 0; i < arrayIds.size(); i++) {
                alumnos.add(new NotificacionesClass(arrayIds.get(i), arrayNames.get(i), "7°3°", " 123456789", "5", arrayEmail.get(i), arrayTelefono.get(i), "Calle falsa 123", "Argentina"));
            }
        }

    }

    public NotifacionesAdaptador adaptador;

    private void iniciarAdaptador() {
        adaptador = new NotifacionesAdaptador(Notificaciones.this, alumnos);
        listaNotificaciones.setAdapter(adaptador);
    }

    private void alumnosEnGrupo() {
        arrayIds = globalData.getIdAlumnosAusentesRecurrentes();
        arrayNames = globalData.getNombreAlumnosAusentesRecurrentes();
        arrayTelefono = globalData.getTelefonoAlumnosAusentesRecurrentes();
        arrayEmail = globalData.getEmailAlumnosAusentesRecurrentes();
        iniciarDatos();
        iniciarAdaptador();
    }
}
