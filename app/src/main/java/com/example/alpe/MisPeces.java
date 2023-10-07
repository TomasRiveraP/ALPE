package com.example.alpe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MisPeces extends Activity {
    private TextView pezInfoTextView;
    private ImageView pezImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_peces);

        pezInfoTextView = findViewById(R.id.fishInfoTextView);
        pezImg = findViewById(R.id.fishImageView);

        // Obtén la información del pez desde el Intent
        int imagenPez = getIntent().getIntExtra("imagenPez", 0); // Valor predeterminado 0 si no se encuentra
        String descripcionPez = getIntent().getStringExtra("descripcionPez");

        pezImg.setImageResource(imagenPez);
        pezInfoTextView.setText(descripcionPez);

        // Haz lo que necesites con la información del pez en esta clase
    }

    // Resto del código de la actividad...
}
