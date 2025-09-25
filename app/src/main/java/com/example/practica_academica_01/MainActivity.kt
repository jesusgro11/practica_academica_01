package com.example.practica_academica_01

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlumnoAdapter
    private lateinit var fabAddAlumno: FloatingActionButton
    private val data = mutableListOf<Alumno>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        loadInitialData()
        setupFAB()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerPersonas)
        fabAddAlumno = findViewById(R.id.fabAddAlumno)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AlumnoAdapter(this, data)
        recyclerView.adapter = adapter
    }

    private fun loadInitialData() {
        // Add initial data
        for (i in 1..2) {
            data.add(Alumno(
                nombre = "Alumno $i",
                cuenta = "20190$i$i$i",
                correo = "alumno$i@uacj.mx",
                imagen = "data:image/png;base64,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"
            ))
        }
        adapter.refreshList()
    }

    private fun setupFAB() {
        fabAddAlumno.setOnClickListener {
            showAddAlumnoDialog()
        }
    }

    private fun showAddAlumnoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_alumno, null)
        val etNombre = dialogView.findViewById<TextInputEditText>(R.id.etNombre)
        val etCuenta = dialogView.findViewById<TextInputEditText>(R.id.etCuenta)
        val etCorreo = dialogView.findViewById<TextInputEditText>(R.id.etCorreo)
        val etImagen = dialogView.findViewById<TextInputEditText>(R.id.etImagen)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton(getString(R.string.agregar)) { _, _ ->
                val nombre = etNombre.text.toString().trim()
                val cuenta = etCuenta.text.toString().trim()
                val correo = etCorreo.text.toString().trim()
                val imagen = etImagen.text.toString().trim()

                if (validateInput(nombre, cuenta, correo)) {
                    val finalImagen = if (imagen.isEmpty()) {
                        "data:image/png;base64,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"
                    } else imagen

                    val nuevoAlumno = Alumno(nombre, cuenta, correo, finalImagen)
                    adapter.addAlumno(nuevoAlumno)

                    Toast.makeText(this, getString(R.string.alumno_agregado_exitosamente), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.por_favor_completa_campos), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
            .show()
    }

    private fun validateInput(nombre: String, cuenta: String, correo: String): Boolean {
        return nombre.isNotEmpty() && cuenta.isNotEmpty() && correo.isNotEmpty()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                adapter.refreshList()
                Toast.makeText(this, getString(R.string.lista_actualizada), Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_delete_all -> {
                showDeleteAllDialog()
                true
            }
            R.id.menu_settings -> {
                Toast.makeText(this, getString(R.string.configuracion_proximamente), Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_about -> {
                showAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteAllDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.eliminar_todos))
            .setMessage(getString(R.string.estas_seguro_eliminar))
            .setPositiveButton(getString(R.string.eliminar)) { _, _ ->
                adapter.clearAll()
                Toast.makeText(this, getString(R.string.todos_alumnos_eliminados), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
            .show()
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.acerca_de))
            .setMessage(getString(R.string.acerca_descripcion))
            .setPositiveButton(getString(R.string.ok), null)
            .create()
            .show()
    }
}