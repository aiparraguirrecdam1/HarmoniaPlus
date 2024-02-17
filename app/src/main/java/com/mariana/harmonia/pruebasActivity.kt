package com.mariana.harmonia

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class pruebasActivity : AppCompatActivity() {
    private lateinit var imagenNota: ImageView
    private lateinit var textViewNota: TextView
    private var intentos: Int? = 0
    private var aciertos: Int? = 0
    private lateinit var notasArray: Array<String?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebas)
        // Las notas del nivel
        notasArray = arrayOf("5a", "5f", "4a", "5b", "4d", "5e", "4f", "4c", "5g", "4b", "5d", "4e", "5b", "4g", "5d", "4b", "5c", "5e", "4d", "4e", "5f", "4g", "5g", "5e", "4f", "4b", "5a", "4c", "5g", "4a", "4d", "5c", "4e", "5f", "5b", "4c", "5d", "4a", "5c", "5d", "4f", "5a", "4g", "5f", "4e", "5a", "4b", "5g", "4d", "5c", "4b", "5e", "4f", "5a", "4d", "5g", "4e", "5b", "4f", "5e", "4c", "5f", "4a", "5c", "4g", "5b", "4e", "5d", "4c", "5f", "4d", "5g", "4a", "5e", "4b", "5g", "4c", "5d", "4f", "5a", "4e", "5b", "4d", "5c", "4f", "5e", "4g")



        // Click notas negras
        val notaRe_b = findViewById<ImageView>(R.id.notaRe_b)
        val notaMi_b = findViewById<ImageView>(R.id.notaMi_b)
        val notaSol_b = findViewById<ImageView>(R.id.notaSol_b)
        val notaLa_b = findViewById<ImageView>(R.id.notaLa_b)
        val notaSi_b = findViewById<ImageView>(R.id.notaSi_b)

        // Click notas blancas
        val notaDo = findViewById<ImageView>(R.id.notaDo)
        val notaRe = findViewById<ImageView>(R.id.notaRe)
        val notaMi = findViewById<ImageView>(R.id.notaMi)
        val notaFa = findViewById<ImageView>(R.id.notaFa)
        val notaSol = findViewById<ImageView>(R.id.notaSol)
        val notaLa = findViewById<ImageView>(R.id.notaLa)
        val notaSi = findViewById<ImageView>(R.id.notaSi)

        imagenNota = findViewById(R.id.imagenNota)
        textViewNota = findViewById(R.id.layoutTexto)


        //Condiciones iniciales
        cambiarImagen(notasArray[0].toString())
        cambiarTexto("...")



        //Activamos los listeners

        notaRe_b.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickRe_b()
                }

                MotionEvent.ACTION_UP -> {
                    soltarRe_b()
                }
            }
            true
        }

        notaMi_b.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickMi_b()
                }

                MotionEvent.ACTION_UP -> {
                    soltarMi_b()
                }
            }
            true
        }

        notaSol_b.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickSol_b()
                }

                MotionEvent.ACTION_UP -> {
                    soltarSol_b()
                }
            }
            true
        }

        notaLa_b.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickLa_b()
                }

                MotionEvent.ACTION_UP -> {
                    soltarLa_b()
                }
            }
            true
        }

        notaSi_b.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickSi_b()
                }

                MotionEvent.ACTION_UP -> {
                    soltarSi_b()
                }
            }
            true
        }



        notaDo.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickDo()
                }

                MotionEvent.ACTION_UP -> {
                    soltarDo()
                }
            }
            true
        }

        notaRe.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickRe()
                }

                MotionEvent.ACTION_UP -> {
                    soltarRe()
                }
            }
            true
        }

        notaMi.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickMi()
                }

                MotionEvent.ACTION_UP -> {
                    soltarMi()
                }
            }
            true
        }

        notaFa.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickFa()
                }

                MotionEvent.ACTION_UP -> {
                    soltarFa()
                }
            }
            true
        }

        notaSol.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickSol()
                }

                MotionEvent.ACTION_UP -> {
                    soltarSol()
                }
            }
            true
        }

        notaLa.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickLa()
                }

                MotionEvent.ACTION_UP -> {
                    soltarLa()
                }
            }
            true
        }

        notaSi.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    clickSi()
                }

                MotionEvent.ACTION_UP -> {
                    soltarSi()
                }
            }
            true
        }
    }


    private fun comprobarJugada(nombreNota: String) {
        //sumamos una a intentos
        intentos = intentos?.plus(1)

        //comprobamos si el array se ha terminado

        if (aciertos != null && aciertos!! < notasArray.size) {
            //Si la nota es la indicada entra
            if ( notasArray[(aciertos!!)]!!.substring(1) == nombreNota) {

                aciertos = aciertos?.plus(1)
                cambiarImagen(notasArray[aciertos!!].toString())
                cambiarTexto(nombreNota)
                animacionAcierto()
            } else {
                animacionFallo()
            }
        } else {
            // El índice está fuera del rango del array notasArray se termina la partida
            terminarPartida()
        }
    }

    private fun terminarPartida() {
        Log.d("pruebasActivity", "Se termino la partida ya que se termino el array")
    }


    private fun cambiarTexto(texto: String) {
        textViewNota.text = texto
    }

    private fun cambiarImagen(nombreArchivo: String) {
        val idImagen = resources.getIdentifier("nota_"+ nombreArchivo, "drawable", packageName)
        if (idImagen != 0) {
            imagenNota.setImageResource(idImagen)
        } else {
            Toast.makeText(this, "Imagen no encontrada", Toast.LENGTH_SHORT).show()
        }
    }

    fun animacionAcierto() {
        // Cambiar color a verde instantáneamente
        playSound("correcto")
        textViewNota.setTextColor(Color.GREEN)

        // Animación para volver al color original con desvanecido
        val animacion = ObjectAnimator.ofArgb(textViewNota, "textColor", Color.GREEN, Color.BLACK)
        animacion.duration = 1000
        animacion.start()

        // Usar un Handler para llamar a cambiarTexto después de 1 segundo
        Handler().postDelayed({
            cambiarTexto("...")
        }, 1000) // 1000 milisegundos = 1 segundo
    }

    fun animacionFallo() {
        // Cambiar color a verde instantáneamentes
        playSound("incorrecto")
        textViewNota.setTextColor(Color.RED)

        // Animación para volver al color original con desvanecido
        val animacion = ObjectAnimator.ofArgb(textViewNota, "textColor", Color.RED, Color.BLACK)
        animacion.duration = 1000
        animacion.start()

        // Usar un Handler para llamar a cambiarTexto después de 1 segundo
        Handler().postDelayed({
            cambiarTexto("...")
        }, 1000) // 1000 milisegundos = 1 segundo
    }


    private fun playSound(soundFile: String) {
        val mediaPlayer =
            MediaPlayer.create(this, resources.getIdentifier(soundFile, "raw", packageName))
        val volume = 1f
        mediaPlayer.setVolume(volume, volume)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
    }

    private fun actualizarFondoNegras(viewId: Int, drawableId: Int) {
        val view = findViewById<View>(viewId)
        val drawable: Drawable? = ContextCompat.getDrawable(this, drawableId)
        drawable?.let {
            view.background = it
        }
    }


    fun actualizarFondoBlancas(imageViewId: Int, nuevaImagenId: Int, actividad: AppCompatActivity) {
        // Buscar el ImageView por su ID
        val imageView = actividad.findViewById<ImageView>(imageViewId)
        // Establecer la nueva imagen
        imageView.setImageResource(nuevaImagenId)
    }


    private fun clickDo() {
        Log.d("pruebasActivity", "Se ha hecho clic en la nota Do")

        playSound("c4")
        actualizarFondoBlancas(R.id.notaDo, R.drawable.svg_tecla_do_clicada, this)
        cambiarImagen("nota_6d")
        comprobarJugada("c")
    }

    fun clickRe_b() {
        Log.d("pruebasActivity", "Se ha hecho clic en el método Reb")

        playSound("db4")
        actualizarFondoNegras(R.id.fondoReB, R.drawable.style_buttond_egradado_suave)
        comprobarJugada("db")
    }

    private fun clickRe() {
        Log.d("pruebasActivity", "Se ha hecho clic en la nota Re")

        playSound("d4")
        actualizarFondoBlancas(R.id.notaRe, R.drawable.svg_tecla_re_clicada, this)
        comprobarJugada("d")
    }


    fun clickMi_b() {
        Log.d("pruebasActivity", "Se ha hecho clic en el método Mib")

        playSound("eb4")
        actualizarFondoNegras(R.id.fondoMiB, R.drawable.style_buttond_egradado_suave)
        comprobarJugada("mb")
    }


    private fun clickMi() {
        Log.d("pruebasActivity", "Se ha hecho clic en la nota Mi")

        playSound("e4")
        actualizarFondoBlancas(R.id.notaMi, R.drawable.svg_tecla_mi_clicada, this)
        comprobarJugada("e")
    }

    private fun clickFa() {
        Log.d("pruebasActivity", "Se ha hecho clic en la nota Fa")

        playSound("f4")
        actualizarFondoBlancas(R.id.notaFa, R.drawable.svg_tecla_fa_clicada, this)
        comprobarJugada("f")
    }

    fun clickSol_b() {
        Log.d("pruebasActivity", "Se ha hecho clic en el método Solb")

        playSound("gb4")
        actualizarFondoNegras(R.id.fondoSolB, R.drawable.style_buttond_egradado_suave)
        comprobarJugada("gb")
    }

    private fun clickSol() {
        Log.d("pruebasActivity", "Se ha hecho clic en la nota Sol")

        playSound("g4")
        actualizarFondoBlancas(R.id.notaSol, R.drawable.svg_tecla_sol_clicada, this)
        comprobarJugada("g")
    }


    fun clickLa_b() {
        Log.d("pruebasActivity", "Se ha hecho clic en el método Lab")

        playSound("ab4")
        actualizarFondoNegras(R.id.fondoLaB, R.drawable.style_buttond_egradado_suave)
        comprobarJugada("ab")

    }

    private fun clickLa() {
        Log.d("pruebasActivity", "Se ha hecho clic en la nota La")

        playSound("a4")
        actualizarFondoBlancas(R.id.notaLa, R.drawable.svg_tecla_la_clicada, this)
        comprobarJugada("a")

    }

    fun clickSi_b() {
        Log.d("pruebasActivity", "Se ha hecho clic en el método Sib")

        playSound("bb4")
        actualizarFondoNegras(R.id.fondoSiB, R.drawable.style_buttond_egradado_suave)
        comprobarJugada("bb")
    }

    private fun clickSi() {
        Log.d("pruebasActivity", "Se ha hecho clic en la nota Si")

        playSound("b4")
        actualizarFondoBlancas(R.id.notaSi, R.drawable.svg_tecla_si_clicada, this)
        comprobarJugada("b")
    }


    //Soltar

    private fun soltarDo() {
        Log.d("pruebasActivity", "Se ha soltado la nota Do")
        actualizarFondoBlancas(R.id.notaDo, R.drawable.svg_tecla_do, this)
    }

    private fun soltarRe_b() {
        Log.d("pruebasActivity", "Se ha soltado la nota reB")
        actualizarFondoNegras(R.id.fondoReB, R.drawable.sytle_degradado_fondo_piano)
    }

    private fun soltarRe() {
        Log.d("pruebasActivity", "Se ha soltado la nota Re")
        actualizarFondoBlancas(R.id.notaRe, R.drawable.svg_tecla_re, this)
    }

    private fun soltarMi_b() {
        Log.d("pruebasActivity", "Se ha soltado la nota Mib")
        actualizarFondoNegras(R.id.fondoMiB, R.drawable.sytle_degradado_fondo_piano)
    }

    private fun soltarMi() {
        Log.d("pruebasActivity", "Se ha soltado la nota Mi")
        actualizarFondoBlancas(R.id.notaMi, R.drawable.svg_tecla_mi, this)
    }

    private fun soltarFa() {
        Log.d("pruebasActivity", "Se ha soltado la nota Fa")
        actualizarFondoBlancas(R.id.notaFa, R.drawable.svg_tecla_fa, this)
    }

    private fun soltarSol_b() {
        Log.d("pruebasActivity", "Se ha soltado la nota Solb")
        actualizarFondoNegras(R.id.fondoSolB, R.drawable.sytle_degradado_fondo_piano)
    }

    private fun soltarSol() {
        Log.d("pruebasActivity", "Se ha soltado la nota Sol")
        actualizarFondoBlancas(R.id.notaSol, R.drawable.svg_tecla_sol, this)
    }

    private fun soltarLa_b() {
        Log.d("pruebasActivity", "Se ha soltado la nota Lab")
        actualizarFondoNegras(R.id.fondoLaB, R.drawable.sytle_degradado_fondo_piano)
    }

    private fun soltarLa() {
        Log.d("pruebasActivity", "Se ha soltado la nota La")
        actualizarFondoBlancas(R.id.notaLa, R.drawable.svg_tecla_la, this)
    }

    private fun soltarSi_b() {
        Log.d("pruebasActivity", "Se ha soltado la nota Sib")
        actualizarFondoNegras(R.id.fondoSiB, R.drawable.sytle_degradado_fondo_piano)
    }

    private fun soltarSi() {
        Log.d("pruebasActivity", "Se ha soltado la nota Si")
        actualizarFondoBlancas(R.id.notaSi, R.drawable.svg_tecla_si, this)
    }

}