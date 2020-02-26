package com.example.ejemplosqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ejemplosqlite.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);

    }

    public void onClick(View view) {
        Intent miIntent = null;
        switch (view.getId()) {
            case R.id.btnOpcionRegistro:
                miIntent = new Intent(MainActivity.this, RegistroUsuariosActivity.class);
                break;
            case R.id.btnConsultaIndividual:
                miIntent = new Intent(MainActivity.this, ConsultarUsuariosActivity.class);
                break;
            case R.id.btnConsultaSpinner:
                miIntent = new Intent(MainActivity.this, ConsultaComboActivity.class);
                break;
            case R.id.btnConsultaLista:
                miIntent = new Intent(MainActivity.this, ConsultarListaListViewActivity.class);
                break;
            case R.id.btnConsultaListaPersonasRecycler:
                miIntent = new Intent(MainActivity.this, ListaPersonasRecycler.class);
                break;
            case R.id.floatingActionButton:
              registrar();
                break;
        }
        if (miIntent != null) {
            startActivity(miIntent);
        }
    }

    private void registrar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir una nueva constante");

// Set up the input
        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        final EditText input3 = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

// Add a TextView here for the "Title" label, as noted in the comments
        input.setHint("id");
        layout.addView(input); // Notice this is an add method

// Add another TextView here for the "Description" label
        input2.setHint("Nombre");
        layout.addView(input2); // Another add method

        // Add another TextView here for the "Description" label
        input3.setHint("telefono");
        layout.addView(input3); // Another add method
        // Again this is a set method, not add
        builder.setView(layout);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_usuarios", null, 1);

                SQLiteDatabase db = conn.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPO_ID, input.getText().toString());
                values.put(Utilidades.CAMPO_NOMBRE, input2.getText().toString());
                values.put(Utilidades.CAMPO_TELEFONO, input3.getText().toString());

                Long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID, values);

                Toast.makeText(getApplicationContext(), "Id Registro: " + idResultante, Toast.LENGTH_SHORT).show();
                db.close();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    }









