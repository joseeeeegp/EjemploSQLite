package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ejemplosqlite.entidades.Usuario;
import com.example.ejemplosqlite.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarListaListViewActivity extends AppCompatActivity {

    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuarios;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_list_view);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        listViewPersonas =(ListView) findViewById(R.id.listViewPersonas);

        consultarListaPersonas();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewPersonas.setAdapter(adaptador);

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String informacion="id: "+listaUsuarios.get(position).getId()+"\n";
                informacion+= "Nombre: "+listaUsuarios.get(position).getNombre()+"\n";
                informacion+="Telefono: "+listaUsuarios.get(position).getTelefono()+"\n";

                Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();

                //para hacer que cuando se clique se ponga una nueva actividad con los datos
                Usuario user=listaUsuarios.get(position);

                Intent intent = new Intent(ConsultarListaListViewActivity.this,DetalleUsuarioActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario",user);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }

    private void consultarListaPersonas() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Usuario usuario=null;
        listaUsuarios=new ArrayList<Usuario>();

        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_USUARIO,null);

        while (cursor.moveToNext()){
            usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setTelefono(cursor.getString(2));

            listaUsuarios.add(usuario);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for(int i=0;i<listaUsuarios.size();i++){
            listaInformacion.add(listaUsuarios.get(i).getId()+" - "
                    +listaUsuarios.get(i).getNombre());
        }
    }
}
