package com.mariana.harmonia.models.entity

import java.time.Month
import java.time.ZoneId

class User(
    var email: String? = null,
    var name: String? = null,
    var correo: String? = null,
    var experiencia: Int? = 0,
    var nivelActual: Int? = 1,
    var vidas: Int? = 5,
    val puntuacionDesafio: List<Map<String, Number>> = listOf(
        mapOf("dificultad" to 0,"notas" to 0, "tiempo" to 0),
    ),
    var imagen: String? = null,
    var mesRegistro: Month,
    var anioRegistro: Int
)