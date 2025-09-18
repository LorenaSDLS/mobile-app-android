package mx.sphr.zazil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tec.lass.zazil_app.model.UserRepository
import tec.lass.zazil_app.viewmodel.SessionViewModel
/**
 * Clase LoginViewModel
 * @property userRepository Repositorio de usuarios.
 * @property _loginState Estado de la autenticación.
 * @property loginState Estado de la autenticación como StateFlow.
 * @property phoneNumber Número de teléfono del usuario.
 * @property login Función para iniciar sesión.
 * @property setUserName Función para establecer el nombre del usuario en el SessionViewModel.
 * @property sessionViewModel Modelo de vista de sesión.
 */
sealed class LoginState {
    object Success : LoginState()
    object Loading : LoginState()
    class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState

    var phoneNumber: String? = null
        private set

    fun login(phone: String, password: String, sessionViewModel: SessionViewModel) {
        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            userRepository.loginUser(phone, password,
                onSuccess = { user ->
                    phoneNumber = user.phone
                    val userName = user.name ?: "Usuario desconocido"  // Obtén el nombre del usuario
                    _loginState.value = LoginState.Success
                    sessionViewModel.setUserName(userName)  // Almacena el nombre en el SessionViewModel
                },
                onFailure = { error ->
                    _loginState.value = LoginState.Error(error)
                }
            )
        }
    }
}