package tec.lass.zazil_app.model
/**
 * Clase que representa un tema con un nombre, titulo, imagen, descripción, información completa, Zazil, Menst y Datom.
 */
data class Tema(
    val titulo: String,
    val imagen: Int,
    val descripcion: String,
    val infoCompleta: String,
    val Zazil: Boolean,
    val Menst: Boolean,
    val Dato: Boolean
)
