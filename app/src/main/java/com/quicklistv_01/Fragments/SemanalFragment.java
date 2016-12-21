package com.quicklistv_01.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.SemanalAdapter;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.R;
import com.quicklistv_01.SegundaPantallas.Alumnos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SemanalFragment extends Fragment {

    // HTTP stuff
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

    public SemanalFragment() {



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        globalData = (Global) getActivity().getApplicationContext();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootview = inflater.inflate(R.layout.fragment_semanal, container, false);

        //Casteando objetos
        GridView gridView = (GridView) rootview.findViewById(R.id.gridView);

        estadisticasSemanales(gridView);



        return rootview;
    }

    public void IniciarGrid(GridView gridView) {

        SemanalAdapter adapter = new SemanalAdapter(getActivity(), nombres);
        gridView.setAdapter(adapter);

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
                Toast.makeText(getActivity().getApplicationContext(),
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtn,null);
        builder.create();
        builder.show();
    }

}
