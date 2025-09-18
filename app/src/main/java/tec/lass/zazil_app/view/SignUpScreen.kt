
package tec.lass.zazil_app.view

import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import mx.sphr.zazil.viewmodel.SignUpState
import mx.sphr.zazil.viewmodel.SignUpViewModel
import tec.lass.zazil_app.R
/**
 * Pantalla de registro de usuario.
 * @param viewModel Modelo de vista para el registro.
 * @param navController Controlador de navegación para navegar entre pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navController: NavController) {

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidCurp(curp: String): Boolean {
        val curpPattern = "^[A-Z]{4}\\d{6}[HM][A-Z]{2}[A-Z]{3}[A-Z\\d]{1}\\d{1}$"
        return curp.matches(Regex(curpPattern))
    }

    val signUpState by viewModel.signUpState.collectAsState()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var curp by remember { mutableStateOf("") }
    var direction by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showCurpHelp by remember { mutableStateOf(false) }

    var acceptPrivacyPolicy by remember { mutableStateOf(false) } // Estado del checkbox
    var registrationSuccess by remember { mutableStateOf(false) } // Estado de registro exitoso

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            birthdate = String.format("%02d/%02d/%02d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
        }, year, month, day
    )

    val estadosMexico = listOf(
        "Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas",
        "Chihuahua", "Ciudad de México", "Coahuila", "Colima", "Durango", "Estado de México",
        "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacán", "Morelos", "Nayarit",
        "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí",
        "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"
    )

    // Manejo de los estados de registro
    when (signUpState) {
        is SignUpState.Error -> {
            errorMessage = (signUpState as SignUpState.Error).message
        }
        is SignUpState.Success -> {
            LaunchedEffect(Unit) {
                registrationSuccess = true
                delay(5000)
                navController.navigate("login")
            }
        }
        is SignUpState.Loading -> {
            // Mostrar un indicador de carga si está en progreso
            errorMessage = ""
        }
        else -> { /* Nada que hacer aquí */ }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Título
            Text(
                text = "Crea tu cuenta",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC2185B),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            // Campo Nombre
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Icono de nombre")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // Campo Teléfono
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Número de teléfono") },
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = "Icono de teléfono")
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Icono de contraseña")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // Campo Correo Electrónico
            OutlinedTextField(
                value = email,
                onValueChange = { email = it.trim().lowercase() },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Icono de correo")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // Campo Fecha de nacimiento con un DatePicker
            OutlinedTextField(
                value = birthdate,
                onValueChange = { birthdate = it },
                label = { Text("Fecha de nacimiento") },
                leadingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Icono de calendario")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        datePickerDialog.show()
                    },
                enabled = false
            )
        }

        item {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = location,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Localidad") },
                    leadingIcon = {
                        Icon(Icons.Default.Place, contentDescription = "Icono de localidad")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    estadosMexico.forEach { estado ->
                        DropdownMenuItem(
                            text = { Text(text = estado) },
                            onClick = {
                                location = estado
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = direction,
                onValueChange = { direction = it },
                label = { Text("Dirección de envío completa") },
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "Icono de direccion")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            // Campo CURP con icono y toque para mostrar ayuda
            OutlinedTextField(
                value = curp,
                onValueChange = { curp = it },
                label = { Text("CURP") },
                leadingIcon = {
                    IconButton(onClick = { showCurpHelp = !showCurpHelp }) {
                        Icon(Icons.Default.Info, contentDescription = "Icono de CURP")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Mostrar la ayuda del CURP si el icono fue tocado
        if (showCurpHelp) {
            item {
                Text(
                    text = "¿Por qué pedimos la CURP?\nSolicitamos tu CURP porque, como una entidad benefactora, debemos comprobar ante el SAT que nuestras ventas son auténticas y están correctamente registradas.",
                    color = Color(0xFFC2185B),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        item {
            // Casilla de verificación para aceptar la política de privacidad
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = acceptPrivacyPolicy,
                    onCheckedChange = { acceptPrivacyPolicy = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Acepto el aviso de privacidad",
                    modifier = Modifier.clickable { acceptPrivacyPolicy = !acceptPrivacyPolicy },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        item {
            // Botón de registro desactivado si no acepta la política de privacidad
            Button(
                onClick = {
                    if (name.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() &&
                        birthdate.isNotEmpty() && location.isNotEmpty() && curp.isNotEmpty()) {
                        if (!isValidEmail(email.trim())) {
                            errorMessage = "Por favor ingresa un correo electrónico válido."
                        } else if (!isValidCurp(curp.trim())) {
                            errorMessage = "El CURP ingresado no es válido."
                        } else {
                            viewModel.registerUser(
                                email.trim(), password, phone, name, birthdate, curp, location, direction
                            )
                        }
                    } else {
                        errorMessage = "Por favor completa todos los campos"
                    }
                },
                enabled = acceptPrivacyPolicy,  // Habilitar o deshabilitar según la casilla
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear cuenta")
            }
        }

        // Mostrar el mensaje de error, si existe
        if (errorMessage.isNotEmpty()) {
            item {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        // Mostrar el mensaje de éxito de registro
        if (registrationSuccess) {
            item {
                Text(
                    text = "Registro exitoso. Bienvenido a ZAZIL.",
                    color = Color(0xFF388E3C),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        item {
            // Botón para ir a iniciar sesión si ya tiene cuenta
            TextButton(onClick = { navController.navigate("login") }) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }

        item {
            // Imagen decorativa al final
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.todas),
                contentDescription = "Ilustración",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
            )
        }
    }
}
