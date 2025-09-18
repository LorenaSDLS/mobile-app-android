package tec.lass.zazil_app.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import tec.lass.zazil_app.model.User

     /**
     * Función para cargar los datos del usuario que inició sesión.
     */

     class UserProfileViewModel : ViewModel(){
         private val _userData = MutableLiveData<User?>()
         private val database = FirebaseDatabase.getInstance().getReference("users")

         val userData: LiveData<User?> get() = _userData

         fun loadUserData(phone: String) {
             database.child(phone).get().addOnSuccessListener { dataSnapshot ->
                 if (dataSnapshot.exists()) {
                     val user = dataSnapshot.getValue(User::class.java)
                     _userData.postValue(user)
                     Log.d("UserProfileViewModel", "Datos cargados correctamente para $phone")
                 } else {
                     _userData.postValue(null)
                     Log.e("UserProfileViewModel", "No se encontraron datos para el teléfono: $phone")
                 }
             }.addOnFailureListener {
                 _userData.postValue(null)
                 Log.e("UserProfileViewModel", "Error al cargar datos: ${it.message}")
             }
         }
     }
