package com.example.concesionario_autos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText jetUsuario, jetClave;
    Button jbtIngresar, jbtCancelar, jbtRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        jetUsuario = findViewById(R.id.etusuario);
        jetClave = findViewById(R.id.etclave);
        jbtIngresar = findViewById(R.id.btingresar);
        jbtRegistrar = findViewById(R.id.btregistrarse);
        jbtCancelar = findViewById(R.id.btcancelar);
    }

    public void Ingresar(View view) {
        String usuario,clave;
        usuario = jetUsuario.getText().toString();
        clave = jetClave.getText().toString();
        if(usuario.isEmpty() || clave.isEmpty()){
            Toast.makeText(this, "Usuario y clave requeridos", Toast.LENGTH_SHORT).show();
            jetUsuario.requestFocus();
        }else {
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from TlbCliente where usuario = '" + usuario + "' and clave = '" + clave + "'", null);
            if (fila.moveToNext()) {
                Intent intMenu = new Intent(this, MenuActivity.class);
                intMenu.putExtra("nombres",usuario);
                startActivity(intMenu);
            } else {
                Toast.makeText(this, "Usuario o clave invalida", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void Cancelar(View view){

        jetUsuario.setText("");
        jetClave.setText("");
        jetUsuario.requestFocus();
    }

    public void Registrarse(View view){

        Intent intRegistrarse = new Intent(this, RegistrarseActivity.class);
        startActivity(intRegistrarse);
    }
}