package com.quicklistv_01;

import android.annotation.TargetApi;
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
import android.view.MenuItem;
import android.widget.TextView;

import com.quicklistv_01.Fragments.Cursos;
import com.quicklistv_01.Fragments.Favoritos;
import com.quicklistv_01.SegundaPantallas.Acerca;
import com.quicklistv_01.SegundaPantallas.CalendarioCursos;
import com.quicklistv_01.SegundaPantallas.Preferences;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Cursos.OnFragmentInteractionListener, Favoritos.OnFragmentInteractionListener {

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
        }

        setupNavigationDrawerContent(navigationView);
        //Seteamos el primer framgment como predeterminado al momento de abrir la activity
        setFragment(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                                Intent intenta = new Intent(Home.this, Login.class);
                                startActivity(intenta);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_salir:
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

}

