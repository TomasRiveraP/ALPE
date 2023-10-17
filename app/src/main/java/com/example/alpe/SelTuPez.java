package com.example.alpe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelTuPez extends Activity {
    private Spinner pezSpinner;
    private TextView pezInfoTextView;
    private ImageView pezImg;
    private EditText numberEditText;
    private Button addButton;
    private Button subtractButton;

    private TextView pezName;

    private Button agregarPezButton;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_pez);

        databaseReference = FirebaseDatabase.getInstance().getReference("MisPeces");

        numberEditText = findViewById(R.id.numberEditText);
        addButton = findViewById(R.id.addButton);
        subtractButton = findViewById(R.id.subtractButton);
        pezSpinner = findViewById(R.id.fishSpinner);
        pezInfoTextView = findViewById(R.id.fishInfoTextView);
        pezImg = findViewById(R.id.fishImageView);
        pezName = findViewById(R.id.nombrePezEditText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipos_de_peces, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pezSpinner.setAdapter(adapter);

        pezSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedFish = parentView.getItemAtPosition(position).toString();
                String[] fishDescriptions = getResources().getStringArray(R.array.informacion_de_peces);
                String[] fishName = getResources().getStringArray(R.array.tipos_de_peces);

                int imageResourceId = getResources().getIdentifier(selectedFish.toLowerCase(), "drawable", getPackageName());
                int descriptionIndex = position;

                pezImg.setImageResource(imageResourceId);
                pezName.setText(fishName[descriptionIndex]);
                pezInfoTextView.setText(fishDescriptions[descriptionIndex]);

                Button sendInfoButton = findViewById(R.id.sendInfoButton);
                sendInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        agregarPez();
                    }
                });
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incrementNumber();
                    }
                });
                subtractButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decrementNumber();
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada si no se selecciona nada
            }
        });
    }
    private void incrementNumber() {
        int currentValue = Integer.parseInt(numberEditText.getText().toString());
        numberEditText.setText(String.valueOf(currentValue + 1));
    }
    private void decrementNumber() {
        int currentValue = Integer.parseInt(numberEditText.getText().toString());
        if (currentValue > 0) {
            numberEditText.setText(String.valueOf(currentValue - 1));
        }
    }
    private void agregarPez() {
        String nombrePez = pezName.getText().toString();
        int cantidadPez = Integer.parseInt(numberEditText.getText().toString());

        Pez nuevoPez = new Pez(nombrePez, cantidadPez);

        if (nombrePez != null) {
            databaseReference.child(nombrePez).setValue(nuevoPez);
        }

        pezName.setText("");
        numberEditText.setText("");

        Toast.makeText(SelTuPez.this, "Pez agregado con exito", Toast.LENGTH_SHORT).show();
    }
}
