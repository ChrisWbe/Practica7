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
        String[] args = new String[]{nombre.getText().toString()};

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
                Toast.makeText(this, "Pasa los if", Toast.LENGTH_LONG).show();
                db.update("Peluches", nuevoValor, "nombre=?", args);

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
        String[] args = new String[]{nombre.getText().toString()};

        if(id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingresa Id o Nombre para Borrar un peluche", Toast.LENGTH_LONG).show();
        }
        else if(!id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){
            db.delete("Peluches", "id="+Integer.valueOf(id.getText().toString()), null);
        }
        else if(id.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Entra", Toast.LENGTH_LONG).show();
            db.delete("Peluches", "nombre=?", args);
        }

    }

    public void _buscar(View Objeto) {
        TextView id = (TextView) findViewById(R.id.bID);
        TextView nombre = (TextView) findViewById(R.id.bNombre);
        TextView salida = (TextView) findViewById(R.id.output_buscar);

        String[] campos = new String[]{"id", "nombre", "cantidad", "valor"};
        String[] args;

        if (id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()) {
            Toast.makeText(this, "Necesitas el nombre y/o el ID del peluche", Toast.LENGTH_LONG).show();
        }
        else if(!id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()) {
            args = new String[]{id.getText().toString()};
            Cursor c = db.query("Peluches", campos, "id=?", args, null, null, null);

            if (c.moveToNext()) {
                salida.setText("");
                do {
                    int id_salida = c.getInt(0);
                    String nombre_salida = c.getString(1);
                    int cantidad_salida = c.getInt(2);
                    int valor_salida = c.getInt(3);

                    salida.setText("" + id_salida + " - " + nombre_salida + " - " + cantidad_salida + " - " + valor_salida + " - " + "\n");

                } while (c.moveToNext());
            }
        }

        else if(id.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){
            args = new String[]{nombre.getText().toString()};
            Cursor c = db.query("Peluches", campos, "nombre=?", args, null, null, null);

            if (c.moveToNext()) {
                salida.setText("");
                do {
                    int id_salida = c.getInt(0);
                    String nombre_salida = c.getString(1);
                    int cantidad_salida = c.getInt(2);
                    int valor_salida = c.getInt(3);

                    salida.setText("" + id_salida + " - " + nombre_salida + " - " + cantidad_salida + " - " + valor_salida + " - " + "\n");

                } while (c.moveToNext());
            }
        }
    }

    public void _venta(View Objeto){
        TextView id = (TextView)findViewById(R.id.vID);
        TextView nombre = (TextView)findViewById(R.id.vNombre);
        TextView cantidad = (TextView)findViewById(R.id.vCantidad);
        String[] campos = new String[]{"id", "nombre", "cantidad", "valor"};
        int v_final=0;
        int total=0;

        if(id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese ID o Nombre para realizar la venta", Toast.LENGTH_LONG).show();
        }
        else if(!id.getText().toString().isEmpty() && nombre.getText().toString().isEmpty()){

            if(cantidad.getText().toString().isEmpty()){
                Toast.makeText(this, "Ingrese la Cantidad a Vender", Toast.LENGTH_LONG).show();
            }
            else{
                String[] args = new String[]{id.getText().toString()};
                Cursor c = db.query("Peluches",campos, "id=?", args, null, null, null);

                if(c.moveToFirst()){
                    int cantidad_salida = 0;
                    int id_salida;
                    int valor_salida = 0;
                    String nombre_salida;
                    do{
                        id_salida = c.getInt(0);
                        nombre_salida = c.getString(1);
                        cantidad_salida = c.getInt(2);
                        valor_salida = c.getInt(3);
                    }while (c.moveToNext());

                    if(cantidad_salida > Integer.valueOf(cantidad.getText().toString())){
                        total = cantidad_salida-Integer.valueOf(cantidad.getText().toString());
                        v_final = valor_salida*Integer.valueOf(cantidad.getText().toString());
                        actualizar2(v_final, total, id_salida);
                        Toast.makeText(this, "Venta Realizada: "+nombre_salida+" : "+cantidad.getText().toString()+", "+v_final+" pesos", Toast.LENGTH_LONG).show();

                    }
                    else if(cantidad_salida < Integer.valueOf(cantidad.getText().toString())){
                        Toast.makeText(this, "La cantidad supera el numero de Peluches", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        else if(id.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){

            if(cantidad.getText().toString().isEmpty()){
                Toast.makeText(this, "Ingrese la Cantidad a Vender", Toast.LENGTH_LONG).show();
            }
            else{
                String[] args = new String[]{nombre.getText().toString()};
                Cursor c = db.query("Peluches",campos, "nombre=?", args, null, null, null);
                if(c.moveToFirst()){
                    int cantidad_salida = 0;
                    int id_salida;
                    int valor_salida = 0;
                    String nombre_salida;
                    do{
                        id_salida = c.getInt(0);
                        nombre_salida = c.getString(1);
                        cantidad_salida = c.getInt(2);
                        valor_salida = c.getInt(3);
                    }while (c.moveToNext());

                    if(cantidad_salida > Integer.valueOf(cantidad.getText().toString())){
                        total = cantidad_salida-Integer.valueOf(cantidad.getText().toString());
                        v_final = valor_salida*Integer.valueOf(cantidad.getText().toString());
                        actualizar2(v_final, total, id_salida);
                        Toast.makeText(this, "Venta Realizada: "+nombre_salida+" : "+cantidad.getText().toString()+", "+v_final+" pesos", Toast.LENGTH_LONG).show();


                    }
                    else if(cantidad_salida < Integer.valueOf(cantidad.getText().toString())){
                        Toast.makeText(this, "La cantidad supera el numero de Peluches", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }



    }

    public void actualizar2(int f_valor, int f_cantidad, int f_id) {


        ContentValues nuevoValor = new ContentValues();

        nuevoValor.put("cantidad", f_cantidad);
        //nuevoValor.put("cantidad", f_cantidad);
        db.update("Peluches", nuevoValor, "id="+f_id, null);
        //poner la notificacion en este lugar con la cantidad final


        ContentValues dineroNuevo = new ContentValues();

        //dineroNuevo.put("dinero", f_valor);
        //db.update("Ganancia", dineroNuevo, null, null);

    }


}
