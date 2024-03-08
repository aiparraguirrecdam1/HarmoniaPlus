package com.mariana.harmonia.activitys

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.mariana.harmonia.MainActivity

import com.mariana.harmonia.R
import com.mariana.harmonia.activitys.fragments.FragmentoDificultadDesafio
import com.mariana.harmonia.interfaces.PlantillaActivity
import com.mariana.harmonia.models.db.FirebaseDB
import com.mariana.harmonia.utils.ServicioTiempo
import com.mariana.harmonia.utils.Utils
import com.mariana.harmonia.utils.UtilsDB
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class EligeModoJuegoActivity : AppCompatActivity(), PlantillaActivity {

    private   var RC_NOTIFICATION = 99

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var nombreTextView: TextView
    private lateinit var porcentajeTextView: TextView
    private lateinit var imageViewFotoPerfil: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var botonAventura: androidx.appcompat.widget.AppCompatButton
    private lateinit var botonDesafio: androidx.appcompat.widget.AppCompatButton
    private lateinit var botonOpciones: androidx.appcompat.widget.AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.elige_modo_juego_activity) // Inflar el layout primero


        Utils.isExternalStorageWritable()
        Utils.isExternalStorageReadable()

        val imageView: ImageView = findViewById(R.id.fondoImageView)
        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.animacion_principal)
        imageView.startAnimation(anim)

        nombreTextView = findViewById(R.id.nombreModoDeJuego)
        porcentajeTextView = findViewById(R.id.porcentajeTextView)
        progressBar = findViewById(R.id.progressBarCarga)
        imageViewFotoPerfil = findViewById(R.id.imageViewFotoPerfil) // Inicializar imageViewFotoPerfil
        botonAventura = findViewById(R.id.botonAventura)
        botonDesafio = findViewById(R.id.botonChallenge)
        botonOpciones = findViewById(R.id.botonOpciones)


        inicilalizarVariablesThis()
        inicializarConBase()

        lifecycleScope.launch {
            downloadImage2()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), RC_NOTIFICATION)
        }
        val serviceIntent = Intent(this, ServicioTiempo::class.java)
        startService(serviceIntent)
        println("SERVICIO CONTADOR COMENZO")
    }
    fun inicializarConBase() = runBlocking {
        var nivel = UtilsDB.getExperiencia()!!/100
        var experienciaSobrante = UtilsDB.getExperiencia()!!%100

        nombreTextView.text = UtilsDB.getNombre()
        porcentajeTextView.text = "NV. "+nivel.toString()
        progressBar.progress = experienciaSobrante
    }

    private fun inicilalizarVariablesThis() {
        Utils.degradadoTexto(this, R.id.cerrarSesion,R.color.rosa,R.color.morado)
        Utils.degradadoTexto(this, R.id.titleTextView,R.color.rosa,R.color.morado)
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_cuatro)
        //Utils.serializeImage(this,R.mipmap.img_gema)
        //imageViewFotoPerfil.setImageBitmap(Utils.deserializeImage(this,"/storage/emulated/0/Download/imagenSerializada.json"))
    }


    private fun downloadImage2() {
        val storageRef = FirebaseDB.getInstanceStorage().reference
        val userId = FirebaseDB.getInstanceFirebase().currentUser?.uid
        val imagesRef = storageRef.child("imagenesPerfilGente").child("$userId.jpg")

        imagesRef.downloadUrl.addOnSuccessListener { url ->
            Glide.with(this)
                .load(url)
                .into(imageViewFotoPerfil)

            // Call the function to change the name and upload the image
            /*runBlocking {
                changeAndUploadImage(url)
            }*/
        }.addOnFailureListener { exception ->
            println("Error al cargar la imagen: ${exception.message}")
            imageViewFotoPerfil.setImageResource(R.mipmap.fotoperfil_guitarra)
        }
    }

    fun menu_perfil(view: View){
        mediaPlayer.start()
        val intent = Intent(this, PerfilUsuarioActivity::class.java)
        startActivity(intent)
    }

    fun cerrarSesion(view: View) {
        mediaPlayer.start()
        val serviceIntent = Intent(this, ServicioTiempo::class.java)
        stopService(serviceIntent)
        println("SERVICIO CONTADOR CERRADO")
        FirebaseDB.getInstanceFirebase().signOut()
        UtilsDB.currentUser?.reload()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


        finish()
        finishAffinity() // Cierra todas las actividades anteriores

    }

    fun clickOpciones(view: View){
        mediaPlayer.start()

        val intent = Intent(this, ConfiguracionActivity::class.java)
        startActivity(intent)

    }

    fun irModoAventura(view: View){
        mediaPlayer.start()
        val intent = Intent(this, NivelesAventuraActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun irDesafio(view: View) {
        mediaPlayer.start()

        // Crea una instancia del fragmento que deseas mostrar
        val fragment = FragmentoDificultadDesafio()

        // Obtén el administrador de fragmentos y comienza una transacción
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Reemplaza el contenido del contenedor de fragmentos con el fragmento que deseas mostrar
        fragmentTransaction.replace(R.id.fragment_container, fragment)

        // Agrega la transacción al historial de retroceso (opcional)
        fragmentTransaction.addToBackStack(null)

        // Realiza la transacción
        fragmentTransaction.commit()

        // Oculta los botones de la actividad principal
        botonAventura.visibility = View.GONE
        botonDesafio.visibility = View.GONE
        botonOpciones.visibility = View.GONE
        // Puedes hacer lo mismo para otros botones de la actividad si es necesario
    }

    override fun onBackPressed() {
        botonAventura.visibility = View.VISIBLE
        botonDesafio.visibility = View.VISIBLE
        botonOpciones.visibility = View.VISIBLE
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RC_NOTIFICATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "ALLOWED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

}