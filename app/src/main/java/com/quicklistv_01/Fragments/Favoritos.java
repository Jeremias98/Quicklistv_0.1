package com.quicklistv_01.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Adapters.FavoritosAdapter;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Curso;
import com.quicklistv_01.Class.DBHelper;
import com.quicklistv_01.Class.DividerItemDecoration;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends Fragment {

    public static Context context;

    public Favoritos() {
    }

    public FavoritosAdapter adaptador;
    TextView tvCurso;
    RecyclerView listaCursos;
    private List<Curso> curso;
    ArrayList<String> c = new ArrayList<>();
    ArrayList<Integer> d = new ArrayList<>();
    String a[] = {};
    // HTTP stuff
    private Global globalData;

    private ProgressDialog pDialog;

    public static String TAG = Cursos.class.getSimpleName();

    ArrayList<String> arrayNames;
    ArrayList<Integer> arrayIDs;
    ArrayList<Boolean> arrayFav;
    TextView tvEstado;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalData = (Global) getActivity().getApplicationContext();


        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_favoritos, container, false);
        listaCursos = (RecyclerView) rootView.findViewById(R.id.rvFavoritos);
        tvCurso = (TextView) rootView.findViewById(R.id.tvCurso);
        tvEstado = (TextView) rootView.findViewById(R.id.tvEstado);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());


        listarCursos();

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaCursos.setLayoutManager(llm);

        listaCursos.setItemAnimator(new DefaultItemAnimator());
        listaCursos.addItemDecoration(new DividerItemDecoration(getContext()));
        listaCursos.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.barra));
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iniciarDatos();
                iniciarAdaptador();
                refreshItems();
            }
        });

        return rootView;
    }

    public void iniciarDatos() {

        DBHelper helper = new DBHelper(getContext());
        helper.open();
        c = helper.llenar();
        d = helper.llenarIds();
        if (c.size() != 0) {
            listaCursos.setVisibility(View.VISIBLE);
            tvEstado.setVisibility(View.INVISIBLE);
            curso = new ArrayList<>();

            for (String nombre : a) {
                c.add(nombre);
            }

            for (int i = 0; i < c.size(); i++) {
                curso.add(new Curso(d.get(i), c.get(i), arrayFav.get(i)));
            }
        } else if (c.size() == 0) {
            tvEstado.setVisibility(View.VISIBLE);
            listaCursos.setVisibility(View.INVISIBLE);
        }

        helper.close();

    }

    private void iniciarAdaptador() {

        adaptador = new FavoritosAdapter(curso, getContext());
        listaCursos.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

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
                                    for (int k = 0; k < globalData.getIdGrupos().size(); k++) {
                                        if (globalData.getIdGrupos().get(k) == id.getInt(j)) {
                                            flag = true;
                                            break;
                                        }
                                    }

                                    arrayNames.add(name.getString(j));
                                    arrayIDs.add(id.getInt(j));

                                    if (flag) {
                                        arrayFav.add(true);
                                    } else if (!flag) {
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
        builder.setPositiveButton(posBtn, null);
        builder.create();
        builder.show();
    }


    void refreshItems() {

        iniciarDatos();

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        adaptador.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
