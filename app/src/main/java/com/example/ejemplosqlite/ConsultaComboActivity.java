package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ejemplosqlite.entidades.Usuario;
import com.example.ejemplosqlite.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultaComboActivity extends AppCompatActivity {

    Spinner comboPersonas;
    TextView txtNombre, txtDocumento, txtTelefono;
    ArrayList<String> listaPersonas;
    ArrayList<Usuario> personasList;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_combo);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_usuarios", null, 1);

        comboPersonas = (Spinner) findViewById(R.id.comboPersonas);

        txtDocumento = (TextView) findViewById(R.id.txtDocumento);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtTelefono = (TextView) findViewById(R.id.txtTelefono);

        consultarListaPersonas();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPersonas);

        comboPersonas.setAdapter(adaptador);

        comboPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {

                //Si no se le da a la posición 0 es decir al Seleccione eso quiere decir que se ha clicado a uno de los usuarios entonces
                //que se muestre la info de cada uno de ellos
                //Si no es así significa que está puesto el Seleccione y todos los campos deberán de aparecer vacios
                if (position!=0){
                    //Se le tienne que poner position-1 porque en el método obtenerLista está el "Seleccione" que ya ocupa una posición
                    txtDocumento.setText(personasList.get(position-1).getId().toString());
                    txtNombre.setText(personasList.get(position-1).getNombre());
                    txtTelefono.setText(personasList.get(position-1).getTelefono());
                }else {
                    txtDocumento.setText("");
                    txtNombre.setText("");
                    txtTelefono.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Usuario persona = null;
        personasList = new ArrayList<Usuario>();

        //select * from usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO, null);

        //moveToNext hace que si hay más registros siga haciendo el bucle
        while (cursor.moveToNext()) {
            persona = new Usuario();
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));

            personasList.add(persona);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaPersonas = new ArrayList<String>();
        listaPersonas.add("Seleccione");

        for (int i = 0; i < personasList.size(); i++) {
            // de la lista de personas obtener del primer objeto el Id y concatene con el nombre
            listaPersonas.add(personasList.get(i).getId() + " - " + personasList.get(i).getNombre());
        }
    }
}
