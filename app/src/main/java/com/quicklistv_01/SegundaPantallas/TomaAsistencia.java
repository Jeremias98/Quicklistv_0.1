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

    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
    private String currentDateTimeString = sdf.format(dt);

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

    private String ultimaFecha;

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

        ultimaTomaAsistencia();

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

        if (globalData.isModificar()) {

            for (int i = 0; i < alumnos.size(); i++) {
                idParam.add(alumnos.get(i).getId());
                assistParam.add(alumnos.get(i).getAsistencia());
                modificarAsistenciaPorFecha(i);
            }

            globalData.setModificar(false);

        }
        else if (ultimaFecha != null) {

            if (ultimaFecha.equals(currentDateTimeString)) {
                for (int i = 0; i < alumnos.size(); i++) {
                    idParam.add(alumnos.get(i).getId());
                    assistParam.add(alumnos.get(i).getAsistencia());
                    modificarAsistencia(i);
                }
            }
            else {
                for (int i = 0; i < alumnos.size(); i++) {
                    idParam.add(alumnos.get(i).getId());
                    assistParam.add(alumnos.get(i).getAsistencia());
                    guardarAsistencia(i);
                }
            }
        }
        else {
            for (int i = 0; i < alumnos.size(); i++) {
                idParam.add(alumnos.get(i).getId());
                assistParam.add(alumnos.get(i).getAsistencia());
                guardarAsistencia(i);
            }
        }
        //revisarAusencias();

        Toast.makeText(getApplicationContext(), "Se tomó asistencia correctamente", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), globalData.getNombreAlumnosAusentesRecurrentes().toString(), Toast.LENGTH_SHORT).show();

        //Log.d("Pepe", arrayNames.toString());

        Intent intent = new Intent(TomaAsistencia.this, CursosDetail.class);
        intent.putExtra("Nombre", globalData.getNameCurrentGrupo().toString());
        intent.putExtra("ID", globalData.getIdCurrentGrupo());
        startActivity(intent);
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



                //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


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

    private void modificarAsistencia(final int i) {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/ModificarAsistenciaService",
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



                //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


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

    private void modificarAsistenciaPorFecha(final int i) {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/ModificarAsistenciaService",
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



                //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


                Log.d("Dato", idParam.toString());

                params.put("id_alumnos", idParam.get(i).toString());
                params.put("assist_alumnos", assistParam.get(i).toString());
                params.put("fecha", globalData.getFechaCurrent());
                params.put("grupo", globalData.getIdCurrentGrupo().toString());
                params.put("cuenta", globalData.getUserID().toString());

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    private void ultimaTomaAsistencia() {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/UltimaTomaAsistenciaService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                ultimaFecha = jsonObject.getString("fecha");

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

                Log.d("Dato", idParam.toString());

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
