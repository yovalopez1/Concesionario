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

public class VehiculoActivity extends AppCompatActivity {

    EditText jetPlaca,jetMarca,jetModelo,jetCosto,jetColor, jetIdCliente;
    Button jbtGuardar,jbtConsultar,jbtAnular,jbtCancelar,jbtRegresar;
    int sw;
    long resp;
    String placa, idcliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        getSupportActionBar().hide();

        jetPlaca = findViewById(R.id.etplaca);
        jetMarca = findViewById(R.id.etmarca);
        jetModelo = findViewById(R.id.etmodelo);
        jetCosto = findViewById(R.id.etcosto);
        jetColor = findViewById(R.id.etcolor);
        jbtGuardar = findViewById(R.id.btguardarvehiculo);
        jbtConsultar = findViewById(R.id.btconsultar);
        jbtAnular = findViewById(R.id.btanular);
        jbtCancelar = findViewById(R.id.btcancelar);
        jbtRegresar = findViewById(R.id.btregresar);
        jetIdCliente = findViewById(R.id.etdocumento);
        sw = 0;
    }

    public void GuardarVehiculo(View view){
        Toast.makeText(this, "se", Toast.LENGTH_SHORT).show();
        String marca,color,modelo,costo;
        placa = jetPlaca.getText().toString();
        marca = jetMarca.getText().toString();
        modelo = jetModelo.getText().toString();
        costo = jetCosto.getText().toString();
        color = jetColor.getText().toString();
        idcliente = jetIdCliente.getText().toString();
        if(placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || costo.isEmpty() || color.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetPlaca.requestFocus();
        }else{
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registroVehiculo = new ContentValues();
            registroVehiculo.put("placa", placa);
            registroVehiculo.put("marca", marca);
            registroVehiculo.put("modelo", modelo);
            registroVehiculo.put("color", color);
            registroVehiculo.put("costo", costo);
            if(sw == 0)
                resp = db.insert("TlbVehiculo", null,registroVehiculo);
            else{
                resp = db.update("placa", registroVehiculo,"placa'" + placa + "'",null);
                sw=0;
            }
            if(resp > 0){
                LimpiarCampos();
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void ConsultarV(View view){
        ConsultarVehiculo();
    }

    public void ConsultarVehiculo(){

        placa = jetPlaca.getText().toString();
        if(placa.isEmpty()){
            Toast.makeText(this, "La placa es requerida para buscar", Toast.LENGTH_SHORT).show();
            jetPlaca.requestFocus();
        }else{
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from TlbVehiculo where placa = '" + placa + "'",null);
            if(fila.moveToNext()){
                if(fila.getString(5).equals("no")){
                    Toast.makeText(this, "Registro existe pero esta anulado", Toast.LENGTH_SHORT).show();
                }else{
                    sw = 1;
                    jetMarca.setText(fila.getString(1));
                    jetModelo.setText(fila.getString(2));
                    jetColor.setText(fila.getString(3));
                    jetCosto.setText(fila.getString(4));
                }
            }else{
                Toast.makeText(this, "Registro no Encontrado", Toast.LENGTH_SHORT).show();

            }
            db.close();
        }
    }

    public  void AnularVehiculo(View view){
        ConsultarVehiculo();
        if(sw==1){
            //String placa = jetPlaca.getText().toString();
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues dato = new ContentValues();
            dato.put("placa", placa);
            dato.put("activo","no");
            resp = db.update("TlbVehiculo", dato,"placa='" + placa + "'", null);
            if(resp > 0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                LimpiarCampos();
            }else{
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void CancelarV(View view){
        LimpiarCampos();
    }

    public void RegresarV(View view){
        Intent intMenu=new Intent(this,MainActivity.class);
        startActivity(intMenu);
    }

    private void LimpiarCampos(){
        sw = 0;
        jetPlaca.setText("");
        jetMarca.setText("");
        jetModelo.setText("");
        jetColor.setText("");
        jetCosto.setText("");
        jetPlaca.requestFocus();
    }
}