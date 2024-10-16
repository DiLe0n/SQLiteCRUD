package com.example.sqlitecrud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// Crear la base de datos
        database = openOrCreateDatabase("mi_base_de_datos", Context.MODE_PRIVATE, null)

// Crear la tabla
        database.execSQL("DROP TABLE IF EXISTS usuarios")
        database.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY, nombre TEXT, correo TEXT)")
        //database.execSQL("ALTER TABLE usuarios ADD COLUMN correo TEXT")

        val editTextId = findViewById<EditText>(R.id.editTextId)
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)

// Agregar código para crear registros
        val botonCrear = findViewById<Button>(R.id.botonCrear)
        botonCrear.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val correo = editTextCorreo.text.toString()

            // Insertar el registro
            database.execSQL("INSERT INTO usuarios (nombre, correo) VALUES ('$nombre', '$correo')")
            //database.execSQL("INSERT INTO usuarios (correo) VALUES ('$correo')")
            println("Correo: $correo")

            // Limpiar campos de texto
            editTextNombre.text.clear()
            editTextCorreo.text.clear()
        }

// Agregar código para leer registros
        val botonLeer = findViewById<Button>(R.id.botonLeer)
        botonLeer.setOnClickListener {
            val id = editTextId.text.toString().toIntOrNull()

            // Verificar si se ingresó un ID válido
            if (id != null) {
                // Consultar el registro por ID
                val cursor = database.rawQuery("SELECT * FROM usuarios WHERE id = $id", null)

                if (cursor.moveToFirst()) {
                    val nombre = cursor.getString(1)
                    val correo = cursor.getString(2)

                    // Mostrar el nombre y correo en los EditText
                    editTextNombre.setText(nombre)
                    editTextCorreo.setText(correo)
                    println("ID: $id, Nombre: $nombre, Correo: $correo")
                } else {
                    // Si no se encuentra el registro, limpiar los campos
                    editTextNombre.text.clear()
                    editTextCorreo.text.clear()
                }



                // Cerrar el cursor
                cursor.close()
            }
        }

// Agregar código para actualizar registros
        val botonActualizar = findViewById<Button>(R.id.botonActualizar)
        botonActualizar.setOnClickListener {
            val id = editTextId.text.toString().toIntOrNull()
            val nombre = editTextNombre.text.toString()
            val correo = editTextCorreo.text.toString()

            if (id != null) {
                // Actualizar el registro
                database.execSQL("UPDATE usuarios SET nombre='$nombre', correo='$correo' WHERE id=$id")
            }
        }

// Agregar código para eliminar registros
        val botonEliminar = findViewById<Button>(R.id.botonEliminar)
        botonEliminar.setOnClickListener {
            val id = editTextId.text.toString().toIntOrNull()

            if (id != null) {
                // Eliminar el registro
                database.execSQL("DELETE FROM usuarios WHERE id=$id")

                // Limpiar los campos de texto
                editTextId.text.clear()
                editTextNombre.text.clear()
                editTextCorreo.text.clear()
            }
        }
    }
}