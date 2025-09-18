package mx.sphr.zazil.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tec.lass.zazil_app.model.UserRepository

/**
 * Clase SignUpViewModel
 *
 * Este ViewModel maneja la lógica del registro de nuevos usuarios.
 * Se comunica con el UserRepository para enviar los datos del usuario y
 * gestionar el flujo de estado de la operación.
 */

sealed class SignUpState {
    object Idle : SignUpState()
    object Success : SignUpState()
    object Loading : SignUpState()
    data class Error(val message: String) : SignUpState()
}

/**
 * ViewModel para manejar el proceso de registro de usuarios.
 * Utiliza coroutines para ejecutar el registro en segundo plano.
 */

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun registerUser(
        email: String,
        password: String,
        phone: String,
        name: String,
        birthdate: String,
        curp: String,
        location: String,
        direction: String

    ) {
        _signUpState.value = SignUpState.Loading

        userRepository.signUpUser(
            name = name,
            phone = phone,
            password = password,
            email = email,
            birthdate = birthdate,
            location = location,
            curp = curp,
            direction = direction,
            onSuccess = {
                _signUpState.value = SignUpState.Success
            },
            onFailure = { errorMessage ->
                _signUpState.value = SignUpState.Error(errorMessage)
            }
        )
    }
}
