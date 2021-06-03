package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CertificadoActivity extends AppCompatActivity {

    private String cookie;
    JSONArray constancias = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);
        //obtengo la cookie

        cookie = getIntent().getStringExtra("cookie");
        getCertificado();

    }

    public void getCertificado(){
        System.out.println("entro al getVacunatorios");
        //hago el pedido de los vacunatorios
        String url = "http://10.0.2.2:8080/grupo15-services/rest/vacunaciones/certificado";
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest or = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                constancias = response.getJSONArray("constancias");
                                for (int i=0; i<constancias.length(); i++){
                                    System.out.println(constancias.getJSONObject(i).getString("nombre"));
                                }
                                System.out.println();
                                //continuarFlujo();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response: ", error.toString());
                        //Toast.makeText(VacunatoriosActivity.this,"Todavia no se asignaron los vacunatorios", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        rq.add(or);
    }


    public void continuarFlujo() throws JSONException {
        ListView listaConstancias = (ListView) findViewById(R.id.listaConstancias);

        ArrayList<String> constanciasAux = new ArrayList<String>();
        for(int i=0; i< constancias.length(); i++){
            constanciasAux.add(constancias.getJSONObject(i).getString("nombre"));
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, constanciasAux){
            //sobreescribo esto para poder cambiarle el color a los textView dentro de la lista
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        listaConstancias.setAdapter(adaptador);

        listaConstancias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject c = constancias.getJSONObject((position));//puede que position sea -1
                    Intent i= new Intent(CertificadoActivity.this, ConstanciaActivity.class);
                    i.putExtra("id", c.getString("idConstVac"));
                    i.putExtra("inmunidad",c.getString("periodoInmunidad"));
                    i.putExtra("dosisResibidas", c.getString("dosisRecibidas"));
                    i.putExtra("ultimaDosis", c.getString("fechaUltimaDosis"));
                    i.putExtra("vacuna", c.getString("vacuna"));
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}