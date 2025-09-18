package tec.lass.zazil_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.changelist.Operation.AdvanceSlotsBy.name
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.auth.User
import tec.lass.zazil_app.R
import tec.lass.zazil_app.viewmodel.SessionViewModel
/**
 * Pantalla de inicio de la aplicación.
 * @param navController Controlador de navegación.
 * @param SessionViewModel Modelo de vista de la sesión.
 */

@Composable
fun PantallaInicio(navController: NavHostController, sessionViewModel: SessionViewModel) {
    //val phone by SessionViewModel.phoneNumber.observeAsState("")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // Título de la pantalla
        Text(
            text = "¡Nos alegra tenerte aquí!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "En ZAZIL celebramos la diversidad y nos comprometemos a apoyar a todas las personas en su bienestar menstrual.",
            fontSize = 13.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
// SECCIÓN TIENDA
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Explora productos sostenibles diseñados para cuidar de ti y del planeta.",
            fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        // Botón para ir a la tienda
        Button(onClick = { navController.navigate("tienda") })
        { Text("Ir a la Tienda") }
        Spacer(modifier = Modifier.height(8.dp))
        // Carrusel de imágenes
        Text(text = "Algunos de nuestros productos:", fontSize = 13.sp, fontWeight = FontWeight.Light, textAlign = androidx.compose.ui.text.style.TextAlign.Left)
        Spacer(modifier = Modifier.height(8.dp))
        val imagenes = listOf(
            R.drawable.tienda1,
            R.drawable.tienda2,
            R.drawable.tienda3,
            R.drawable.tienda4,
            R.drawable.tienda5
        )
        CarruselDeImagenes(imagenes)

// SECCIÓN HABLEMOS DE
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "En ZAZIL tenemos como objetivo romper tabúes y generar conciencia sobre la menstruación.",
            fontSize = 15.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Botón para ir a la tienda
        Button(onClick = { navController.navigate("hablemos") })
        { Text("Visitar hablemos de...") }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Todos estos temas y más en nuestro apartado informativo", fontSize = 13.sp, fontWeight = FontWeight.Light, textAlign = androidx.compose.ui.text.style.TextAlign.Start)
        Spacer(modifier = Modifier.height(8.dp))
        val imagenes2 = listOf(
            R.drawable.tienda1,
            R.drawable.tienda2,
            R.drawable.tienda3,
            R.drawable.tienda4,
            R.drawable.tienda5
        )
        CarruselDeImagenes(imagenes2)

        // SECCIÓN CALENDARIO
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Prepárate para tu siguiente periodo con Zazil, prueba nuestro calendario menstrual y conoce más de tu ciclo.",
            fontSize = 15.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate("calendario") })
        { Text("Conocer calendario menstrual") }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Conoce las fases de tu ciclo menstrual:", fontSize = 13.sp, fontWeight = FontWeight.Light, textAlign = androidx.compose.ui.text.style.TextAlign.Start)
        Spacer(modifier = Modifier.height(8.dp))
        val imagenes3 = listOf(
            R.drawable.tienda1,
            R.drawable.tienda2,
            R.drawable.tienda3,
            R.drawable.tienda4,
            R.drawable.tienda5
        )

        CarruselDeImagenes(imagenes3)

        // SECCIÓN CONOCENOS
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Explora nuestro sitio y descubre cómo ZAZIL está trabajando para mejorar la salud menstrual de todas las personas.\n" +  "¡Queremos que te sientas parte de este movimiento",
            fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para ir a la tienda
        Button(onClick = {
            navController.navigate("conocenos")
        }) {
            Text("Saber más de ZAZIL")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Un poco de ZAZIL", fontSize = 13.sp, fontWeight = FontWeight.Light, textAlign = androidx.compose.ui.text.style.TextAlign.Start)
        Spacer(modifier = Modifier.height(8.dp))
        val imagenes4 = listOf(
            R.drawable.tienda1,
            R.drawable.tienda2,
            R.drawable.tienda3,
            R.drawable.tienda4,
            R.drawable.tienda5
        )
        CarruselDeImagenes(imagenes4)

        // SECCIÓN DONACIONES
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Trabajamos incansablemente para promover la igualdad, empoderar a las mujeres y asegurar que todas tengan acceso a productos de higiene menstrual.\n" +
                    "Únete a nosotros en esta importante causa.",
            fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            navController.navigate("donaciones")
        }) {
            Text("Dona aquí")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tu ayuda puede marcar la diferencia.", fontSize = 13.sp, fontWeight = FontWeight.Light, textAlign = androidx.compose.ui.text.style.TextAlign.Start)
        Spacer(modifier = Modifier.height(8.dp))
        val imagenes5 = listOf(
            R.drawable.tienda1,
            R.drawable.tienda2,
            R.drawable.tienda3,
            R.drawable.tienda4,
            R.drawable.tienda5
        )

        CarruselDeImagenes(imagenes5)
    }
}

@Composable
fun CarruselDeImagenes(imagenes: List<Int>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(imagenes) { imagen ->
            Image(
                painter = painterResource(id = imagen),
                contentDescription = "Imagen de producto",
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}
