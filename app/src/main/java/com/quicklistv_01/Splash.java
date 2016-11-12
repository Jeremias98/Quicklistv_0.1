package com.quicklistv_01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Splash extends AppCompatActivity {
    public static final int segundos=3;
    public static final int mili= segundos*1000;
    public static final int delay = 2;
    Boolean str=false;
    private ProgressBar progressBar;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView titulo = (TextView) findViewById(R.id.Titulo);
        //Typeface face = Typeface.createFromAsset(getAssets(),"Raleway.woff");
        //titulo.setTypeface(Splash.myFont(this));
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax(maxim());
        empezar();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway.woff");
    }
    public void empezar(){
        new CountDownTimer(mili, segundos) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(establecer(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                SharedPreferences preferences = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
                str = preferences.getBoolean("recordar", false);
                if (str == true ){
                    Intent intent = new Intent(Splash.this, Home.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(Splash.this,Login.class);
                    startActivity(intent);
                    finish();
                }


            }
        }.start();
    }
    public int establecer(long milisegundos){
        return (int) ((mili - milisegundos)/1000);

    }
    public int maxim(){
        return mili- delay;
    }
}
