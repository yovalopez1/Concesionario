package com.example.concesionario_autos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button jbtVehiculos,jbtFactura,jbtListar,jbtUsuario,jbtSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        jbtVehiculos = findViewById(R.id.btvehiculos);
        jbtFactura = findViewById(R.id.btfacturas);
        jbtListar = findViewById(R.id.btlistarvehiculos);
        jbtUsuario = findViewById(R.id.btususario);
        jbtSalir = findViewById(R.id.btsalir);
    }

    public void Vehiculos(View view){
        Intent intVehiculos = new Intent(this, VehiculoActivity.class);
        startActivity(intVehiculos);
    }

    public void Facturas(View view){
        Intent intFacturas = new Intent(this, FacturaActivity.class);
        startActivity(intFacturas);
    }

    public void Mostrar(View view){
        String usuario;
        usuario = getIntent().getStringExtra("nombres");
        Intent intMostrar = new Intent(this, MostrarActivity.class);
        intMostrar.putExtra("usuario", usuario);
        startActivity(intMostrar);
    }

    public void Usuarios(View view){
        Intent intUsuarios = new Intent(this, RegistrarseActivity.class);
        startActivity(intUsuarios);
    }

    public void Salir(View view){
        Intent intSalir = new Intent(this, MainActivity.class);
        startActivity(intSalir);
    }
}