package com.mariana.harmonia.activitys.iniciarSesion

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.mariana.harmonia.MainActivity
import com.mariana.harmonia.R
import com.mariana.harmonia.interfaces.PlantillaActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.mariana.harmonia.models.db.FirebaseDB
import com.mariana.harmonia.models.entity.User
import com.mariana.harmonia.utils.HashUtils
import com.mariana.harmonia.utils.Utils
import java.time.LocalDate

class RegistroActivity : AppCompatActivity(), PlantillaActivity {

    private lateinit var storage: FirebaseStorage
    private lateinit var firebaseAuth: FirebaseAuth

    // FUN --> OnCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_activity)
        Utils.degradadoTexto(this, R.id.VolverInicioSesion,R.color.rosa,R.color.morado)
        Utils.degradadoTexto(this, R.id.titleTextView,R.color.rosa,R.color.morado)

        firebaseAuth = FirebaseDB.getInstanceFirebase()
        val db = Firebase.firestore
        storage = FirebaseDB.getInstanceStorage()


    }

    // FUN --> Volver al inicio de sesión
    fun irIniciarSesion(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // FUN --> Salir de la aplicación
    fun irSalir(view: View) {
        Utils.salirAplicacion(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun botonCrearCuenta(view: View) {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.sonido_cuatro)
        mediaPlayer.start()
        // 1. Obtener los valores ingresados en los campos de correo y contraseña
        val emailTextView = findViewById<TextView>(R.id.editText1)
        val contraseñaTextView = findViewById<TextView>(R.id.editText3)
        val email = emailTextView.text.toString().lowercase()
        val contraseña = contraseñaTextView.text.toString()
        val nombreTextView = findViewById<TextView>(R.id.editText2)
        val nombre = nombreTextView.text.toString()


        // 2. Validar los campos
        if (email.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Validar el formato de correo electrónico
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Formato de correo electrónico incorrecto", Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Validar la longitud y composición de la contraseña
        if (!validarContraseña(contraseña)) {
            Toast.makeText(
                this,
                "La contraseña debe tener al menos 8 caracteres, 1 minúscula, 1 mayúscula y 1 número",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // 5. Llamar a una función para registrar al usuario en Firebase
        registrarUsuarioEnFirebase(email.lowercase(), contraseña, nombre)
    }

    // FUN --> Validar la contraseña
    private fun validarContraseña(contraseña: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$")
        return regex.matches(contraseña)
    }

    private fun establecerFotoPerfilPorDefecto(email: String) {
        val userId = FirebaseDB.getInstanceFirebase().currentUser?.uid
        val storageRef = storage.reference
        val defaultProfileImageRef = storageRef.child("imagenesPerfilGente/pablo.png")
        val userImageRef = storageRef.child("imagenesPerfilGente/$userId.jpg")

        defaultProfileImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            userImageRef.putBytes(bytes).addOnSuccessListener { _ ->
            }.addOnFailureListener { exception ->
                Log.e(TAG, "Error al establecer la foto de perfil por defecto: ${exception.message}")
            }
        }.addOnFailureListener { exception ->
            Log.e(TAG, "Error al descargar la foto de perfil por defecto: ${exception.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
     fun registrarUsuarioEnFirebase(email: String, contraseña: String, nombre: String) {
        val emailEncriptado = HashUtils.sha256(email.lowercase())
        val fechaRegistro = LocalDate.now()
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        mFirebaseAnalytics.setUserId(LocalDate.now().toString())

        firebaseAuth.createUserWithEmailAndPassword(email, contraseña)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val encriptado = HashUtils.sha256(email!!.lowercase())
                    println(email+"/"+encriptado)
                    println("ENCRIPTADO: $encriptado")
                    val user = User(email = emailEncriptado  , name = nombre,correo = email.lowercase(), 0, 1, mesRegistro = fechaRegistro.month, anioRegistro = fechaRegistro.year)

                    UserDao.addUser(user)
                    establecerFotoPerfilPorDefecto(email)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error al registrar usuario: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}