package tec.lass.zazil_app.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tec.lass.zazil_app.viewmodel.UserProfileViewModel
/**
 * Pantalla de perfil del usuario.
 * @param viewModel Modelo de vista del perfil del usuario.
 * @param phone Número de teléfono del usuario.
 */
@Composable
fun PantallaPerfil(viewModel: UserProfileViewModel, phone: String) {
    // Cargar los datos del usuario
    LaunchedEffect(Unit) {
        viewModel.loadUserData(phone)
    }

    val userData by viewModel.userData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        userData?.let { user ->
            // Encabezado con el nombre y la imagen de perfil
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(Color(0xFFFDD5507C))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen de perfil
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.size(80.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Nombre del usuario
                    Text(
                        text = user.name ?: "Nombre no disponible",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card para los detalles del usuario (Correo, teléfono, dirección, fecha de nacimiento)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(Color(0xFFFFC0CB))

            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Correo electrónico
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = "Correo")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = user.email ?: "Correo no disponible")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Teléfono
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = user.phone ?: "Teléfono no disponible")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dirección
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Place, contentDescription = "Ubicación")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = user.location ?: "Ubicación no disponible")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fecha de nacimiento
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DateRange, contentDescription = "Fecha de nacimiento")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = user.birthdate ?: "Fecha de nacimiento no disponible")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    //direccion de envio
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Place, contentDescription = "Dirección")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = user.direction ?: "Dirección no disponible")
                    }
                }
            }
        } ?: run {
            // Mensaje si no se encuentran datos del usuario
            Text(text = "No se encontraron datos del usuario", color = Color.Red)
        }
    }
}
