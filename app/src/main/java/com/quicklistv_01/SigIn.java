package com.quicklistv_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SigIn extends AppCompatActivity {
    Button btn_go, btn_can;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_in);
        btn_go = (Button) findViewById(R.id.btn_go);
        btn_can = (Button) findViewById(R.id.btn_cancelar);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigIn.this, Login.class);
                startActivity(intent);
            }
        });

        btn_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigIn.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
