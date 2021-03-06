package com.quicklistv_01.SegundaPantallas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.quicklistv_01.Class.Global;
import com.quicklistv_01.R;

import java.util.Calendar;

public class CalendarioCursos extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private Button btn_buscar;

    // HTTP stuff
    private Global globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        globalData = (Global) getApplicationContext();

        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_buscar = (Button) findViewById(R.id.btn_buscar);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarioCursos.this, CursosConsulta.class);
                String f = day+"-"+(month+1)+"-"+year;
                intent.putExtra("fecha", f);
                globalData.setFechaCurrent(f);
                //Toast.makeText(getApplicationContext(), f, Toast.LENGTH_SHORT)
                  //      .show();
                startActivity(intent);
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Seleccione la fecha que desea buscar", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            year = arg1;
            month = arg2;
            day = arg3;
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText("La fecha seleccionada es: " + new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
    }

}
