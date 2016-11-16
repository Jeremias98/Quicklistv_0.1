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
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.AlumnosAdaptador;
import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.Fragments.TomaAlumno;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alumnos extends AppCompatActivity {

    private List<Alumno> alumnos;
    RecyclerView listaAlumno;
    ImageView btn;

    // HTTP stuff
    public static String TAG = Alumnos.class.getSimpleName();

    // Variables globales
    private Global globalData;

    // Progress dialog
    private ProgressDialog pDialog;

    ArrayList<Integer> arrayIds;
    ArrayList<String> arrayNames;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        globalData = (Global) getApplicationContext();

        intent = getIntent();

        btn = (ImageView) findViewById(R.id.btn_tomarAsistencia);
        listaAlumno = (RecyclerView) findViewById(R.id.rvAlumnos);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        LinearLayoutManager llm = new LinearLayoutManager(Alumnos.this);

        alumnosEnGrupo();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaAlumno.setLayoutManager(llm);

        listaAlumno.addItemDecoration(new DividerItemDecoration(getBaseContext()));
        listaAlumno.addItemDecoration( new DividerItemDecoration(Alumnos.this, R.drawable.barra));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Alumnos.this , TomaAsistencia.class);
                //intent.putIntegerArrayListExtra("ids", arrayIds);
                //intent.putStringArrayListExtra("names", arrayNames);
                startActivity(intent);
            }
        });

    }
    public void inciarDatos(){

        alumnos = new ArrayList<>();

        for (int i = 0; i < arrayIds.size(); i++) {
            alumnos.add(new Alumno(arrayIds.get(i), arrayNames.get(i)));
        }

    }

    public AlumnosAdaptador adaptador;

    private void iniciarAdaptador(){
        adaptador = new AlumnosAdaptador(alumnos, getBaseContext());
        listaAlumno.setAdapter(adaptador);
    }

    private void alumnosEnGrupo() {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/AlumnoGrupoService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            arrayNames = new ArrayList<String>();
                            arrayIds = new ArrayList<Integer>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray alumnos_id = jsonObject.getJSONArray("ids");
                                JSONArray alumnos_name = jsonObject.getJSONArray("nombres");

                                for (int j = 0; j < alumnos_id.length(); j++) {
                                    arrayIds.add(alumnos_id.getInt(j));
                                    arrayNames.add(alumnos_name.getString(j));
                                }

                            }

                            globalData.setIdAlumnosEnGrupo(arrayIds);
                            globalData.setNameAlumnosEnGrupo(arrayNames);

                            inciarDatos();
                            iniciarAdaptador();

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

                //Integer id = intent.getExtras().getInt("ID");

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

