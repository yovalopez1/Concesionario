package com.example.concesionario_autos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarseActivity extends AppCompatActivity {

    EditText jetIdentificacion, jetNombre, jetUsuario, jetClave1, jetClave2;
    Button jbtGuardar, jbtConsultar, jbtAnular, jbtCancelar, jbtRegresar;
    int sw;
    long resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        getSupportActionBar().hide();

        jetIdentificacion = findViewById(R.id.etdocumento);
        jetNombre = findViewById(R.id.etnombre1);
        jetUsuario = findViewById(R.id.etusuario);
        jetClave1 = findViewById(R.id.etclave);
        jetClave2 = findViewById(R.id.etconfirmarclave);
        jbtGuardar = findViewById(R.id.btguardar);
        jbtConsultar = findViewById(R.id.btconsultar);
        jbtAnular = findViewById(R.id.btanular);
        jbtCancelar = findViewById(R.id.btcancelar);
        jbtRegresar = findViewById(R.id.btregresar);
        sw=0;
    }

    public void Guardar(View view){

        String identificacion, nombre, usuario,clave,confirmacion;
        identificacion = jetIdentificacion.getText().toString();
        nombre = jetNombre.getText().toString();
        usuario = jetUsuario.getText().toString();
        clave = jetClave1.getText().toString();
        confirmacion = jetClave2.getText().toString();
        if(identificacion.isEmpty() || nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty() || confirmacion.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetIdentificacion.requestFocus();
        }else{
            if(!clave.equals(confirmacion)){
                Toast.makeText(this, "Las claves son diferentes", Toast.LENGTH_SHORT).show();
                jetIdentificacion.requestFocus();
            }else{
                ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();
                ContentValues registro = new ContentValues();
                registro.put("IdCliente", identificacion);
                registro.put("nombreCliente", nombre);
                registro.put("usuario", usuario);
                registro.put("clave", clave);
                if(sw == 0)
                    resp = db.insert("TlbCliente", null,registro);
                else{
                    resp = db.update("IdCliente", registro,"IdCliente'" + identificacion + "'",null);
                    sw=0;
                }
                if(resp > 0){
                    limpiarCampos();
                    Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        }
    }

    public  void Anular(View view){
        consultarRegistro();
        if(sw == 1){
            String identificacion = jetIdentificacion.getText().toString();
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues dato = new ContentValues();
            dato.put("IdCliente", identificacion);
            dato.put("activo","no");
            resp = db.update("TlbCliente", dato,"IdCliente='" + identificacion + "'", null);
            if(resp > 0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }else{
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void Consultar(View view){
        consultarRegistro();
    }

    public void consultarRegistro(){
        String identificacion;
        identificacion = jetIdentificacion.getText().toString();
        if(identificacion.isEmpty()){
            Toast.makeText(this, "Identificacion es requerida para buscar", Toast.LENGTH_SHORT).show();
            jetIdentificacion.requestFocus();
        }else{
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from TlbCliente where IdCliente = '" + identificacion + "'",null);
            if(fila.moveToNext()){
                if(fila.getString(4).equals("no")){
                    Toast.makeText(this, "Registro existe pero esta anulado", Toast.LENGTH_SHORT).show();
                }else {
                    sw = 1;
                    jetNombre.setText(fila.getString(1));
                    jetUsuario.setText(fila.getString(2));
                    jetClave1.setText(fila.getString(3));
                }
            }else{
                Toast.makeText(this, "Registro no Encontrado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void limpiarCampos(){
        sw = 0;
        jetIdentificacion.setText("");
        jetNombre.setText("");
        jetUsuario.setText("");
        jetClave1.setText("");
        jetClave2.setText("");
        jetIdentificacion.requestFocus();
    }

    public void Cancelar(View view){

        jetIdentificacion.setText("");
        jetNombre.setText("");
        jetUsuario.setText("");
        jetClave1.setText("");
        jetClave2.setText("");
        jetIdentificacion.requestFocus();
    }

    public void Regresar(View view){

        Intent intmain = new Intent(this, MainActivity.class);
        startActivity(intmain);
    }
}