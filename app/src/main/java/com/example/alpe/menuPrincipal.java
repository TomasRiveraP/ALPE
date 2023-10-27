package com.example.alpe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    public static final String ESP8266_IP = "192.168.5.107";
    private ProgressBar porcentajeProgressBar;
    private TextView porcentajeTextView;
    private Handler handler = new Handler();
    private int delay = 10000; // Intervalo de actualización en milisegundos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("registros");
        Button btnOpenMenu = findViewById(R.id.btnOpenMenu);
        porcentajeProgressBar = findViewById(R.id.porcentajeProgressBar);
        porcentajeTextView = findViewById(R.id.porcentajeTextView);
        Button consultarPorcentajeButton = findViewById(R.id.consultarPorcentajeButton);
        ///startRepeatingTask();

        alimentarPeces();
        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        consultarPorcentajeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConsultarPorcentajeTask().execute();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        ///stopRepeatingTask();
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
                        Intent intent3 = new Intent(menuPrincipal.this, verPeces.class);
                        startActivity(intent3);
                        return true;
                    case R.id.opcion4:
                        Intent intent4 = new Intent(menuPrincipal.this, Pecera.class);
                        startActivity(intent4);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
    public void conexion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ESP8266_IP + "/alimentar");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

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
    public void alimentarPeces(){
        Button alimentar = findViewById(R.id.btnAlimentar);
        alimentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearRegistro();
                conexion();
            }
        });
    }
    public void crearRegistro(){
        String id = databaseReference.push().getKey();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String fecha = sdf.format(new Date());
        String contenido = "Registro generado el " + fecha;
        Registro registro = new Registro(id, fecha, contenido);
        if (id != null) {
            databaseReference.child(id).setValue(registro);
        }
        Toast.makeText(menuPrincipal.this, "Botón pulsado a las " + fecha, Toast.LENGTH_SHORT).show();
    }
    private class ConsultarPorcentajeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://" + ESP8266_IP + "/ultrasonico");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("Error")) {
                int porcentaje = (int) Double.parseDouble(result);
                porcentajeProgressBar.setProgress(porcentaje);
                porcentajeTextView.setText(porcentaje + "%");
                if(porcentaje <= 20){
                    Toast.makeText(menuPrincipal.this, "Llene el tanque de almacenamiento de alimento", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /*private void startRepeatingTask() {
        statusChecker.run();
    }

    private void stopRepeatingTask() {
        handler.removeCallbacks(statusChecker);
    }

    Runnable statusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                new ConsultarPorcentajeTask().execute();
            } finally {
                handler.postDelayed(this, delay);
            }
        }
    };*/

}