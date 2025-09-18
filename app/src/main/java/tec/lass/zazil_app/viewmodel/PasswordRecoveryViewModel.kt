package tec.lass.zazil_app.viewmodel

import com.google.firebase.auth.FirebaseAuth

/**
 * Clase PasswordRecoveryViewModel
 *
 * Este ViewModel maneja la lógica para la recuperación de contraseñas.
 * Se comunica con el UserRepository para enviar solicitudes de recuperación
 * y gestiona el estado del proceso.
 */

class PasswordRecoveryViewModel {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Función sendPasswordResetEmail
     *
     * Inicia el proceso de recuperación de contraseña de un usuario.
     * Actualiza el estado del proceso según el resultado de la operación.
     *
     * @param email Correo electrónico del usuario.
     */

    fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }
}
