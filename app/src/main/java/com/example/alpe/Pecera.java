package com.example.alpe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText phEditText;
    private TextView mensajeTextView;
    private Button calcular;
    private Button tutorial;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecera);

        temperaturaTextView = findViewById(R.id.temperaturaTextView);
        actualizarButton = findViewById(R.id.actualizarButton);
        phEditText = findViewById(R.id.phEditText);
        mensajeTextView = findViewById(R.id.mensajeTextView);
        calcular = findViewById(R.id.calcular);
        tutorial = findViewById(R.id.tutorial);

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerTemperatura();
            }
        });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pecera.this, tutoPH.class);
                startActivity(intent);
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
    public void calcular() {
        String phStr = phEditText.getText().toString();
        String tempStr = temperaturaTextView.getText().toString();
        if (!phStr.isEmpty()) {
            try {
                double ph = Double.parseDouble(phStr);
                double temperatura = Double.parseDouble(tempStr.replace("Temperatura: ", "").replace(" °C", ""));

                if (ph >= 6.5 && ph <= 7.5 && temperatura >= 16.0 && temperatura <= 28.0) {
                    mensajeTextView.setText("Nivel de pH y temperatura óptimos. ¡El agua está en buenas condiciones!");
                    mensajeTextView.setTextColor(Color.GREEN);
                } else if((ph < 6.5 && temperatura >= 16.0 && temperatura <= 28.0 )|| (ph > 7.5 && temperatura >= 16.0 && temperatura <= 28.0)) {
                    mensajeTextView.setText("Nivel de temperatura adecuado, ph fuera del rango óptimo. ¡Se necesita atención!");
                    mensajeTextView.setTextColor(Color.RED);
                } else if(temperatura < 16.0 || temperatura > 28.0 && ph >= 6.5 && ph <= 7.5) {
                    mensajeTextView.setText("Nivel de ph adecuado, temperatura fuera del rango óptimo. ¡Se necesita atención!");
                    mensajeTextView.setTextColor(Color.RED);
                } else {
                    mensajeTextView.setText("Nivel de pH y temperatura fuera del rango optimo. ¡El agua está en condiciones de riesgo para tus peces!");
                    mensajeTextView.setTextColor(Color.RED);
                }
            } catch (NumberFormatException e) {
                mensajeTextView.setText("Los valores de pH y/o temperatura ingresados no son válidos. Por favor, ingrese números.");
                mensajeTextView.setTextColor(Color.RED);
            }
        } else {
            mensajeTextView.setText("Por favor, ingrese valores de pH y temperatura.");
        }
    }
}
