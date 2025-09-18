package tec.lass.zazil_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tec.lass.zazil_app.R
import tec.lass.zazil_app.model.Integrante
import tec.lass.zazil_app.model.Producto
import tec.lass.zazil_app.model.Tema
import tec.lass.zazil_app.model.integrantes
import tec.lass.zazil_app.model.temas
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.navigation.NavController
import tec.lass.zazil_app.view.PantallaTema
/**
 * Composable que muestra la pantalla de Hablemos de.
 * @param navController Controlador de navegación para navegar a otras pantallas.
 */
@Composable
fun PantallaHablemosDe(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),  // Habilita el desplazamiento vertical
        verticalArrangement = Arrangement.Top,  // Cambiar a Top para que no quede en el centro
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Una menstruación consciente y una vida saludable",
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Image(
            painter = painterResource(id = R.drawable.recordatorio),
            contentDescription = "recordatorio",
            modifier = Modifier
                .height(370.dp)  // Ajusta el tamaño de la imagen
        )
        Spacer(modifier = Modifier.height(8.dp))
        Seccion(title = "ZAZIL")
        Spacer(modifier = Modifier.height(8.dp))
        AreaRow(temas = temas.filter { it.Zazil }, navController = navController)
        Spacer(modifier = Modifier.height(8.dp))
        Seccion(title = "Sabías que...")
        Spacer(modifier = Modifier.height(8.dp))
        AreaRow(temas = temas.filter { it.Dato }, navController = navController)
        Spacer(modifier = Modifier.height(8.dp))
        Seccion(title = "Menstruación")
        Spacer(modifier = Modifier.height(8.dp))
        AreaRow(temas = temas.filter { it.Menst }, navController = navController)
    }
}


@Composable
fun Seccion (title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE91E63), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
    }
}
@Composable
fun AreaRow(temas: List<Tema>, navController: NavController) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(temas) { tema ->
            TemaCard(tema = tema, navController = navController)

        }
    }
}

@Composable
fun TemaCard(tema: Tema, navController: NavController) {
    Card(
        modifier = Modifier
            .width(180.dp)  // Ancho fijo para todas las tarjetas
            .padding(4.dp)
            .heightIn(min = 250.dp)
            .clickable {
                // Navegar a la pantalla de detalles de la noticia pasando los parámetros correctos
                navController.navigate("tema/${tema.titulo}/${tema.descripcion}/${tema.imagen}/${tema.infoCompleta}")

            },// Altura mínima para todas las tarjetas
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 300.dp) // Para asegurar la altura mínima
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = tema.imagen),  // Reemplazar con imagen real
                    contentDescription = tema.titulo,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = tema.titulo, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = tema.descripcion, fontSize = 11.sp, color = Color.Gray)
            }

            // "Leer más..." alineado al fondo
            Text(
                text = "Leer más...",
                color = Color.Blue,
                modifier = Modifier
                    .align(Alignment.BottomCenter)  // Alineado al fondo del Box
                    .padding(8.dp),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
