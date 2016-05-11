package com.christianquintero.practica7peluchitos;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeluchesSQL peluchitos = new PeluchesSQL(this);

        db = peluchitos.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (item.getItemId()){
            case R.id.mAgregar:
                Agregar_Fragment fragment = new Agregar_Fragment();
                ft.replace(android.R.id.content, fragment).commit();
                return true;
            case R.id.mBuscar:
                Buscar_Fragment fragment1 = new Buscar_Fragment();
                ft.replace(android.R.id.content, fragment1).commit();
                return true;
            case R.id.mEliminar:
                Eliminar_Fragment fragment2 = new Eliminar_Fragment();
                ft.replace(android.R.id.content, fragment2).commit();
                return true;
            case R.id.mActualizar:
                Actualizar_Fragment fragment3 = new Actualizar_Fragment();
                ft.replace(android.R.id.content, fragment3).commit();
                return true;

            case R.id.mPeluches:
                Inventario_Fragment fragment4 = new Inventario_Fragment();
                ft.replace(android.R.id.content, fragment4).commit();

                return true;

            case R.id.mVenta:
                Venta_Fragment fragment5 = new Venta_Fragment();
                ft.replace(android.R.id.content, fragment5).commit();
                return true;

            case R.id.mGanancias:
                Ganancias_Fragment fragment6 = new Ganancias_Fragment();
                ft.replace(android.R.id.content, fragment6).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void _agregar(View Objeto){
        EditText id = (EditText)findViewById(R.id.aID);
        EditText nombre = (EditText)findViewById(R.id.aNombre);
        EditText cantidad = (EditText)findViewById(R.id.aCantidad);
        EditText valor = (EditText)findViewById(R.id.aValor);

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("id", Integer.valueOf(id.getText().toString()));
        nuevoRegistro.put("nombre", nombre.getText().toString());
        nuevoRegistro.put("cantidad", Integer.valueOf(cantidad.getText().toString()));
        nuevoRegistro.put("valor", Integer.valueOf(valor.getText().toString()));

        db.insert("Peluches", null, nuevoRegistro);
        Toast.makeText(this, "Peluche Creado", Toast.LENGTH_LONG).show();
    }

    public void _inventario(View Objeto){
        TextView misPeluches = (TextView)findViewById(R.id.iPeluches);

        Toast.makeText(this, "Funciona", Toast.LENGTH_LONG).show();
        misPeluches.setText("");
        Cursor c = db.rawQuery("SELECT id, nombre, cantidad, valor FROM Peluches", null);
        if(c.moveToFirst()){
            do{
                int c_id = c.getInt(0);
                String c_nombre = c.getString(1);
                int c_cantidad = c.getInt(2);
                int c_valor = c.getInt(3);

                misPeluches.append(""+c_id+" -- "+c_nombre+" -- "+c_cantidad+" -- "+c_valor+"\n");
            }while (c.moveToNext());
        }

    }

    public void _actualizar(View Objeto){
        EditText id = (EditText)findViewById(R.id.mID);
        EditText nombre = (EditText)findViewById(R.id.mNombre);
        EditText cantidad = (EditText)findViewById(R.id.mCantidad);
        EditText valor = (EditText)findViewById(R.id.mValor);

        ContentValues nuevoValor = new ContentValues();

        if(id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Actualiza el Peluche por el ID o por su Nombre", Toast.LENGTH_LONG).show();
        }
        else if(id.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){
            //obtiene el nombre
            if(cantidad.getText().toString().isEmpty() && valor.getText().toString().isEmpty()){
                Toast.makeText(this, "Ingresa la Cantidad y/o el Valor del Peluche a modificar", Toast.LENGTH_LONG).show();
            }
            else{
                if(!cantidad.getText().toString().isEmpty() && valor.getText().toString().isEmpty()){
                    nuevoValor.put("cantidad", Integer.valueOf(cantidad.getText().toString()));

                }
                else if (cantidad.getText().toString().isEmpty() && !valor.getText().toString().isEmpty()){
                    nuevoValor.put("valor", Integer.valueOf(valor.getText().toString()));

                }
                else if(!cantidad.getText().toString().isEmpty() && !valor.getText().toString().isEmpty()){
                    nuevoValor.put("valor", Integer.valueOf(valor.getText().toString()));
                    nuevoValor.put("cantidad", Integer.valueOf(cantidad.getText().toString()));
                }
                db.update("Peluches", nuevoValor, "nombre="+nombre.getText().toString(), null);
            }



        }
        else if(!id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){
            //obtiene el ID
            if(cantidad.getText().toString().isEmpty() && valor.getText().toString().isEmpty()){
                Toast.makeText(this, "Ingresa la Cantidad y/o el Valor del Peluche a modificar", Toast.LENGTH_LONG).show();
            }
            else{
                if(!cantidad.getText().toString().isEmpty() && valor.getText().toString().isEmpty()){
                    nuevoValor.put("cantidad", Integer.valueOf(cantidad.getText().toString()));

                }
                else if (cantidad.getText().toString().isEmpty() && !valor.getText().toString().isEmpty()){
                    nuevoValor.put("valor", Integer.valueOf(valor.getText().toString()));

                }
                else if(!cantidad.getText().toString().isEmpty() && !valor.getText().toString().isEmpty()){
                    nuevoValor.put("valor", Integer.valueOf(valor.getText().toString()));
                    nuevoValor.put("cantidad", Integer.valueOf(cantidad.getText().toString()));
                }

                db.update("Peluches", nuevoValor, "id="+Integer.valueOf(id.getText().toString()), null);
            }

        }


    }

    public void _eliminar(View Objeto){
        TextView id = (TextView)findViewById(R.id.eID);
        TextView nombre = (TextView)findViewById(R.id.eNombre);

        if(id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingresa Id o Nombre para Borrar un peluche", Toast.LENGTH_LONG).show();
        }
        else if(!id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){
            db.delete("Peluches", "id="+Integer.valueOf(id.getText().toString()), null);
        }
        else if(id.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){
            db.delete("Peluches", "nombre="+nombre.getText().toString(), null);
        }
    }


}
