package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VacunatoriosActivity extends AppCompatActivity {

    private String cookie;
    private ArrayList<JSONObject> vacunatorios = new ArrayList<JSONObject>();
    private Location gps;
    FusedLocationProviderClient fusedLocationClient;
    TextView vac1, vac2, vac3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunatorios);

        cookie = ((CookieClass) this.getApplication()).getCookie();
        vac1 = findViewById(R.id.vac1);
        vac2 = findViewById(R.id.vac2);
        vac3 = findViewById(R.id.vac3);


        //permisos de ubicacion
        if(ActivityCompat.checkSelfPermission(VacunatoriosActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //Cuando tenemos el permiso
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            getLocation();

        }else{
            ActivityCompat.requestPermissions(VacunatoriosActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            finish();
        }


    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            System.out.println(String.valueOf((location.getLatitude())));
                            System.out.println(String.valueOf(location.getLongitude()));
                            gps = location;
                            getVacunatorios();
                        }
                    }
                });
    }

    public void getVacunatorios(){
        System.out.println("entro al getVacunatorios");
        //hago el pedido de los vacunatorios
        String url = "http://10.0.2.2:8080/grupo15-services/rest/vacunatorios/listar";
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonArrayRequest or = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            try {
                                vacunatorios.add(response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        continuaFlujo();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response: ", error.toString());
                        Toast.makeText(VacunatoriosActivity.this,"Todavia no se asignaron los vacunatorios", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        rq.add(or);
    }

    public void continuaFlujo(){
        System.out.println("entro al continuarFlujo");
        System.out.println(gps.getLatitude());
        System.out.println(vacunatorios.size());
        try {
            vacunatorios = bubbleSort(vacunatorios);
            vac1.setText(vacunatorios.get(0).getString("nombre"));
            if(vacunatorios.size() >= 2) {
                vac2.setText(vacunatorios.get(1).getString("nombre"));
                if(vacunatorios.size() >= 3){
                    vac3.setText(vacunatorios.get(2).getString("nombre"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //acomoda el arreglo con los mas cercanos arriba
    public ArrayList<JSONObject> bubbleSort(ArrayList<JSONObject> v) throws JSONException {
        boolean sorted = false;
        JSONObject temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < v.size() - 1; i++) {
                double d1 = getDistancia(Double.parseDouble(v.get(i).getString("latitud")), Double.parseDouble(v.get(i).getString("longitud")));
                double d2 = getDistancia(Double.parseDouble(v.get(i+1).getString("latitud")), Double.parseDouble(v.get(i+1).getString("longitud")));
                if (d1 > d2) {
                    temp = v.get(i);
                    v.set(i, v.get(i+1));
                    v.set(i+1, temp);
                    sorted = false;
                }
//                if (v[i] > v[i+1]) {
//                    temp = v[i];
//                    v[i] = v[i+1];
//                    v[i+1] = temp;
//                    sorted = false;
//                }
            }
        }
        return v;
    }

    public double getDistancia(double lat2, double lng2) {
        double radioTierra = 6371;//en kilómetros
        double dLat = Math.toRadians(lat2 - gps.getLatitude());
        double dLng = Math.toRadians(lng2 - gps.getLongitude());
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(gps.getLatitude())) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return distancia;
    }
}