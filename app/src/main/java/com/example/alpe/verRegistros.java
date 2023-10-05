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

public class verRegistros extends Activity {

    private TextView textViewRegistros;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registros);

        textViewRegistros = findViewById(R.id.textViewRegistros);

        databaseReference = FirebaseDatabase.getInstance().getReference("registros");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Registro> registros = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Registro registro = snapshot.getValue(Registro.class);
                    registros.add(registro);
                }

                mostrarRegistros(registros);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void mostrarRegistros(List<Registro> registros) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Registro registro : registros) {
            stringBuilder.append("ID: ").append(registro.getId()).append("\n");
            stringBuilder.append("Fecha: ").append(registro.getFecha()).append("\n");
            stringBuilder.append("Contenido: ").append(registro.getContenido()).append("\n\n");
        }
        textViewRegistros.setText(stringBuilder.toString());
    }
}