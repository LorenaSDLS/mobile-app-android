package tec.lass.zazil_app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tec.lass.zazil_app.viewmodel.PasswordRecoveryViewModel
/**
 * Pantalla de recuperación de contraseña.
 * @param navController Controlador de navegación.
 * @param viewModel Modelo de vista para la recuperación de contraseña.
 */
@Composable
fun PasswordRecoveryScreen(
    navController: NavController, // Para la navegación si quieres regresar al login después
    viewModel: PasswordRecoveryViewModel // Puedes usar ViewModel si ya tienes uno
) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recuperar contraseña",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isNotEmpty()) {
                    viewModel.sendPasswordResetEmail(
                        email,
                        onSuccess = {
                            successMessage = "Correo de recuperación enviado"
                            errorMessage = ""
                        },
                        onFailure = {
                            errorMessage = "Error al enviar correo: ${it.message}"
                        }
                    )
                } else {
                    errorMessage = "Por favor, ingrese su correo electrónico"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recuperar contraseña")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        if (successMessage.isNotEmpty()) {
            Text(text = successMessage, color = Color.Green)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("¿Ya recuerdas tu contraseña? Inicia sesión")
        }
    }
}
