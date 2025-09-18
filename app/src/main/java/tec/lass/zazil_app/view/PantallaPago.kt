package tec.lass.zazil_app.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.launch
import tec.lass.zazil_app.viewmodel.PagoViewModel
import tec.lass.zazil_app.viewmodel.PaymentState
import java.util.UUID


class PaymentActivity : ComponentActivity() {
    private val pagoViewModel: PagoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la configuración de Stripe con la clave pública
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QAcoXFxGNIrLyOAjFRRld2LNHZ9muBGEadk1bRib8WeCxX6oBYXxe29SpxBcgP7WMTlr4muR7uFuJCoOo0UiAtW00dKUN8eXJ"
        )

        // Inicializa el PaymentSheet en el ViewModel
        pagoViewModel.initializePaymentSheet(this) { paymentResult ->
            val orderId = "10" // Aquí deberías obtener el ID real de la orden
            handlePaymentResult(paymentResult, orderId)
        }
        // Obtén el total a pagar desde un intent o cualquier fuente que estés utilizando
        val totalPrecio = intent.getFloatExtra("totalPrecio", 0.0f)

        // Configura el contenido con Jetpack Compose
        setContent {
            PantallaPago(pagoViewModel = pagoViewModel, totalPrecio = totalPrecio, activity = this)
        }
    }

    // Maneja el resultado del pago
    private fun handlePaymentResult(paymentResult: PaymentSheetResult, orderId: String) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                // Pago exitoso, actualiza la orden
                pagoViewModel.handlePaymentSuccess(orderId)
                Log.d("PaymentActivity", "Pago completado con éxito")

                finish()
            }
            is PaymentSheetResult.Failed -> {
                // Pago fallido, actualiza la orden
                pagoViewModel.handlePaymentFailure(orderId)
                Log.e("PaymentActivity", "Error al procesar el pago", paymentResult.error)
            }
            is PaymentSheetResult.Canceled -> {
                Log.d("PaymentActivity", "Pago cancelado")
            }
        }
    }
}



@Composable
fun PantallaPago(pagoViewModel: PagoViewModel, totalPrecio: Float, activity: ComponentActivity) {
    val paymentState by pagoViewModel.paymentState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Muestra el monto a cobrar dinámicamente basado en el valor pasado
        Text("Monto a cobrar: \$${"%.2f".format(totalPrecio)}")
        Spacer(modifier = Modifier.height(16.dp))

        when (paymentState) {
            is PaymentState.Loading -> {
                Text("Preparando el pago...")
                // Mostrar botón para crear el PaymentIntent
                Button(
                    onClick = {
                        val monto = (totalPrecio * 100).toInt()
                        val orderId = UUID.randomUUID().toString()
                        pagoViewModel.crearPaymentIntent(monto, orderId)
                    }
                ) {
                    Text("Generar Intento de Pago")
                }
            }
            is PaymentState.ReadyToPay -> {
                val clientSecret = (paymentState as PaymentState.ReadyToPay).clientSecret
                val orderId = (paymentState as PaymentState.ReadyToPay).orderId

                // Mostrar botón "Pagar" cuando el PaymentSheet esté listo
                Button(
                    onClick = {
                        pagoViewModel.iniciarPago(clientSecret, orderId)
                    }
                ) {
                    Text("Pagar")
                }
            }
            is PaymentState.Error -> {
                val errorMessage = (paymentState as PaymentState.Error).message
                Text("Error: $errorMessage", color = Color.Red)
            }
        }

        // Botón para crear el PaymentIntent
        /*if (paymentState is PaymentState.Loading) {
            Button(
                onClick = {
                    val monto = (totalPrecio * 100).toInt()  // Convierte a centavos (Stripe usa centavos)
                    val orderId = UUID.randomUUID().toString() // Genera un ID único para la orden
                    pagoViewModel.crearPaymentIntent(monto, orderId)
                }
            ) {
                Text("Generar Intento de Pago")
            }
        } */
    }
}

