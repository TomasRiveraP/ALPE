package com.example.alpe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Pecera extends menuPrincipal{
    private TextView temperaturaTextView;
    private Button actualizarButton;
    private String serverURL = "http://"+ ESP8266_IP+"/temperatura";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecera);

        temperaturaTextView = findViewById(R.id.temperaturaTextView);
        actualizarButton = findViewById(R.id.actualizarButton);

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerTemperatura();
            }
        });
    }

    private void obtenerTemperatura() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        temperaturaTextView.setText("Temperatura: " + response + " °C");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        temperaturaTextView.setText("Error al obtener la temperatura.");
                    }
                }
        );

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
