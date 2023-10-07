package com.example.alpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
                Button sendInfoButton = findViewById(R.id.sendInfoButton);
                sendInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Configura un Intent para enviar la información del pez
                        Intent intent = new Intent(SelTuPez.this, MisPeces.class);
                        intent.putExtra("imagenPez", imageResourceId);
                        intent.putExtra("descripcionPez", fishDescriptions[descriptionIndex]);

                        // Inicia la otra actividad
                        startActivity(intent);
                    }
                });
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada si no se selecciona nada
            }
        });
    }
}
