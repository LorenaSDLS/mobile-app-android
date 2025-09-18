package tec.lass.zazil_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.NavController
import tec.lass.zazil_app.R
/**
 * Pantalla de tema con información detallada.
 * @param titulo Título del tema.
 * @param descripcion Descripción del tema.
 * @param imagen Recurso de imagen del tema.
 * @param infoCompleta Información detallada del tema.
 * @param navController Controlador de navegación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTema(titulo: String, descripcion: String, imagen: Int, infoCompleta: String, navController: NavController) {
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botón de Regresar justo debajo de la TopAppBar
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9045),  // Color de fondo del botón
                        contentColor = Color.White  // Color del texto dentro del botón
                    ),
                    modifier = Modifier
                        .padding(bottom = 16.dp) // Espacio entre el botón y el título
                        .align(Alignment.Start)
                ) {
                    Text(text = "Regresar")
                }

                // Título justo debajo del botón
                Text(
                    text = titulo,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)  // Espacio entre el título y el resto del contenido
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = descripcion,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify
                )

                // Imagen y contenido
                Image(
                    painter = painterResource(id = imagen),
                    contentDescription = titulo,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = infoCompleta,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify)

            }
        }
    )
}
