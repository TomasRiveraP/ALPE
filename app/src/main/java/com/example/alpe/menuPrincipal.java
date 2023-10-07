package com.example.alpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class menuPrincipal extends Activity {
    private DatabaseReference databaseReference;
    private static final String ESP8266_IP = "192.168.5.110";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("registros");

        Button alimentar = findViewById(R.id.btnAlimentar);
        Button btnOpenMenu = findViewById(R.id.btnOpenMenu);
        alimentar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id = databaseReference.push().getKey();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                String fecha = sdf.format(new Date());

                String contenido = "Registro generado el " + fecha;

                Registro registro = new Registro(id, fecha, contenido);

                if (id != null) {
                    databaseReference.child(id).setValue(registro);
                }
                Toast.makeText(menuPrincipal.this, "Botón pulsado a las " + fecha, Toast.LENGTH_SHORT).show();
                ejecutarAccion();
            }
        });
        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opcion1:
                        Intent intent = new Intent(menuPrincipal.this, verRegistros.class);
                        startActivity(intent);
                        return true;
                    case R.id.opcion2:
                        Intent intent2 = new Intent(menuPrincipal.this, SelTuPez.class);
                        startActivity(intent2);
                        return true;
                    case R.id.opcion3:
                        Intent intent3 = new Intent(menuPrincipal.this, MisPeces.class);
                        startActivity(intent3);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
    private void ejecutarAccion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // URL para la solicitud GET al ESP8266
                    URL url = new URL("http://" + ESP8266_IP + "/accion");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // Obtener la respuesta
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        in.close();

                        final String respuesta = response.toString();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Manejar la respuesta del ESP8266 aquí
                                // Por ejemplo, mostrar un Toast con la respuesta
                                Toast.makeText(menuPrincipal.this, respuesta, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}