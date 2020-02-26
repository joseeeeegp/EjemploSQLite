package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ejemplosqlite.entidades.Usuario;

public class DetalleUsuarioActivity extends AppCompatActivity {

    TextView campoId,campoNombre,campoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        campoId=(TextView) findViewById(R.id.campoId);
        campoNombre=(TextView) findViewById(R.id.campoNombre);
        campoTelefono=(TextView) findViewById(R.id.campoTelefono);

        Bundle objetoEnviado= getIntent().getExtras();
        Usuario user=null;

        if(objetoEnviado!=null){
            user= (Usuario) objetoEnviado.getSerializable("usuario");
            campoId.setText(user.getId().toString());
            campoNombre.setText(user.getNombre());
            campoTelefono.setText(user.getTelefono());

        }
    }
}
