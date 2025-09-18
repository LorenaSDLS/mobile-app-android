package tec.lass.zazil_app.view
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavHostController

import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon


import androidx.compose.material3.Icon
/**
 * Composable que representa la pantalla de aviso de privacidad.
 * Muestra la información relacionada con las políticas de privacidad de la aplicación.
 */
@Composable
fun PantallaAvisoPriv() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // Habilita el scroll vertical
        verticalArrangement = Arrangement.Top, // Cambia de Center a Top para que comience desde arriba
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "AVISO DE PRIVACIDAD", fontSize = 15.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "En Fundación Todas Brillamos AC, valoramos la privacidad de nuestros clientes y nos comprometemos a proteger la información personal que nos proporcionan. Esta política de privacidad explica cómo recopilamos, utilizamos y protegemos sus datos personales.", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "INFORMACIÓN RECOLECTADA", fontSize = 15.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "• Datos personales: nombre, dirección, correo electrónico, número de teléfono\n" + "• Información de pago: tarjeta de crédito, débito o PayPal", fontSize = 13.sp, color = Color.Black)
        //Text(text = "• Información de pago: tarjeta de crédito, débito o PayPal", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "USO DE LA INFORMACIÓN", fontSize = 15.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "• Procesar y enviar pedidos\n" +
                "• Enviar correos electrónicos con promociones y ofertas especiales\n" +
                "• Mejorar nuestra tienda online y experiencia de usuario", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "PROTECCIÓN DE LA INFORMACIÓN", fontSize = 15.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "• Utilizamos medidas de seguridad para proteger sus datos personales\n" +
                    "• No compartimos información personal con terceros, excepto para procesar pedidos y envíos", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "DERECHO DE LOS CLIENTES", fontSize = 15.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "• Acceder, rectificar o cancelar su información personal en cualquier momento\n" +
                "• Oponerse al uso de su información para fines de marketing", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "CAMBIOS EN LA POLÍTICA DE PRIVACIDAD", fontSize = 15.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "• Podemos actualizar esta política de privacidad en cualquier momento\n" +
                "• Se notificará a los clientes de cualquier cambio significativo", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "FECHA DE ÚLTIMA ACTUALIZACIÓN: 2 de Septiembre 2024", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Si tienes alguna pregunta o inquietud, por favor no dudes en contactarnos.", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "POLITICA DE DEVOLUCIÓN: ", fontSize = 15.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Por ser una prenda íntima y de uso personal, los cambios en este producto no son procedentes. Atendiendo a la Ley Federal de Protección al consumidor en México (PROFECO) únicamente realizaremos cambios por defecto de fábrica. * Aplica únicamente dentro de los primeros 5 días posteriores a la entrega *", fontSize = 13.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

