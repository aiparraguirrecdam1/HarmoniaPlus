package com.mariana.harmonia.activitys.pantallasExtras

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.mariana.harmonia.activitys.JuegoMusicalActivity
import com.mariana.harmonia.activitys.NivelesAventuraActivity
import com.mariana.harmonia.R
import com.mariana.harmonia.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class derrotaDesafio_activity : AppCompatActivity() {
    private var nivel: Int = 0
    private var notasHacertadas: Int = 0
    private var tiempoDurado: Int = 0
    private lateinit var textViewResultados: TextView
    private lateinit var mediaPlayer: MediaPlayer
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        nivel = intent.getIntExtra("numeroNivel", 999)
        notasHacertadas = intent.getIntExtra("notasHacertadas", 0)
        tiempoDurado = intent.getIntExtra("tiempoDurado", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_derrota_desafio)
        textViewResultados = findViewById(R.id.textViewResultados)
        var tiempo = ((tiempoDurado-60)*-1)
        textViewResultados.text = "Total=$notasHacertadas\nTiempo=$tiempo s"
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_cuatro)
        actualizarDatosInterfaz()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun actualizarDatosInterfaz() = runBlocking {
        val puntuacion = Pair(notasHacertadas, (60 - tiempoDurado))
        val puntuacionDB = Utils.getPuntuacionDesafio()
        val mayorHaciertos = puntuacionDB?.first ?:0
        Utils.setPuntuacionDesafio(puntuacion)
        Utils.setPuntuacionDesafioGlobal(puntuacion)
    }

    fun irRepetir(view: View) {
        mediaPlayer.start()
        val intent = Intent(this, JuegoMusicalActivity::class.java)
        intent.putExtra("desafio", true)
        finish()
        startActivity(intent)
    }
    fun irMenu(view: View) {
        mediaPlayer.start()
        val intent = Intent(this, NivelesAventuraActivity::class.java)
        finish()
        startActivity(intent)
    }
}