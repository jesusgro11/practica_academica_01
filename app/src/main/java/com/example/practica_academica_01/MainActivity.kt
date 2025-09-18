package com.example.practica_academica_01

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // getting the recyclerview by its id
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPersonas)

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ArrayList of class Alumno
        val data = ArrayList<Alumno>()

        // Add elements to my data list
        for (i in 1..10) {
            data.add(Alumno(nombre = "Jesus Guerrero", cuenta = "20204468", correo = "jserfi@uacj.mx", imagen = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStrutvA0q2q2vKJsmbZ-mbDXNmvrY7mVA34w&s"))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = AlumnoAdapter(this, data)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }
}