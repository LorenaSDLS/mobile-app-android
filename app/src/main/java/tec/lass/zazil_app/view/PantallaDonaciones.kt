package tec.lass.zazil_app.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
/**
 * Composable que representa la pantalla de 'Donaciones'.
 */

@Composable
fun PantallaDonaciones() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Patrocinadores: Construyendo Comunidad, Transformando Vidas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = androidx.compose.ui.graphics.Color(0xFFC2185B)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "En Zazil, ser patrocinador significa mucho más que solo realizar una donación. Promovemos una transformación profunda hacia una cultura menstruante sostenible, ofreciendo toallas reutilizables como alternativa ecológica y apoyando a quienes más lo necesitan.Al involucrarte como patrocinador, estarás participando activamente en la construcción de una comunidad solidaria donde el apoyo es recíproco y humano. ",
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Texto adicional sobre la importancia de la donación
        Text(
            text = "¿Qué Logramos Juntos?\n" +
                    "Educación y Conciencia: Impulsamos la conversación sobre la menstruación, eliminando tabúes y promoviendo una cultura inclusiva y sin prejuicios.\n" +
                    "Sostenibilidad Ambiental: Informamos sobre la contaminación generada por las toallas desechables y ofrecemos soluciones amigables con el planeta a través de nuestras toallas reutilizables.\n" +
                    "Acceso para Todas: Con tu aportación, ayudamos a personas que no tienen los recursos para adquirir productos de higiene femenina. Tu donación se transforma en un kit de toallas reutilizables para quien más lo necesita."
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Texto adicional sobre la contribución
        Text(
            text = "¿Por qué ser Patrocinador?\n" +
                    "\n" +
                    "Transforma vidas: Al donar, estás proporcionando un recurso esencial y contribuyendo a la dignidad menstrual de muchas personas.\n" +
                    "Genera impacto sostenible: Tu apoyo no solo ayuda ahora, sino que promueve hábitos ecológicos para un futuro mejor.\n" +
                    "Involúcrate y crea comunidad: Recibirás actualizaciones periódicas sobre cómo tu aportación está cambiando vidas, para que seas parte de cada paso hacia el cambio."
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Al formar parte de esta comunidad de patrocinadores, juntos reducimos la brecha de acceso a productos de higiene, promovemos la sostenibilidad y educamos sobre la importancia de la salud menstrual. Únete y descubre cómo una pequeña acción puede tener un gran impacto social y ambiental.",
        )

        // Llamado a la acción
        Text(
            text = "Patrocina hoy y sé parte del cambio.",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Card para resaltar la información de la donación
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = androidx.compose.ui.graphics.Color(0xFFFFC0CB)
            )

        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Fundación Todas Brillamos A.C.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Banorte")
                Text(text = "Cuenta: 1096319621")
                Text(text = "Clabe: 072180010963196216")
                Text(text = "Concepto: Cuota de recuperación")
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Contacto: +52 56 2808 3883")
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Por favor, envía una captura de tu donación junto con tu nombre a este número.",
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
