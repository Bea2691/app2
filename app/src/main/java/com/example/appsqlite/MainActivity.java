package com.example.appsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2,et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        et1= findViewById(R.id.et1);
        et2= findViewById(R.id.et2);
        et3= findViewById(R.id.et3);


    }


public void alta(View view){
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
    //getWriteableDataBase() ---> crea o abre una base de datos que se utilizara para lectura y escritura
    SQLiteDatabase bd = admin.getWritableDatabase();

    String cod= et1.getText().toString();
    String des= et2.getText().toString();
    String precio=et3.getText().toString();
    /*
    * Los objetos ContentValues se usan para insertar filas/registros en las tablas de la BD
    * Cada objeto ContentValues representa una sola fila, como un mapa de nombres de columnas y sus valores.
    * Las consultas en Android se devuelven en objetos de tipo Cursor
    * */
    ContentValues registro = new ContentValues();
    registro.put("codigo", cod);
    registro.put("descripcion", des);
    registro.put("precio", precio);

    //Si no se establece null, el parametro nullColumnHack proporciona el nombre de la columna que acepta valores nulos
    //Acepta valores nulos para insertar explicitamente un valor null en el caso de que esten vacios
    bd.insert("articulos",null,registro);
    bd.close();
    et1.setText("");
    et2.setText("");
    et3.setText("");

    Toast.makeText(this,"Se han almacenado los datos correctamente", Toast.LENGTH_LONG).show();

}
public void consultaporcodigo(View v){
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,1);
    SQLiteDatabase bd = admin.getWritableDatabase();

    String cod= et1.getText().toString();
    Cursor fila = bd.rawQuery("select descripcion,precio from articulos where codigo =" + cod,null);

    if(fila.moveToFirst()){
        et2.setText(fila.getString(0));
        et3.setText(fila.getString(1));
    }else
        Toast.makeText(this,"no existe ese articulo con ese código", Toast.LENGTH_LONG).show();

    bd.close();
}
    public void consultapordescripcion(View v2){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String des= et2.getText().toString();
        Cursor fila = bd.rawQuery("select codigo,precio from articulos where descripcion =" +"'" + des +"'",null);

        if(fila.moveToFirst()){
            et1.setText(fila.getString(0));
            et3.setText(fila.getString(1));
        }else
            Toast.makeText(this,"no existe ese articulo con esa descripcion", Toast.LENGTH_LONG).show();

        bd.close();
    }
    public void baja(View v3){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String cod= et1.getText().toString();

        int cantidad= bd.delete("articulos", "codigo="+cod,null);


        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        if(cantidad==1)
            Toast.makeText(this,"Elemento eliminado correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"no existe ese articulo con ese código", Toast.LENGTH_LONG).show();
    }
    public void modificar(View v4){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String cod= et1.getText().toString();
        String des= et2.getText().toString();
        String precio=et3.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("descripcion", des);
        registro.put("precio", precio);

        int cantidad= bd.update("articulos",registro,"codigo=" + cod,null);
        bd.close();

        if(cantidad == 1)
            Toast.makeText(this,"Elemento modificado correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Elemento no existe", Toast.LENGTH_LONG).show();
        et1.setText("");
        et2.setText("");
        et3.setText("");



    }
}