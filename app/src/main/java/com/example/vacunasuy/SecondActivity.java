package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.ServiceWorkerController;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NetworkResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.net.URL;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class SecondActivity extends AppCompatActivity {
    //esta clase se va a crearse cuando se llegue a un http://localhost

    private String cookie = null;
    private TextView nombre;
    private CardView certificado;
    private CardView vacunatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nombre = (TextView) findViewById(R.id.nombre);
        certificado = findViewById(R.id.certificado);
        vacunatorio = findViewById(R.id.vacunatorio);

        cookie = ((CookieClass) this.getApplication()).getCookie();
        getNombre();
        //onClick del certificado
        certificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent certificado = new Intent(SecondActivity.this, CertificadoActivity.class);
                certificado.putExtra("cookie", cookie);
                startActivity(certificado);
            }
        });

        //onclick del vacunatorio
        vacunatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vacunatorios = new Intent(SecondActivity.this, VacunatoriosActivity.class);
                vacunatorios.putExtra("cookie", cookie);
                startActivity(vacunatorios);
            }
        });

    }

    public void getNombre(){
        String url = "http://10.0.2.2:8080/grupo15-services/rest/mobile/id";
        OkHttpClient client = new OkHttpClient();
        //defino el request
        Request request = new Request.Builder()
                .addHeader("Cookie", cookie)
                .url(url)
                .build();

        //hago el http request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) { //si falla
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException { //si obtengo respuesta
                    String name = Objects.requireNonNull(response.body()).string();
                    System.out.println(name);
                    nombre.setText(name);
            }
        });
    }


}

