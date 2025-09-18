package tec.lass.zazil_app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import tec.iama.myapplication.ciclo.viewmodel.CalculadoraMenstrualViewModel
import tec.iama.myapplication.ciclo.view.CicloMenstrualConIconos
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
/**
 * Composable que representa la pantalla del calendario.
 * Permite visualizar los días del ciclo menstrual y eventos importantes.
 * @param viewModel ViewModel asociado al calendario.
 * @param fechaUltimaMenstruacionInput Campo de entrada para la fecha de última menstruación.
 * @param duracionCicloInput Campo de entrada para la duración del ciclo menstrual.
 * @param resultado Texto que muestra el resultado del cálculo.
 * @param diasFertiles Texto que muestra los días fértiles.
 * @param showChart Indica si se debe mostrar el gráfico del ciclo menstrual.
 * @param proximaMenstruacion Fecha de la próxima menstruación calculada.
 * @param diasFertilesInicio Fecha de inicio de los días fértiles calculados.
 * @param diasFertilesFin Fecha de fin de los días fértiles calculados.
 * @param formatter Formato de fecha utilizado para el procesamiento de fechas.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaCalendario() {
    val viewModel: CalculadoraMenstrualViewModel = viewModel()

    var fechaUltimaMenstruacionInput by remember { mutableStateOf("") }
    var duracionCicloInput by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var diasFertiles by remember { mutableStateOf("") }
    var showChart by remember { mutableStateOf(false) }

    var proximaMenstruacion by remember { mutableStateOf<LocalDate?>(null) }
    var diasFertilesInicio by remember { mutableStateOf<LocalDate?>(null) }
    var diasFertilesFin by remember { mutableStateOf<LocalDate?>(null) }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            fechaUltimaMenstruacionInput =
                String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
        }, year, month, day
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = fechaUltimaMenstruacionInput,
            onValueChange = { fechaUltimaMenstruacionInput = it },
            label = { Text("Fecha de última menstruación (YYYY-MM-DD)") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    datePickerDialog.show()
                },
            enabled = false
        )

        OutlinedTextField(
            value = duracionCicloInput,
            onValueChange = { duracionCicloInput = it },
            label = { Text("Duración del ciclo (en días)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                try {
                    val fechaUltimaMenstruacion = LocalDate.parse(fechaUltimaMenstruacionInput, formatter)
                    val duracionCiclo = duracionCicloInput.toIntOrNull() ?: 28

                    proximaMenstruacion = viewModel.calcularProximaMenstruacion(fechaUltimaMenstruacion, duracionCiclo)

                    val diasFertilesResult = viewModel.calcularDiasFertiles(proximaMenstruacion!!, duracionCiclo)

                    diasFertilesInicio = diasFertilesResult.first
                    diasFertilesFin = diasFertilesResult.second

                    resultado = "Próxima menstruación: ${proximaMenstruacion?.format(formatter)}"
                    diasFertiles = "Días fértiles: ${diasFertilesInicio?.format(formatter)} a ${diasFertilesFin?.format(formatter)}"

                    showChart = true

                } catch (e: Exception) {
                    resultado = "Por favor, introduce una fecha válida."
                    diasFertiles = ""
                    showChart = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFDD5507C),)
        ) {
            Text(text = "Calcular",
                color = Color.White
            )
        }

        Text(text = resultado, style = MaterialTheme.typography.bodyLarge)
        Text(text = diasFertiles, style = MaterialTheme.typography.bodyLarge)

        if (showChart) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = " Gráfico del ciclo ",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                CicloMenstrualConIconos(
                    proximaMenstruacion = proximaMenstruacion,
                    diasFertilesInicio = diasFertilesInicio,
                    diasFertilesFin = diasFertilesFin,
                    fechaUltimaMenstruacion = LocalDate.parse(fechaUltimaMenstruacionInput, formatter),
                    duracionCiclo = duracionCicloInput.toIntOrNull() ?: 28
                )
            }
        }
    }
}