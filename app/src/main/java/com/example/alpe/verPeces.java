package com.example.alpe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class verPeces extends Activity {
    private TextView pezInfoTextView;
    private TextView pezName;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_peces);

        pezInfoTextView = findViewById(R.id.fishInfo);

        databaseReference = FirebaseDatabase.getInstance().getReference("Mis Peces");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Pez> peces = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pez pez = snapshot.getValue(Pez.class);
                    peces.add(pez);
                }

                mostrarPeces(peces);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void mostrarPeces(List<Pez> peces) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pez pez : peces) {
            stringBuilder.append("Nombre: ").append(pez.getNombre()).append("\n");
            stringBuilder.append("Cantidad: ").append(pez.getCantidad()).append("\n");
        }
        pezInfoTextView.setText(stringBuilder.toString());
    }
}
