package com.example.alpe;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.Activity;

public class TuPez extends Activity {
    private Spinner fishSpinner;
    private TextView fishInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_pez);

        // Obtener referencias a vistas
        fishSpinner = findViewById(R.id.fishSpinner);
        fishInfoTextView = findViewById(R.id.fishInfoTextView);

        // Crear un adaptador para la lista desplegable
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_de_peces, // Recurso que contiene los nombres de los peces
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador a la lista desplegable
        fishSpinner.setAdapter(adapter);

        // Manejar la selección de un pez en la lista desplegable
        fishSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener el nombre del pez seleccionado
                String selectedFish = (String) parentView.getItemAtPosition(position);

                // Aquí puedes mostrar información específica del pez seleccionado
                String fishInfo = obtenerInformacionDelPez(selectedFish);

                // Mostrar la información en el TextView
                fishInfoTextView.setText(fishInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Hacer algo si no se selecciona nada
            }
        });
    }

    // Este método obtiene la información del pez seleccionado
    private String obtenerInformacionDelPez(String selectedFish) {
        // Aquí debes implementar la lógica para obtener la información del pez
        // Puedes utilizar un arreglo, una base de datos o cualquier otra fuente de datos

        // Ejemplo: Supongamos que tenemos un arreglo de información de peces
        String[] nombresDePeces = getResources().getStringArray(R.array.tipos_de_peces);
        String[] informacionDePeces = getResources().getStringArray(R.array.informacion_de_peces);

        for (int i = 0; i < nombresDePeces.length; i++) {
            if (nombresDePeces[i].equals(selectedFish)) {
                return informacionDePeces[i];
            }
        }

        return "Información no disponible para este pez.";
    }
}
