package tec.lass.zazil_app.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheetResult
import tec.lass.zazil_app.viewmodel.PagoViewModel

/*class PaymentActivity : ComponentActivity() {
    private val pagoViewModel: PagoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la configuración de Stripe con la clave pública
        PaymentConfiguration.init(applicationContext, "pk_test_51QAcoXFxGNIrLyOAjFRRld2LNHZ9muBGEadk1bRib8WeCxX6oBYXxe29SpxBcgP7WMTlr4muR7uFuJCoOo0UiAtW00dKUN8eXJ")

        // Asegúrate de llamar a initializePaymentSheet ANTES de iniciar cualquier pago
        pagoViewModel.initializePaymentSheet(this) { paymentResult ->
            val orderId = "10"  // ID de la orden
            handlePaymentResult(paymentResult, orderId)
        }

        // Configura el contenido con Jetpack Compose
        setContent {
            PantallaPago(pagoViewModel = pagoViewModel, navController = rememberNavController(), totalPrecio = 830.0)
        }
    }

    private fun handlePaymentResult(paymentResult: PaymentSheetResult, orderId: String) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                pagoViewModel.handlePaymentSuccess(orderId)
            }
            is PaymentSheetResult.Failed -> {
                pagoViewModel.handlePaymentFailure(orderId)
            }
            is PaymentSheetResult.Canceled -> {
                Log.d("PaymentActivity", "Pago cancelado")
            }
        }
    }
}
*/