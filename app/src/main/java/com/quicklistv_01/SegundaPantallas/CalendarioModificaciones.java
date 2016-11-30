package com.quicklistv_01.SegundaPantallas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.quicklistv_01.Modificar;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarioModificaciones extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private Button btn_buscar;

    // HTTP stuff
    private Global globalData;

    private ProgressDialog pDialog;

    public static String TAG = CalendarioModificaciones.class.getSimpleName();

    ArrayList<String> arrayNames;
    ArrayList<Integer> arrayIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        globalData = (Global) getApplicationContext();

        // Progress dialog
        pDialog = new ProgressDialog(CalendarioModificaciones.this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent e = getIntent();
        final String curso = e.getStringExtra("Curso");
        ActionBar i = getSupportActionBar();
        i.setTitle(curso);
        btn_buscar = (Button) findViewById(R.id.btn_buscar);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String f = day+"-"+(month+1)+"-"+year;
                globalData.setFechaCurrent(f);

                listarCursos();

            }
        });
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

                            boolean flag = false;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray name = jsonObject.getJSONArray("nombre_grupo");
                                JSONArray id = jsonObject.getJSONArray("id_grupo");

                                for (int j = 0; j < id.length(); j++) {

                                    arrayNames.add(name.getString(j));
                                    arrayIDs.add(id.getInt(j));

                                    if (globalData.getIdCurrentGrupo() == arrayIDs.get(j)) {
                                        flag = true;
                                    }

                                }

                            }

                            if (flag) {
                                Intent intent = new Intent(CalendarioModificaciones.this, Modificar.class);

                                intent.putExtra("fecha", globalData.getFechaCurrent());

                                startActivity(intent);
                            }
                            else {
                                dialogError("Aviso", "No se tomó lista en esa fecha en "+globalData.getNameCurrentGrupo(), "Aceptar");
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
                Toast.makeText(CalendarioModificaciones.this.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                String fecha = globalData.getFechaCurrent();

                params.put("fecha", fecha);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Seleccione la fecha que desea buscar", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            year = arg1;
            month = arg2;
            day = arg3;
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText("La fecha seleccionada es: " + new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarioModificaciones.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtn,null);
        builder.create();
        builder.show();
    }
}
