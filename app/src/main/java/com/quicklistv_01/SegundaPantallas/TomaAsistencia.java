package com.quicklistv_01.SegundaPantallas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.AlumnosViewPagerAdapter;
import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.CursosDetail;
import com.quicklistv_01.Fragments.TomaAlumno;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TomaAsistencia extends AppCompatActivity implements  TomaAlumno.OnFragmentInteractionListener{

    public AlumnosViewPagerAdapter adapter;
    public static ViewPager mViewPager;

    // HTTP stuff
    public static String TAG = TomaAsistencia.class.getSimpleName();

    // Variables globales
    private Global globalData;

    // Progress dialog
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

    private ArrayList<Integer> idParam = new ArrayList<>();
    private ArrayList<Integer> assistParam = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toma_asistencia);

        globalData = (Global) getApplicationContext();

        Intent intent = getIntent();

        //ArrayList<Integer> ids = intent.getExtras().getIntegerArrayList("ids");
        ArrayList<Integer> ids = globalData.getIdAlumnosEnGrupo();
        //ArrayList<String> nombres = intent.getExtras().getStringArrayList("names");
        ArrayList<String> nombres = globalData.getNameAlumnosEnGrupo();

        adapter = new AlumnosViewPagerAdapter(getSupportFragmentManager(), getApplicationContext(), ids, nombres);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

    }

    public static void createInstancealtern(Activity activity) {
        Intent intent = getLaunchIntentaltern(activity);
        activity.startActivity(intent);
    }
    public static Intent getLaunchIntentaltern(Context context) {
        Intent intent = new Intent(context, TomaAsistencia.class);
        return intent;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void jumpToPage(View view) {

        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    public void mostrarTodas(View view) {

        List<Alumno> alumnos;
        alumnos = adapter.getAlumnosAsistencia();

        for (int i = 0; i < alumnos.size(); i++) {
            idParam.add(alumnos.get(i).getId());
            assistParam.add(alumnos.get(i).getAsistencia());
            guardarAsistencia(i);
        }

        revisarAusencias();


        //dialogError("Información", "Se guardó la asistencia", "Aceptar");

    }

    private void guardarAsistencia(final int i) {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/GuardarAsistenciaService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                if(jsonObject.getBoolean("success")) {

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
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("dd-MM-yyyy");

                //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String currentDateTimeString = sdf.format(dt);

                Log.d("Dato", idParam.toString());

                params.put("id_alumnos", idParam.get(i).toString());
                params.put("assist_alumnos", assistParam.get(i).toString());
                params.put("fecha", currentDateTimeString);
                params.put("grupo", globalData.getIdCurrentGrupo().toString());
                params.put("cuenta", globalData.getUserID().toString());

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

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

                            if (globalData.getIdAlumnosAusentesRecurrentes() == null) {
                                arrayNames = new ArrayList<>();
                                arrayIds = new ArrayList<>();
                                arrayDni = new ArrayList<>();
                                arrayTel = new ArrayList<>();
                                arrayCel = new ArrayList<>();
                                arrayEmail = new ArrayList<>();
                                arrayDireccion = new ArrayList<>();
                                arrayNacionalidad = new ArrayList<>();
                                arrayCurso = new ArrayList<>();
                            }
                            else {
                                arrayNames = globalData.getNombreAlumnosAusentesRecurrentes();
                                arrayIds = globalData.getIdAlumnosAusentesRecurrentes();
                                arrayDni = globalData.getDniAlumnosAusentesRecurrentes();
                                arrayTel = globalData.getTelefonoAlumnosAusentesRecurrentes();
                                arrayCel = globalData.getCelularAlumnosAusentesRecurrentes();
                                arrayEmail = globalData.getEmailAlumnosAusentesRecurrentes();
                                arrayDireccion = globalData.getDireccionAlumnosAusentesRecurrentes();
                                arrayNacionalidad = globalData.getNacionalidadAlumnosAusentesRecurrentes();
                                arrayCurso = globalData.getCursoAlumnosAusentesRecurrentes();
                            }


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


                            //Log.d("Pepe", arrayNames.toString());
                            Toast.makeText(getApplicationContext(), "Se guardó la asistencia", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), globalData.getNombreAlumnosAusentesRecurrentes().toString(), Toast.LENGTH_SHORT).show();

                            //Log.d("Pepe", arrayNames.toString());

                            Intent intent = new Intent(TomaAsistencia.this, CursosDetail.class);
                            intent.putExtra("Nombre", globalData.getNameCurrentGrupo().toString());
                            intent.putExtra("ID", globalData.getIdCurrentGrupo());
                            startActivity(intent);

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

                params.put("id_grupo", globalData.getIdCurrentGrupo().toString());

                return params;
            }
        };

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
