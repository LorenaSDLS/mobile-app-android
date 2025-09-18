
package tec.lass.zazil_app.view
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.sphr.zazil.viewmodel.LoginState
import mx.sphr.zazil.viewmodel.LoginViewModel
import tec.lass.zazil_app.R
import tec.lass.zazil_app.viewmodel.SessionViewModel
/**
 * Composable que representa la pantalla de inicio de sesión.
 * Permite a los usuarios introducir sus credenciales para acceder a la aplicación.
 */
@Composable
fun LoginScreen(viewModel: LoginViewModel, sessionViewModel: SessionViewModel, navController: NavController) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Observar el estado del login desde StateFlow
    val loginState by viewModel.loginState.collectAsState()

    val isButtonEnabled = phone.isNotEmpty() && password.isNotEmpty()
    val backgroundColor = Color(0xFFFFC0CB)

    // Manejar el estado de la sesión de inicio de sesión
    when (loginState) {
        is LoginState.Error -> {
            errorMessage = (loginState as LoginState.Error).message
        }
        is LoginState.Success -> {
            sessionViewModel.setPhoneNumber(phone)
            Log.d("LoginScreen", "Navegando a inicio con el teléfono: $phone")
            //navController.navigate("tienda")
            navController.navigate("inicio")

        }
        is LoginState.Loading -> {
            errorMessage = ""
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFC2185B),
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Text(
            text = "¡Hola, qué gusto tenerte de vuelta!",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo para el número de teléfono
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Número de teléfono") },
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = "Icono de teléfono")
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            isError = phone.isEmpty() && errorMessage.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Icono de contraseña")
            },
            isError = password.isEmpty() && errorMessage.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Enlace para recuperar contraseña
        TextButton(onClick = { navController.navigate("password_recovery") }) {
            Text("¿Olvidaste tu contraseña?", color = Color(0xFF512DA8))
        }

        // Mensaje de error
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Botón para iniciar sesión
        Button(
            onClick = {
                if (phone.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.login(phone, password, sessionViewModel)  // Llamar a la función de inicio de sesión
                } else {
                    errorMessage = "Por favor completa todos los campos"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = isButtonEnabled
        ) {
            Text("Ingresar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Imagen decorativa
        Image(
            painter = painterResource(id = R.drawable.todas),
            contentDescription = "Ilustración",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Enlace para registrarse
        TextButton(onClick = { navController.navigate("signup") }) {
            Text("¿Aún no tienes cuenta? Crea tu cuenta aquí", color = Color(0xFF477cc4))
        }
    }
}
