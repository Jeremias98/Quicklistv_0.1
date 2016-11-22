package com.quicklistv_01.SegundaPantallas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.quicklistv_01.CursosDetail;
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
    public static String TAG = Notificaciones.class.getSimpleName();
    private Global globalData;
    private ProgressDialog pDialog;

    private ArrayList<Integer> arrayIds;
    private ArrayList<String> arrayNames;
    private ArrayList<String> arrayDni;
    private ArrayList<String> arrayTel;
    private ArrayList<String> arrayCel;
    private ArrayList<String> arrayEmail;
    private ArrayList<String> arrayDireccion;
    private ArrayList<String> arrayCurso;
    private ArrayList<String> arrayNacionalidad;

    private ArrayList<Integer> arrayCursosId;
    private ArrayList<String> arrayCursosName;

    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        globalData = (Global) getApplicationContext();
        listaNotificaciones = (RecyclerView) findViewById(R.id.rvNotificaciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rl = (RelativeLayout) findViewById(R.id.RlEstado);
        LinearLayoutManager llm = new LinearLayoutManager(Notificaciones.this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        iniciarDatos();

        iniciarAdaptador();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaNotificaciones.setLayoutManager(llm);

        listaNotificaciones.addItemDecoration(new DividerItemDecoration(getBaseContext()));
        listaNotificaciones.addItemDecoration( new DividerItemDecoration(Notificaciones.this, R.drawable.barra));

    }
    public void iniciarDatos() {

        alumnos = new ArrayList<>();
            if (globalData.getIdAlumnosAusentesRecurrentes() != null) {
                rl.setVisibility(View.INVISIBLE);

                for (int i = 0; i < globalData.getIdAlumnosAusentesRecurrentes().size(); i++) {
                    alumnos.add(new NotificacionesClass(globalData.getIdAlumnosAusentesRecurrentes().get(i),
                            globalData.getNombreAlumnosAusentesRecurrentes().get(i),
                            globalData.getCursoAlumnosAusentesRecurrentes().get(i),
                            globalData.getDniAlumnosAusentesRecurrentes().get(i), "3",
                            globalData.getEmailAlumnosAusentesRecurrentes().get(i),
                            globalData.getTelefonoAlumnosAusentesRecurrentes().get(i),
                            globalData.getDireccionAlumnosAusentesRecurrentes().get(i),
                            globalData.getNacionalidadAlumnosAusentesRecurrentes().get(i)));

                }
            }

        else if(globalData.getIdAlumnosAusentesRecurrentes() == null) {
                rl.setVisibility(View.VISIBLE);
            }



    }
    public NotifacionesAdaptador adaptador;

    private void iniciarAdaptador(){
        adaptador = new NotifacionesAdaptador(Notificaciones.this, alumnos);
        listaNotificaciones.setAdapter(adaptador);
    }

    private void revisarAusencias() {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/RevisarAsistenciaService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            arrayNames = new ArrayList<String>();
                            arrayIds = new ArrayList<Integer>();
                            arrayDni = new ArrayList<String>();
                            arrayTel = new ArrayList<String>();
                            arrayCel = new ArrayList<String>();
                            arrayEmail = new ArrayList<String>();
                            arrayDireccion = new ArrayList<String>();
                            arrayNacionalidad = new ArrayList<String>();
                            arrayCurso = new ArrayList<String>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray alumnos_id = jsonObject.getJSONArray("id");
                                JSONArray alumnos_name = jsonObject.getJSONArray("name");
                                JSONArray alumnos_dni = jsonObject.getJSONArray("dni");
                                JSONArray alumnos_tel = jsonObject.getJSONArray("phone");
                                JSONArray alumnos_cel = jsonObject.getJSONArray("mobile");
                                JSONArray alumnos_email = jsonObject.getJSONArray("email");
                                JSONArray alumnos_direccion = jsonObject.getJSONArray("direccion");
                                JSONArray alumnos_nacionalidad = jsonObject.getJSONArray("nacionalidad");
                                JSONArray alumnos_curso = jsonObject.getJSONArray("curso");

                                for (int j = 0; j < alumnos_id.length(); j++) {
                                    arrayIds.add(alumnos_id.getInt(j));
                                    arrayNames.add(alumnos_name.getString(j));
                                    arrayDni.add(alumnos_dni.getString(j));
                                    arrayTel.add(alumnos_tel.getString(j));
                                    arrayCel.add(alumnos_cel.getString(j));
                                    arrayEmail.add(alumnos_email.getString(j));
                                    arrayDireccion.add(alumnos_direccion.getString(j));
                                    arrayNacionalidad.add(alumnos_nacionalidad.getString(j));
                                    arrayCurso.add(alumnos_curso.getString(j));
                                }

                            }

                            globalData.setIdAlumnosAusentesRecurrentes(arrayIds);
                            globalData.setNombreAlumnosAusentesRecurrentes(arrayNames);
                            globalData.setDniAlumnosAusentesRecurrentes(arrayDni);
                            globalData.setTelefonoAlumnosAusentesRecurrentes(arrayTel);
                            globalData.setCelularAlumnosAusentesRecurrentes(arrayCel);
                            globalData.setEmailAlumnosAusentesRecurrentes(arrayEmail);
                            globalData.setDireccionAlumnosAusentesRecurrentes(arrayDireccion);
                            globalData.setNacionalidadAlumnosAusentesRecurrentes(arrayNacionalidad);
                            globalData.setCursoAlumnosAusentesRecurrentes(arrayCurso);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id_grupo", "2");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    private void listarCursos() {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/ListarCursosService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            arrayCursosName = new ArrayList<String>();
                            arrayCursosId = new ArrayList<Integer>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray name = jsonObject.getJSONArray("nombre_curso");
                                JSONArray id = jsonObject.getJSONArray("id_curso");

                                for (int j = 0; j < id.length(); j++) {

                                    arrayCursosName.add(name.getString(j));
                                    arrayCursosId.add(id.getInt(j));

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    // Dialog de carga y mensajes
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void dialogError(String title, String message, String posBtn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtn,null);
        builder.create();
        builder.show();
    }

}
