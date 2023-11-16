package com.example.alpe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class contacto extends menuPrincipal {

    private Button btnEnviarComentario;

    private EditText editTextComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        editTextComentario = findViewById(R.id.editTextComentario);
        btnEnviarComentario = findViewById(R.id.btnEnviarComentario);

        btnEnviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///String comentario = editTextComentario.getText().toString();
                ///enviarComentarioPorWhatsApp(comentario);
                String numeroWhatsAppDesarrollador = "573203895397";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "whatsapp://send?phone="+numeroWhatsAppDesarrollador+"&text="+editTextComentario.getText().toString();
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

}
