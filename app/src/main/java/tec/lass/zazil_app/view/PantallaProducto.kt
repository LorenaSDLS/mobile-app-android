package tec.lass.zazil_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import tec.lass.zazil_app.R
import tec.lass.zazil_app.model.Producto
/**
 * Pantalla de detalles de un producto.
 * @param navController Controlador de navegación.
 * @param producto Producto seleccionado.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProducto(navController: NavController, producto: Producto) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = producto.category) },
                navigationIcon = {
                    BotonBack(onBackClick = {
                        navController.popBackStack()  // Navegar hacia atrás
                    })
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp)
        ) {
            Text(text = producto.category, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = producto.product, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberAsyncImagePainter(model = producto.img),
                contentDescription = producto.product,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Precio: \$${producto.price}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = producto.description, fontSize = 16.sp)
        }
    }
}

@Composable
fun BotonBack(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Volver"
        )
    }
}
