package com.quicklistv_01.SegundaPantallas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.CursoAdaptador;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.CursosDetail;
import com.quicklistv_01.Fragments.Cursos;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CursosConsulta extends AppCompatActivity {

    private Intent intent;

    public static Context context;

    TextView tvCurso;
    RecyclerView listaCursos;
    private List<Curso> curso;

    // HTTP stuff
    private Global globalData;

    private ProgressDialog pDialog;

    public static String TAG = CursosConsulta.class.getSimpleName();

    ArrayList<String> arrayNames;
    ArrayList<Integer> arrayIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos_consulta);

        globalData = (Global) getApplicationContext();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        // Progress dialog
        pDialog = new ProgressDialog(CursosConsulta.this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        listaCursos = (RecyclerView) findViewById(R.id.rvCursos);
        tvCurso = (TextView) findViewById(R.id.tvCurso);

        LinearLayoutManager llm = new LinearLayoutManager(CursosConsulta.this);

        listarCursos();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaCursos.setLayoutManager(llm);

        listaCursos.setItemAnimator(new DefaultItemAnimator());
        listaCursos.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
        listaCursos.addItemDecoration( new DividerItemDecoration(CursosConsulta.this, R.drawable.barra));
    }


    public void iniciarDatos(){

        curso = new ArrayList<Curso>();

        for (int i = 0; i < arrayIDs.size(); i++) {
            curso.add(new Curso(arrayIDs.get(i), arrayNames.get(i)));
        }

    }
    public CursoAdaptador adaptador;

    private void iniciarAdaptador(){
        context = getApplicationContext();
        adaptador = new CursoAdaptador(curso, context);
        listaCursos.setAdapter(adaptador);
    }




    private void listarCursos() {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/BuscarCursosService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            arrayNames = new ArrayList<String>();
                            arrayIDs = new ArrayList<Integer>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray name = jsonObject.getJSONArray("nombre_grupo");
                                JSONArray id = jsonObject.getJSONArray("id_grupo");

                                for (int j = 0; j < id.length(); j++) {

                                    arrayNames.add(name.getString(j));
                                    arrayIDs.add(id.getInt(j));

                                }

                            }

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
                Toast.makeText(CursosConsulta.this.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                String fecha = intent.getExtras().getString("fecha");

                params.put("fecha", fecha);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void dialogError(String title, String message, String posBtn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CursosConsulta.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtn,null);
        builder.create();
        builder.show();
    }
}
