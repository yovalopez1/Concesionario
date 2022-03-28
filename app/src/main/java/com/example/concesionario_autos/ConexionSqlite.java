package com.example.concesionario_autos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSqlite extends SQLiteOpenHelper {


    public ConexionSqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TlbCliente(IdCliente text primary key,nombreCliente text not null, usuario text not null,"+
                " clave text not null, activo text default 'si')");
        sqLiteDatabase.execSQL("CREATE TABLE TlbVehiculo(Placa text primary key,marca text not null,modelo text not null, " +
                "color text not null,costo text not null,activo text not null default'si')");
        sqLiteDatabase.execSQL("CREATE TABLE TblFactura(codigofactura text primary key,fecha text not null,placa text not null, " +
                "idcliente text not null,activo text not null default'si',constraint pkfactura foreign key(placa) references TlbVehiculo (placa), " +
                 "foreign key (idcliente) references TlbCliente(idcliente))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE TlbCliente");{
            onCreate(sqLiteDatabase);
        }
        sqLiteDatabase.execSQL("DROP TABLE TlbVehiculo");{
            onCreate(sqLiteDatabase);
        }
    }
}
