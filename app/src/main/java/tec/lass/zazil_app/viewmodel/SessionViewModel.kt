package tec.lass.zazil_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/**
 * Clase SessionViewModel
 * @property _phoneNumber LiveData que contiene el número de teléfono de la sesión.
 * @property _userName LiveData que contiene el nombre de usuario de la sesión.
 * @property phoneNumber LiveData que proporciona el número de teléfono de la sesión.
 * @property userName LiveData que proporciona el nombre de usuario de la sesión.
 */
class SessionViewModel : ViewModel() {
    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun setUserName(name: String) {
        _userName.value = name
    }
}