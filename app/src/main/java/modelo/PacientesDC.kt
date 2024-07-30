package modelo

import java.util.Date

data class PacientesDC(
    val UUID: String,
    var nombre: String,
    var apellidos: String,
    val edad: Number,
    val enfermedad: String,
    val fecha_nacimiento: Date,
    val medicina: String
)
