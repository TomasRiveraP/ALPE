package com.example.alpe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
public class SelTuPez extends Activity {
    private Spinner pezSpinner;
    private TextView pezInfoTextView;
    private ImageView pezImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_pez);

        pezSpinner = findViewById(R.id.fishSpinner);
        pezInfoTextView = findViewById(R.id.fishInfoTextView);
        pezImg = findViewById(R.id.fishImageView);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipos_de_peces, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pezSpinner.setAdapter(adapter);

        pezSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedFish = parentView.getItemAtPosition(position).toString();
                String[] fishDescriptions = getResources().getStringArray(R.array.informacion_de_peces);

                int imageResourceId = getResources().getIdentifier(selectedFish.toLowerCase(), "drawable", getPackageName());
                int descriptionIndex = position;


                pezImg.setImageResource(imageResourceId);
                pezInfoTextView.setText(fishDescriptions[descriptionIndex]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada si no se selecciona nada
            }
        });
    }


    /*private String obtenerInformacionDelPez(String selectedFish) {

        String[] nombresDePeces = getResources().getStringArray(R.array.tipos_de_peces);
        String[] informacionDePeces = getResources().getStringArray(R.array.informacion_de_peces);
        int[] pezImg = {R.drawable.pez1, R.drawable.pez2, /* ... */;

        /*for (int i = 0; i < nombresDePeces.length; i++) {
            if (nombresDePeces[i].equals(selectedFish)) {
                return new Pez(selectedFish, pezImg[i], informacionDePeces[i]);
            }
        }

        return "Información no disponible para este pez.";
    }*/
}
