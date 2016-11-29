package com.quicklistv_01.SegundaPantallas;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.AlumnosAdaptador;
import com.quicklistv_01.Adapters.ListaAsistenciaAdapter;
import com.quicklistv_01.Class.Alumno;
import com.quicklistv_01.Class.AlumnoAsistencia;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListaAsistencia extends AppCompatActivity {

    RecyclerView rvLista;
    private List<AlumnoAsistencia> alumnos;

    // HTTP stuff
    public static String TAG = ListaAsistencia.class.getSimpleName();

    // Variables globales
    private Global globalData;

    // Progress dialog
    private ProgressDialog pDialog;

    ArrayList<Integer> arrayIds;
    ArrayList<String> arrayNames;
    ArrayList<String> arrayAssist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asistencia);

        globalData = (Global) getApplicationContext();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        rvLista = (RecyclerView) findViewById(R.id.rvLista);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alumnosEnGrupo();

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

        for (int i = 0; i < arrayIds.size(); i++) {
            alumnos.add(new AlumnoAsistencia(arrayIds.get(i), arrayNames.get(i), arrayAssist.get(i)));
        }

    }

    public ListaAsistenciaAdapter adaptador;

    private void iniciarAdaptador(){
        adaptador = new ListaAsistenciaAdapter( alumnos, getApplicationContext());
        rvLista.setAdapter(adaptador);
    }

    private void alumnosEnGrupo() {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/RevisarAsistenciaService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            arrayNames = new ArrayList<String>();
                            arrayAssist = new ArrayList<String>();
                            arrayIds = new ArrayList<Integer>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray alumnos_id = jsonObject.getJSONArray("id");
                                JSONArray alumnos_name = jsonObject.getJSONArray("name");
                                JSONArray alumnos_assist = jsonObject.getJSONArray("assist");

                                for (int j = 0; j < alumnos_id.length(); j++) {

                                    arrayIds.add(alumnos_id.getInt(j));
                                    arrayNames.add(alumnos_name.getString(j));
                                    arrayAssist.add(alumnos_assist.getString(j));

                                }

                            }

                            globalData.setIdAlumnosEnGrupo(arrayIds);
                            globalData.setNameAlumnosEnGrupo(arrayNames);
                            globalData.setAsistenciaAlumnosEnGrupo(arrayAssist);

                            iniciarDatos();
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
                params.put("fecha", globalData.getFechaCurrent());

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

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


    public static Intent getLaunchIntent(Context context, Curso curso) {
        Intent intent = new Intent(context, ListaAsistencia.class);
        intent.putExtra("Nombre", curso.getNombre());
        intent.putExtra("ID", curso.getId());
        return intent;
    }
}
