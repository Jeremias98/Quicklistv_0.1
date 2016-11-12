package com.quicklistv_01.SegundaPantallas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quicklistv_01.R;

public class Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerferences);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new Config()).commit();

    }
}
