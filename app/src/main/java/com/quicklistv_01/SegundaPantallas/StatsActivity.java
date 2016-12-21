package com.quicklistv_01.SegundaPantallas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.MesStatsAdapter;
import com.quicklistv_01.Adapters.SemanalAdapter;
import com.quicklistv_01.Adapters.StatsAdapter;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.Class.Meses;
import com.quicklistv_01.Fragments.MensualFragment;
import com.quicklistv_01.Fragments.SemanalFragment;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatsActivity extends AppCompatActivity {
    public static String TAG = SemanalFragment.class.getSimpleName();

    // Variables globales
    private Global globalData;

    // Progress dialog
    private ProgressDialog pDialog;

    ArrayList<Integer> arrayIds;
    ArrayList<String> arrayNames;
    ArrayList<Integer> arrayPS;
    ArrayList<Integer> arrayAS;
    ArrayList<Integer> arrayTS;

    ArrayList<String> nombres = new ArrayList<>();

    private RelativeLayout rlSemanal;
    private RecyclerView rv;
    private TextView lyNombres;
    private GridView gridView;
    private List<Meses> meses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalData = (Global) getApplicationContext();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        setContentView(R.layout.activity_stats);
        rv = (RecyclerView) findViewById(R.id.rvMeses);
        lyNombres = (TextView)findViewById(R.id.ly_nombres);

         gridView = (GridView) findViewById(R.id.gridView);

        SetearToolbar();//Establece el nombre del curso en el que esta
        iniciarColumnas();//Agrega las columnas de Nombre, A ; P  y T
        estadisticasSemanales(gridView);//Llena el gridview



    }

    public void SetearToolbar(){
        //Obtenemos data del intent
        Intent i = getIntent();
        final String name = i.getStringExtra("Curso");
        //Seteamos nombre del curso en la toolbar
        getSupportActionBar().setTitle(name);

    }
    public void iniciarColumnas(){
        this.nombres.add("NOMBRE Y APELLIDO");
        this.nombres.add("P");
        this.nombres.add("A");
        this.nombres.add("T");
    }


    public void IniciarGrid(GridView gridView) {

        SemanalAdapter adapter = new SemanalAdapter(getApplicationContext(), nombres);//instancia el adaptador
        gridView.setAdapter(adapter);//Seteal el adaptador

    }
    public void AgregarValores (String nombres, Integer presentes, Integer ausentes, Integer tardes) {

        this.nombres.add(nombres);
        this.nombres.add(presentes.toString());
        this.nombres.add(ausentes.toString());
        this.nombres.add(tardes.toString());

    }

    private void estadisticasSemanales(final GridView gv) {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/EstadisticasAlumnoService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            arrayNames = new ArrayList<String>();
                            arrayIds = new ArrayList<Integer>();
                            arrayPS = new ArrayList<Integer>();
                            arrayAS = new ArrayList<Integer>();
                            arrayTS = new ArrayList<Integer>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray alumnos_id = jsonObject.getJSONArray("id_alumno");
                                JSONArray alumnos_name = jsonObject.getJSONArray("nombre_alumno");

                                JSONArray presentes_semanal = jsonObject.getJSONArray("presentes_semanal");
                                JSONArray ausentes_semanal = jsonObject.getJSONArray("ausentes_semanal");
                                JSONArray tardes_semanal = jsonObject.getJSONArray("tardes_semanal");

                                for (int j = 0; j < alumnos_id.length(); j++) {

                                    arrayIds.add(alumnos_id.getInt(j));
                                    arrayNames.add(alumnos_name.getString(j));
                                    arrayPS.add(presentes_semanal.getInt(j));
                                    arrayAS.add(ausentes_semanal.getInt(j));
                                    arrayTS.add(tardes_semanal.getInt(j));

                                }

                            }

                            for (int i = 0; i < arrayIds.size(); i++) {

                                AgregarValores(arrayNames.get(i), arrayPS.get(i), arrayAS.get(i), arrayTS.get(i));

                            }

                            Log.d(TAG, nombres.toString());

                            IniciarGrid(gv);


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

                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("dd-MM-yyyy");
                String currentDateTimeString = sdf.format(dt);

                params.put("id_grupo", globalData.getIdCurrentGrupo().toString());
                params.put("fecha", currentDateTimeString);

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

    public void btnSemanal (View view){
        rv.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        lyNombres.setText("ESTADISTICAS SEMANALES");
    }
    public void btnMensuales(View view){
        gridView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        lyNombres.setText("ESTADISTICAS MENSUALES");
        cargarMeses();
        configurarAdaptador();
        iniciarAdaptador();
        SetearAdaptador();



    }


    private void cargarMeses() {
        meses.add(new Meses("Marzo"));
        meses.add(new Meses("Abril"));
        meses.add(new Meses("Mayo"));
        meses.add(new Meses("Junio"));
        meses.add(new Meses("Julio"));
        meses.add(new Meses("Agosto"));
        meses.add(new Meses("Septiembre"));
        meses.add(new Meses("Octurbre"));
        meses.add(new Meses("Noviembre"));
        meses.add(new Meses("Diciembre"));

    }

    public void configurarAdaptador() {
        LinearLayoutManager lim = new LinearLayoutManager(getApplicationContext());
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new com.quicklistv_01.Class.DividerItemDecoration(getApplicationContext()));
        rv.addItemDecoration(new com.quicklistv_01.Class.DividerItemDecoration(getApplicationContext(), R.drawable.barra));
    }

    public MesStatsAdapter adapter;

    public void iniciarAdaptador() {
        adapter = new MesStatsAdapter(getApplicationContext(), meses);

    }

    public void SetearAdaptador() {
        rv.setAdapter(adapter);
    }
}
