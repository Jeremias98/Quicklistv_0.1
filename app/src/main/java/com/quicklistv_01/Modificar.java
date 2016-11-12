package com.quicklistv_01;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quicklistv_01.SegundaPantallas.Alumnos;
import com.quicklistv_01.SegundaPantallas.TomaAsistencia;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Modificar extends AppCompatActivity {
    Button btn_esc, btn_esr;
    TextView contentTxt, formatTxt;
    private String m_Text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
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
}
