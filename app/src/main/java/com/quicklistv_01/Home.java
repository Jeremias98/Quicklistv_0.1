package com.quicklistv_01;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.quicklistv_01.Fragments.Cursos;
import com.quicklistv_01.Fragments.Favoritos;
import com.quicklistv_01.SegundaPantallas.Acerca;
import com.quicklistv_01.SegundaPantallas.Alumnos;
import com.quicklistv_01.SegundaPantallas.CalendarioCursos;
import com.quicklistv_01.SegundaPantallas.Notificaciones;
import com.quicklistv_01.SegundaPantallas.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Cursos.OnFragmentInteractionListener, Favoritos.OnFragmentInteractionListener {

    // TAG
    public static String TAG = Home.class.getSimpleName();

    // Variables globales
    private Global globalData;

    // Progress dialog
    private ProgressDialog pDialog;
    TextView usuario;

    //Boton de confirmacion de Salida
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("Desea salir de la aplicación?");
        builder.setPositiveButton("Confimar", new DialogInterface.OnClickListener(){

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Logout
                logout();

                finishAffinity();

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Global data
        globalData = (Global) getApplicationContext();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere...");
        pDialog.setCancelable(false);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
            View header=navigationView.getHeaderView(0);
            Intent i = getIntent();
            final String cuenta = i.getStringExtra("cuenta");
            TextView usuario = (TextView)header.findViewById(R.id.userName);
            usuario.setText(cuenta);
        }

        final Thread.UncaughtExceptionHandler oldHandler =
                Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(
                            Thread paramThread,
                            Throwable paramThrowable
                    ) {
                        //Do your own error handling here


                        if (oldHandler != null)
                            oldHandler.uncaughtException(
                                    paramThread,
                                    paramThrowable
                            ); //Delegates to Android's error handling
                        else{
                            System.exit(2); //Prevents the service/app from freezing
                            logout();
                        }

                    }
                });

        setupNavigationDrawerContent(navigationView);
        //Seteamos el primer framgment como predeterminado al momento de abrir la activity
        setFragment(1);
        //Usuario


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_alumnos_drawer, menu);
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        logout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                Intent intent = new Intent(Home.this, Notificaciones.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        switch (menuItem.getItemId()) {
                            case R.id.item_fav:
                                setFragment(1);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_cursos:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_calendar:

                                menuItem.setChecked(true);
                                drawer.closeDrawer(GravityCompat.START);
                                Intent calendar = new Intent(Home.this, CalendarioCursos.class);
                                startActivity(calendar);
                                return true;
                            case R.id.item_configuraciones:
                                drawer.closeDrawer(GravityCompat.START);
                                Intent intento = new Intent(Home.this, Preferences.class);
                                startActivity(intento);
                                return true;
                            case R.id.item_acerca:
                                Intent intent = new Intent(Home.this, Acerca.class);
                                startActivity(intent);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_cerrarsesion:
                                logout();
                                Intent intenta = new Intent(Home.this, Login.class);
                                startActivity(intenta);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_salir:
                                logout();
                                finishAffinity();
                                return true;
                        }
                        return true;
                    }
                });
    }

    //Metodo para setear los framgentos del Navigation Drawer
    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Cursos inboxFragment = new Cursos();
                fragmentTransaction.replace(R.id.contenedor, inboxFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Favoritos starredFragment = new Favoritos();
                fragmentTransaction.replace(R.id.contenedor, starredFragment);
                fragmentTransaction.commit();
                break;

        }
    }

    // Cerrar la sesion del user
    private void logout() {

        showpDialog();

        StringRequest req = new StringRequest(Request.Method.POST, globalData.getUrl() + "/LogoutService",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

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
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posBtn,null);
        builder.create();
        builder.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

}

