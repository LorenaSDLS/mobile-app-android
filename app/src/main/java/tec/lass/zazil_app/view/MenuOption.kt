package tec.lass.zazil_app.view
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
/**
 * Clase que representa una opción de menú en el drawer.
 * Cada opción contiene un nombre y un ícono.
 */
@Composable
fun MenuOption(iconResId: Int, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color.Gray,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(8.dp)

        , // Hacer clic en la opción del menú

        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(25.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(androidx.compose.ui.graphics.Color.Black)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = label,
            color = androidx.compose.ui.graphics.Color.Black)
    }
}