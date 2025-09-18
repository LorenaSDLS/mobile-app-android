package tec.lass.zazil_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tec.lass.zazil_app.R
import tec.lass.zazil_app.model.Integrante
import tec.lass.zazil_app.model.Producto

/**
 * Composable que representa la pantalla de créditos.
 * @param integrantes Lista de integrantes que se mostrarán en la pantalla de créditos.
 */
@Composable
fun PantallaCreditos(integrantes: List<Integrante>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(13.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Integrantes", fontSize = 18.sp, color = Color.Black)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(integrantes) { integrante ->
                IntegranteCard(integrante = integrante)
            }
        }
    }
}

@Composable
fun IntegranteCard(integrante: Integrante) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto del integrante a la izquierda
            Image(
                painter = painterResource(id = integrante.Foto), // Reemplazar con imagen real
                contentDescription = integrante.Nombre,
                modifier = Modifier
                    .size(80.dp) // Tamaño de la imagen
                    .padding(end = 16.dp), // Espacio entre imagen y texto
                contentScale = ContentScale.Crop
            )

            // Información del integrante a la derecha
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${integrante.Nombre} ${integrante.Apellido}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = integrante.Correo, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}
