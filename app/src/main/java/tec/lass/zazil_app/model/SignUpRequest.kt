package mx.sphr.zazil.model

data class SignUpRequest(
    val name: String,
    val phone: String,
    val password: String,
    val email: String,
    val birthdate: String,
    val location: String,
    val curp: String
)