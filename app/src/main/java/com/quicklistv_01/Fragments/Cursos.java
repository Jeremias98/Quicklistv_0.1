package com.quicklistv_01.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.CursoAdaptador;
import com.quicklistv_01.Adapters.RecyclerItemClickListener;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.Class.VerticalSpaceItemDecoration;
import com.quicklistv_01.CursosDetail;
import com.quicklistv_01.Login;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Cursos extends Fragment  {

    public Cursos() {}

    TextView tvCurso;
    RecyclerView listaCursos;
    private List<Curso> curso;

    // HTTP stuff
    private Global globalData;

    private ProgressDialog pDialog;

    public static String TAG = Cursos.class.getSimpleName();

    ArrayList<String> arrayNames;
    ArrayList<Integer> arrayIDs;
    ArrayList<Boolean> arrayFav;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalData = (Global) getActivity().getApplicationContext();

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

    }

    public void iniciarDatos(){

        curso = new ArrayList<Curso>();

        for (int i = 0; i < arrayIDs.size(); i++) {
            curso.add(new Curso(arrayIDs.get(i), arrayNames.get(i), arrayFav.get(i)));
        }

    }

    public CursoAdaptador adaptador;

    private void iniciarAdaptador(){
        adaptador = new CursoAdaptador(curso, getContext());
        listaCursos.setAdapter(adaptador);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_cursos, container, false);

        listaCursos = (RecyclerView) rootView.findViewById(R.id.rvCursos);
        tvCurso = (TextView) rootView.findViewById(R.id.tvCurso);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        listarCursos();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaCursos.setLayoutManager(llm);

        listaCursos.setItemAnimator(new DefaultItemAnimator());
        listaCursos.addItemDecoration(new DividerItemDecoration(getContext()));
        listaCursos.addItemDecoration( new DividerItemDecoration(getActivity(), R.drawable.barra));

        return rootView;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
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
                            arrayNames = new ArrayList<String>();
                            arrayIDs = new ArrayList<Integer>();
                            arrayFav = new ArrayList<Boolean>();

                            boolean flag = false;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONArray name = jsonObject.getJSONArray("nombre_curso");
                                JSONArray id = jsonObject.getJSONArray("id_curso");

                                for (int j = 0; j < id.length(); j++) {
                                    flag = false;
                                    // Verifico que no aparezcan los cursos que ya administra
                                    for (int k = 0; k < globalData.getIdGrupos().size(); k++){
                                        if (globalData.getIdGrupos().get(k) == id.getInt(j)){
                                            flag = true;
                                            break;
                                        }
                                    }

                                    arrayNames.add(name.getString(j));
                                    arrayIDs.add(id.getInt(j));

                                    if (flag) {
                                        arrayFav.add(true);
                                    }
                                    else if (!flag) {
                                        arrayFav.add(false);

                                    }

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
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtn,null);
        builder.create();
        builder.show();
    }
}
