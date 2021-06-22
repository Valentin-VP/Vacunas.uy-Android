package com.example.vacunasuy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
                TextView cedula = (TextView) findViewById(R.id.cedula);
                TextView nombre = (TextView) findViewById(R.id.nombre);
                TextView apellido = (TextView) findViewById(R.id.apellido);
                TextView direccion = (TextView) findViewById(R.id.direccion);
                TextView email = (TextView) findViewById(R.id.email);
                TextView barrio = (TextView) findViewById(R.id.barrio);
                TextView departamento = (TextView) findViewById(R.id.departamento);
                if(cedula.getText().toString().isEmpty() || nombre.getText().toString().isEmpty() || apellido.getText().toString().isEmpty() ||
                    direccion.getText().toString().isEmpty() || email.getText().toString().isEmpty() || barrio.getText().toString().isEmpty() ||
                    departamento.getText().toString().isEmpty() || dateText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Complete todos los campo", Toast.LENGTH_SHORT).show();
                }else {
                    //creo el JSONobject con los datos que quiero
                    JSONObject registerJson = new JSONObject();
                    try {
                        registerJson.put("id", cedula.getText().toString());
                        registerJson.put("nombre", nombre.getText().toString());
                        registerJson.put("apellido", apellido.getText().toString());
                        registerJson.put("fechaNac", dateText.getText().toString());
                        registerJson.put("sexo", spinner.getSelectedItem().toString());
                        registerJson.put("email", email.getText().toString());
                        registerJson.put("direccion", direccion.getText().toString());
                        registerJson.put("departamento", departamento.getText().toString());
                        registerJson.put("barrio", barrio.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url = "http://10.0.2.2:8080/grupo15-services/rest/registro/ciudadano";
                    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    //creo el httprequest
                    OkHttpClient client = new OkHttpClient();
                    RequestBody registerBody = RequestBody.create(registerJson.toString(), MediaType.parse("application/json"));

                    //defino el Request
                    Request request = new Request.Builder()
                            .url(url)
                            .post(registerBody)
                            .build();
                    //hago el request
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) { //si falla
                            Toast.makeText(getApplicationContext(), "Error en el registro, intente nuevamente", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException { //si obtengo respuesta
                            System.out.println(response.toString());
                        }
                    });
                    Intent registrado = new Intent(Register.this, MainActivity.class);
                    startActivity(registrado);
                    finish();
                }
            }
        });
    }

    //estas 2 funciones son para que salga el datepicker
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
        String date = null;
        month = month + 1;
        if(month>10 && dayOfMonth>10) {
            date = year + "-" + month + "-" + dayOfMonth;
        }else{
            if(month<10 && dayOfMonth<10) {
                date = year + "-0" + month + "-0" + dayOfMonth;
            }else{
                if (dayOfMonth < 10) {
                    date = year + "-" + month + "-0" + dayOfMonth;
                }
                if (month < 10) {
                    date = year + "-0" + month + "-" + dayOfMonth;
                }

            }
        }
        this.dateText.setText(date);
    }
}