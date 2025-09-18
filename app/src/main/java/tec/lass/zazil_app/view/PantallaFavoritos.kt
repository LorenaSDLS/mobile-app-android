package tec.lass.zazil_app.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import tec.lass.zazil_app.R
import tec.lass.zazil_app.model.Producto
import tec.lass.zazil_app.viewmodel.ProductoVM
/**
 * Composable que representa la pantalla de 'Favoritos'.
 * @param navController Controlador de navegación.
 * @param productoVM ViewModel asociado a la pantalla de favoritos.
 */
@Composable
fun PantallaFavoritos(
    navController: NavHostController,
    productoVM: ProductoVM = viewModel()
) {
    // Observar los identificadores de productos favoritos
    val productosFavoritos by productoVM.productosFavoritos.observeAsState(emptyList())

    // Observar la lista de productos que viene de Firebase
    val listaProductos by productoVM.listaProductos.observeAsState(emptyList())

    LazyColumn {
        items(productosFavoritos) { idProducto ->
            val productoFavorito = listaProductos.find { it.product == idProducto }
            Log.d("ProductoFavorito", "Producto: $productoFavorito")
            if (productoFavorito != null) {
                ProductoFavoritoCard(
                    producto = productoFavorito,
                    onFavoriteClick = { productoVM.toggleFavorito(productoFavorito) },
                    onCarritoClick = { productoVM.toggleCarrito(productoFavorito) },
                    navController = navController
                )
            }
        }
    }
}

    @Composable
fun ProductoFavoritoCard(
    producto: Producto,
    onFavoriteClick: () -> Unit,
    onCarritoClick: () -> Unit,
    navController: NavController
) {
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
            Image(
                painter = rememberAsyncImagePainter(model = producto.img),
                contentDescription = producto.product,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = producto.product, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = producto.price, fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    painter = painterResource(id = if (producto.favorito) R.drawable.ic_favorito_lleno else R.drawable.ic_favorito_vacio),
                    contentDescription = "Favorito",
                    tint = if (producto.favorito) Color.Red else Color.Gray
                )
            }
            IconButton(onClick = onCarritoClick) {
                Icon(
                    painter = painterResource(id = if (producto.carrito) R.drawable.ic_carrito_lleno else R.drawable.ic_carrito_vacio),
                    contentDescription = "Carrito",
                    tint = if (producto.carrito) Color.Magenta else Color.Gray
                )
            }
        }
    }
}
