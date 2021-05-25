package com.example.vacunasuy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class Register extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView registrarse = (TextView) findViewById(R.id.registrarse);
        this.dateText = (TextView) findViewById(R.id.date);

        //cargo el Spinner de Sexo
        Spinner spinner = (Spinner) findViewById(R.id.sexo);
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.sexos, R.layout.spiner_sexo);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //onClick para el datepicker
        dateText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        //onClick para el registrarse
        registrarse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Register.this, SecondActivity.class);
                startActivity(registro);
            }
        });
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =  dayOfMonth + "/" +month + "/" + year;
        this.dateText.setText(date);
    }
}