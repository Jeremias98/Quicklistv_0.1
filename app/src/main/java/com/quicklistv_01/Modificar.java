package com.quicklistv_01;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.SegundaPantallas.Alumnos;
import com.quicklistv_01.SegundaPantallas.TomaAsistencia;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Modificar extends AppCompatActivity {

    Button btn_esc, btn_esr;
    TextView contentTxt, formatTxt;
    private String m_Text = "";

    // HTTP stuff
    public static String TAG = Alumnos.class.getSimpleName();

    // Variables globales
    private Global globalData;

    // Progress dialog
    private ProgressDialog pDialog;

    ArrayList<Integer> arrayIds;
    ArrayList<String> arrayNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        globalData = (Global) getApplicationContext();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        alumnosEnGrupo();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_esc = (Button) findViewById(R.id.btn_escanear);
        btn_esr = (Button) findViewById(R.id.btn_escribir);
        contentTxt= (TextView)findViewById(R.id.codob);
        formatTxt = (TextView) findViewById(R.id.cod) ;
        btn_esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(Modificar.this);
                scanIntegrator.initiateScan();
            }
        });
        btn_esr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Modificar.this);
                builder.setTitle("Escribir Codigo");
                final EditText input = new EditText(Modificar.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        globalData.setModificar(true);
                        startActivity(new Intent(Modificar.this, TomaAsistencia.class));
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            String scanContent = scanningResult.getContents();
            //contentTxt.setText("Contenido: " + scanContent);
            String scanFormat = scanningResult.getFormatName();
            //formatTxt.setText("Formato: " + scanFormat);
            startActivity(new Intent(Modificar.this, TomaAsistencia.class));
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
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

}
