package tec.lass.zazil_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tec.lass.zazil_app.model.Repository

class MainViewModel : ViewModel() {
    private val repository = Repository()

    var userName: String = "Cargando..."
        private set

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            userName = repository.getUserName()
        }
    }
}
