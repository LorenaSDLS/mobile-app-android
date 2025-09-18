package tec.lass.zazil_app.viewmodel

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
/**
 * ViewModel para el flujo de pago.
 * @property functions Instancia de FirebaseFunctions para llamar a las funciones en Firebase.
 * @property _paymentState Estado mutable del flujo de pago.
 * @property paymentState Estado inmutable del flujo de pago.
 * @property crearPaymentIntent Llama a la función en Firebase para obtener el client_secret.
 * @property handlePaymentSuccess Actualiza el estado de la orden en Firestore al éxito del pago.
 * @property handlePaymentFailure Actualiza el estado de la orden en Firestore al fracaso del pago.
 * @property updateOrderStatus Actualiza el estado de la orden en Firestore.
 * @property PaymentState Estados posibles del flujo de pago.
 */

class PagoViewModel : ViewModel() {

    private lateinit var paymentSheet: PaymentSheet
    private val functions = FirebaseFunctions.getInstance()

    // Flow para manejar el estado del pago
    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Loading)
    val paymentState: StateFlow<PaymentState> = _paymentState

    // Inicializamos el PaymentSheet para el pago
    /*fun initializePaymentSheet(
        activity: ComponentActivity,
        onPaymentResult: (PaymentSheetResult) -> Unit
    ) {
        paymentSheet = PaymentSheet(activity, onPaymentResult)
        Log.d("PagoViewModel", "paymentSheet inicializado correctamente.")
    }*/

    // Inicializamos el PaymentSheet para el pago
    fun initializePaymentSheet(
        activity: ComponentActivity,
        onPaymentResult: (PaymentSheetResult) -> Unit
    ) {
        if (!::paymentSheet.isInitialized) {
            paymentSheet = PaymentSheet(activity, onPaymentResult)
            Log.d("PagoViewModel", "paymentSheet inicializado correctamente.")
        } else {
            Log.d("PagoViewModel", "paymentSheet ya estaba inicializado.")
        }
    }



    // Llama a la función en Firebase para obtener el client_secret
   /* fun crearPaymentIntent(monto: Int, orderId: String) {
        val data = hashMapOf(
            "amount" to monto,
            "currency" to "mxn"
        )

        functions
            .getHttpsCallable("createPaymentIntent")
            .call(data)
            .addOnSuccessListener { result ->
                val dataMap = result.data as? Map<*, *>
                val clientSecret = dataMap?.get("clientSecret") as? String
                if (clientSecret != null) {
                    _paymentState.value = PaymentState.ReadyToPay(clientSecret, orderId)
                } else {
                    _paymentState.value = PaymentState.Error("Error: clientSecret es nulo.")
                    Log.e("PagoViewModel", "Error: clientSecret es nulo.")
                }
            }
            .addOnFailureListener { e ->
                _paymentState.value = PaymentState.Error("Error al crear PaymentIntent: ${e.message}")
                Log.e("PagoViewModel", "Error al crear PaymentIntent", e)
            }
    }*/

    fun crearPaymentIntent(monto: Int, orderId: String) {
        Log.d("PagoViewModel", "Creando PaymentIntent para monto: $monto y orderId: $orderId")
        val data = hashMapOf(
            "amount" to monto,
            "currency" to "mxn"
        )

        functions
            .getHttpsCallable("createPaymentIntent")
            .call(data)
            .addOnSuccessListener { result ->
                val dataMap = result.data as? Map<*, *>
                val clientSecret = dataMap?.get("clientSecret") as? String
                if (clientSecret != null) {
                    Log.d("PagoViewModel", "PaymentIntent creado con clientSecret: $clientSecret")
                    _paymentState.value = PaymentState.ReadyToPay(clientSecret, orderId)
                } else {
                    _paymentState.value = PaymentState.Error("Error: clientSecret es nulo.")
                    Log.e("PagoViewModel", "Error: clientSecret es nulo.")
                }
            }
            .addOnFailureListener { e ->
                _paymentState.value = PaymentState.Error("Error al crear PaymentIntent: ${e.message}")
                Log.e("PagoViewModel", "Error al crear PaymentIntent", e)
            }
    }



    // Iniciar el pago con Stripe
    /*fun iniciarPago(clientSecret: String, orderId: String) {
        if (::paymentSheet.isInitialized) {
            paymentSheet.presentWithPaymentIntent(
                clientSecret, PaymentSheet.Configuration(
                    merchantDisplayName = "Zazil"
                )
            )
        } else {
            Log.e("PagoViewModel", "Error: paymentSheet no está inicializado.")
        }
    }*/

    // Iniciar el pago con Stripe
    fun iniciarPago(clientSecret: String, orderId: String) {
        val paymentSheetInstance = paymentSheet
        if (paymentSheetInstance != null) {
            paymentSheetInstance.presentWithPaymentIntent(
                clientSecret, PaymentSheet.Configuration(
                    merchantDisplayName = "Zazil"
                )
            )
        } else {
            _paymentState.value = PaymentState.Error("Error: paymentSheet no está inicializado.")
            Log.e("PagoViewModel", "Error: paymentSheet no está inicializado.")
        }
    }



    // Actualiza el estado de la orden en Firestore
    private fun updateOrderStatus(orderId: String, status: String) {
        val orderRef = FirebaseFirestore.getInstance().collection("orders").document(orderId)

        orderRef.update("status", status)
            .addOnSuccessListener {
                Log.d("PagoViewModel", "Orden actualizada exitosamente a $status.")
            }
            .addOnFailureListener { e ->
                Log.e("PagoViewModel", "Error actualizando la orden", e)
            }
    }

    fun handlePaymentSuccess(orderId: String) {
        updateOrderStatus(orderId, "Aprobado")
    }

    fun handlePaymentFailure(orderId: String) {
        updateOrderStatus(orderId, "Fallido")
    }
}

// Estados posibles del flujo de pago
sealed class PaymentState {
    object Loading : PaymentState()
    data class ReadyToPay(val clientSecret: String, val orderId: String) : PaymentState()
    data class Error(val message: String) : PaymentState()
}
