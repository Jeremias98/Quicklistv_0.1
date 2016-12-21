package com.quicklistv_01;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.quicklistv_01.Class.AppController;
import com.quicklistv_01.Class.Global;
import com.quicklistv_01.SegundaPantallas.Notificaciones;
import com.quicklistv_01.SegundaPantallas.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btn_lg, btn_sg;
    EditText edUser, edPass;
    CheckBox chRecordar, chIniciar;
    private String ip="";

    // TAG
    public static String TAG = CursosDetail.class.getSimpleName();

    // Variables globales
    private Global globalData;

    // Progress dialog
    private ProgressDialog pDialog;

    private String relativeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Global data
        globalData = (Global) getApplicationContext();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);

        setContentView(R.layout.activity_login);

        chRecordar = (CheckBox) findViewById(R.id.cbRecordar);

        edUser= (EditText) findViewById(R.id.edUser);
        edPass= (EditText) findViewById(R.id.edPass);


        //Obtengo la direccion IP del sharedpreferences

        Log.d(TAG , "Direccion ip: " + ip);

        btn_lg = (Button) findViewById(R.id.btn_login);
        btn_sg = (Button) findViewById(R.id.btn_registrar);
        btn_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ingresarUsuario();

                /*
                Intent intent = new Intent(Login.this, Main.class);
                Guardar();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, new Pair<View, String>(findViewById(R.id.btn_login),
                            "trans_animation"));
                    android.support.v13.app.ActivityCompat.startActivity(Login.this, intent, options.toBundle());
                }
                else{
                    startActivity(intent);
                }*/

            }
        });



        btn_sg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SigIn.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, new Pair<View, String>(findViewById(R.id.btn_registrar),
                            "trans_animation"));
                    android.support.v13.app.ActivityCompat.startActivity(Login.this, intent, options.toBundle());
                }
                else{
                    startActivity(intent);
                }
            }
        });
        CargarDatos();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_config_login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ajustes) {
            Intent intent = new Intent(Login.this, Preferences.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Guardar() {

        SharedPreferences preferences = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  preferences.edit();
        String nombre = edUser.getText().toString();
        editor.putString("nombre", nombre);

        if (chRecordar.isChecked()){
            boolean valor = chRecordar.isChecked();

            String pass = edPass.getText().toString();
            editor.putString("user", nombre);
            editor.putString("pass", pass);
            editor.putBoolean("checked", valor);

        }
        else{
            editor.putString("user", "");
            editor.putString("pass", "");
            editor.putBoolean("checked", false);
        }

        editor.commit();
    }

    public void CargarDatos(){

        SharedPreferences preferences = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        chRecordar.setChecked(preferences.getBoolean("checked", false));
        edPass.setText(preferences.getString("pass", ""));
        edUser.setText(preferences.getString("user", ""));

    }

    @Override
    public void onStop() {

        super.onStop();

        if (globalData.getUserID() != null) {
            logout();
        }

    }

    @Override
    public void onBackPressed() {

        logout();
        finishAffinity();

    }

    // Método para ingresar un usuario
    private void ingresarUsuario() {

        SharedPreferences prefencias = getSharedPreferences("Red" , 0);
        ip = "http://"+prefencias.getString("ip_config","")+":"+prefencias.getString("puerto","")+"/Quicklist";
        relativeUrl = ip;
        globalData.setUrl(relativeUrl);

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/IngresarService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        try {


                            JSONArray jsonArray = new JSONArray(response);

                            Integer userId = null;
                            String userName = null;

                            ArrayList<String> arrayNameGrupos = new ArrayList<>();
                            ArrayList<Integer> arrayIdGrupos = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                if (jsonObject.getBoolean("error")) {
                                    dialogError("Error", jsonObject.getString("msj"), "Aceptar");
                                }

                                userId = jsonObject.getInt("id_cuenta");
                                userName = jsonObject.getString("usuario");

                                JSONArray userGroupNames = jsonObject.getJSONArray("name_grupos");
                                JSONArray userGroupIds = jsonObject.getJSONArray("id_grupos");

                                for (int j = 0; j < userGroupIds.length(); j++) {

                                    arrayIdGrupos.add(userGroupIds.getInt(j));
                                    arrayNameGrupos.add(userGroupNames.getString(j));

                                }

                            }

                            Intent intent = new Intent(Login.this, Home.class);

                            globalData.setUserID(userId);
                            globalData.setUserName(userName);
                            globalData.setUrl(relativeUrl);
                            globalData.setIdGrupos(arrayIdGrupos);
                            globalData.setNameGrupos(arrayNameGrupos);

                            Guardar();

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, new Pair<View, String>(findViewById(R.id.btn_login),
                                        "trans_animation"));
                                android.support.v13.app.ActivityCompat.startActivity(Login.this, intent, options.toBundle());
                            }
                            else{
                                startActivity(intent);
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

                params.put("user", edUser.getText().toString());
                params.put("password", edPass.getText().toString());

                return params;
            }
        };

        // Adding request to request queue

        AppController.getInstance().addToRequestQueue(req);

    }

    // Cerrar la sesion del user
    private void logout() {

        SharedPreferences prefencias = getSharedPreferences("Red" , 0);
        ip = "http://"+prefencias.getString("ip_config","")+":"+prefencias.getString("puerto","")+"/Quicklist";
        relativeUrl = ip;
        globalData.setUrl(relativeUrl);

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/LogoutService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", globalData.getUserID().toString());

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
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtn,null);
        builder.create();
        builder.show();
    }
}
