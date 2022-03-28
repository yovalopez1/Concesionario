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

public class FacturaActivity extends AppCompatActivity {

    EditText jetCodigo, jetfecha, jetIdentificacion, getJetFacturaPlaca;
    Button jbtGuardar, jbtConsultar, jbtAnular, jbtCancelar, jbtRegresar;
    long resp;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        getSupportActionBar().hide();

        jetCodigo = findViewById(R.id.etcodigo);
        jetfecha = findViewById(R.id.etfacturamarca);
        jetIdentificacion = findViewById(R.id.etidentificacion);
        getJetFacturaPlaca = findViewById(R.id.etfacturaplaca);
        jbtGuardar = findViewById(R.id.btguardarfactura);
        jbtConsultar = findViewById(R.id.btconsultarfactura);
        jbtAnular = findViewById(R.id.btanularFactura);
        jbtCancelar = findViewById(R.id.btcancelarfactura);
        jbtRegresar = findViewById(R.id.btregresar);
    }

    public void GuardarFactura(View view) {
        String fecha, identificacion, fPlaca;
        codigo = jetCodigo.getText().toString();
        fecha = jetfecha.getText().toString();
        identificacion = jetIdentificacion.getText().toString();
        fPlaca = getJetFacturaPlaca.getText().toString();
        if (codigo.isEmpty() || fecha.isEmpty() || identificacion.isEmpty() || fPlaca.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetCodigo.requestFocus();
        } else {
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor filaC = db.rawQuery("select * from TblCliente where idcliente = '" + identificacion + "'", null);
            Cursor filaV = db.rawQuery("select * from TblVehiculo where placa = '" + fPlaca + "'", null);
            if (filaC.moveToNext()) {
                if (filaV.moveToNext()) {
                    db.close();
                    ConexionSqlite admin1 = new ConexionSqlite(this, "concecionario3.db", null, 1);
                    SQLiteDatabase db1 = admin1.getWritableDatabase();
                    ContentValues registrofactura = new ContentValues();
                    registrofactura.put("codigofactura", codigo);
                    registrofactura.put("fecha", fecha);
                    registrofactura.put("placa", fPlaca);
                    registrofactura.put("idcliente", identificacion);
                    resp = db1.insert("TblFactura", null,registrofactura);
                    registrofactura.put("activo", "no");
                    if(resp > 0){
                        LimpiarCampos();
                        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
                    }
                    db1.close();
                }else{
                    Toast.makeText(this, "El vehiculo no existe", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "El cliente no existe", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ConsultarF(View view){
        ConsultarFactura();
    }

    public void ConsultarFactura() {
        codigo = jetCodigo.getText().toString();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "El codigo es requerido para la busqueda de la factura", Toast.LENGTH_SHORT).show();
            jetCodigo.requestFocus();
        } else {
            //cambiar el nombre de la base de datos (de concecionario2 a concecionario3)en donde exita
            ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from TblFactura where codigofactura = '" + codigo + "'", null);
            if (fila.moveToNext()) {
                jetCodigo.setText(fila.getString(1));
                jetfecha.setText(fila.getString(2));
                jetIdentificacion.setText(fila.getString(3));
                getJetFacturaPlaca.setText(fila.getString(4));
            } else {
                Toast.makeText(this, "Registro no Encontrado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void AnularFactura(View view){
        ConsultarFactura();
        ConexionSqlite admin = new ConexionSqlite(this, "concecionario3.db", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues dato = new ContentValues();
        dato.put("codigofactura", codigo);
        dato.put("actico", "no");
        resp = db.update("TblFactura", dato,"codigofactura'" + codigo + "'",null);
        if (resp > 0){
            Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
            LimpiarCampos();
        }else{
            Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void CancelarFactura(View view){LimpiarCampos();}

    public void RegresarF(View view){
        Intent intMenu=new Intent(this,MainActivity.class);
        startActivity(intMenu);
    }

    private void LimpiarCampos() {
        jetCodigo.setText("");
        jetfecha.setText("");
        jetIdentificacion.setText("");
        getJetFacturaPlaca.setText("");
        jetCodigo.requestFocus();
    }
}
