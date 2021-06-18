package com.example.vacunasuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

//import com.google.firebase.messaging.FirebaseMessaging;


public class SecondActivity extends AppCompatActivity {
    //esta clase se va a crearse cuando se llegue a un http://localhost

    private String cookie = null;
    private TextView nombre;
    private CardView certificado, vacunatorio;
    Toolbar toolbar;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nombre = (TextView) findViewById(R.id.nombre);
        certificado = findViewById(R.id.certificado);
        vacunatorio = findViewById(R.id.vacunatorio);
        toolbar =  (Toolbar) findViewById(R.id.navbar);
        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);//obtengo el sharedPreference
        cookie = sharedPref.getString("cookie", null);//obtengo la cookie, si no existe retorna null
        //sendToken();
        getNombre();
        getToken();
        //seteo el onclick del icono en toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref.edit().remove("cookie").commit();//limpio la cookie
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });

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

    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN_FAILED", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("TAG", token);
                        sendToken(token);
                    }
                });

    }
    private void sendToken(String token){ //envio el token al central
        String url = "http://10.0.2.2:8080/grupo15-services/rest/app/registrar";
        OkHttpClient client = new OkHttpClient();

        //creo el jsonobject
        JSONObject firebaseJson = new JSONObject();
        try {
            firebaseJson.put("mobileToken", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody firebaseBody = RequestBody.create(firebaseJson.toString(), MediaType.parse("application/json"));
        //defino el request
        Request request = new Request.Builder()
                .addHeader("Cookie", cookie)
                .post(firebaseBody)
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
                Log.i("MOBILE_TOKEN", "token enviado con exito");
            }
        });
    }

}

