package com.example.alecontreras.datospersonalistalmacen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table votantes(dni integer primary key, nombre text, colegio text, nromesa integer)");
        db.execSQL("create table personas(id integer primary key autoincrement, nombre text, apellido text,edad integer,telefono text, permiso integer, fechaAlta date, nivelIngles text )");
        //db.execSQL("create table productos(codigo integer primary key autoincrement, nombre text, descripcion text, unidades integer, valor integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        //db.execSQL("drop table if exists productos");
        db.execSQL("drop table if exists personas");
        //db.execSQL("create table productos(codigo integer primary key, nombre text, descripcion text, unidades integer, valor integer)");
        db.execSQL("create table personas(id integer primary key autoincrement, nombre text, apellido text,edad integer,telefono text, permiso integer, fechaAlta date, nivelIngles text )");
    }
}