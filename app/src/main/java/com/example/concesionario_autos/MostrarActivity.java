package com.example.concesionario_autos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MostrarActivity extends AppCompatActivity {

    TextView jtvNombre;
    ListView jlvAutos;
    Button jbtRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        getSupportActionBar().hide();

        jtvNombre = findViewById(R.id.tvnombre);
        jlvAutos = findViewById(R.id.lvautos);
        jbtRegresar = findViewById(R.id.btregresar);

        //mostrar usuario traido desde el ingreso
        String usuario;
        usuario = getIntent().getStringExtra("usuario");
        jtvNombre.setText(usuario);
    }
}