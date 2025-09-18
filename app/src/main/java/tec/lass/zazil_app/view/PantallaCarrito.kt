package tec.lass.zazil_app.view
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tec.lass.zazil_app.model.Producto
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import tec.lass.zazil_app.viewmodel.PagoViewModel
import tec.lass.zazil_app.viewmodel.ProductoVM
/**
 * Composable que representa la pantalla del carrito de compras.
 * Muestra los productos seleccionados y permite gestionar el proceso de compra.
 */
@Composable
fun PantallaCarrito(
    navController: NavHostController,
    productoVM: ProductoVM,
    pagoViewModel: PagoViewModel
) {

    val context = LocalContext.current
    // Cargar productos del carrito al abrir la pantalla
    LaunchedEffect(Unit) {
        productoVM.cargarProductosCarrito()
    }
    val productosEnCarrito by productoVM.productosEnCarrito.observeAsState(emptyList())
    val listaProductos by productoVM.listaProductos.observeAsState(emptyList())
    // Calcula el total de productos sumando las cantidades de cada producto
    val totalProductos = productosEnCarrito.sumOf { idProducto ->
        val productoCarrito = listaProductos.find { it.product == idProducto }
        productoCarrito?.let { producto ->
            productoVM.obtenerCantidadProducto(producto).value ?: 1
        } ?: 1
    }
    // Calcula el total de precios sumando el precio * cantidad
    val totalPrecio = productosEnCarrito.sumOf { idProducto ->
        val productoCarrito = listaProductos.find { it.product == idProducto }
        productoCarrito?.let { producto ->
            val cantidad = productoVM.obtenerCantidadProducto(producto).value ?: 1
            val precio = producto.price.toDoubleOrNull() ?: 0.0
            cantidad * precio
        } ?: 0.0
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(9.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Mostrar el contador de productos en la parte superior
                Text(
                    text = "Cantidad de productos en el carrito: $totalProductos",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // Mostrar el total del precio de los productos
                Text(
                    text = "Total a pagar: \$${"%.2f".format(totalPrecio)}",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                if (productosEnCarrito.isEmpty()) {
                    // Mostrar un mensaje si el carrito está vacío
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Tu carrito está vacío", textAlign = TextAlign.Center)
                    }
                } else {
                    LazyColumn {
                        items(productosEnCarrito, key = { it }) { idProducto ->
                            val productoCarrito = listaProductos.find { it.product == idProducto }
                            if (productoCarrito != null) {
                                ProductoCarritoCard(
                                    producto = productoCarrito,
                                    onDelete = {
                                        productoVM.toggleCarrito(productoCarrito)
                                    },
                                    onIncreaseQuantity = {
                                        productoVM.aumentarCantidadProducto(productoCarrito)
                                    },
                                    onDecreaseQuantity = {
                                        productoVM.disminuirCantidadProducto(productoCarrito)
                                    },
                                    cantidad = productoVM.obtenerCantidadProducto(productoCarrito).value ?: 1
                                )
                            }
                        }
                    }
                }
            }

            // Sección fija para los botones en la parte inferior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {

                        val intent = Intent(context, PaymentActivity::class.java)
                        intent.putExtra("totalPrecio", totalPrecio.toFloat())
                        context.startActivity(intent)
                        //navController.navigate("pantallaPago/${totalPrecio.toFloat()}")
                    },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text("Ir a pagar")
                }

                Button(
                    onClick = {
                        navController.navigate("tienda")
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text("Seguir comprando")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductoCarritoCard(
    producto: Producto,
    onDelete: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    cantidad: Int  // Aquí recibimos la cantidad desde el ViewModel
) {
    // Implementar la funcionalidad de swipe para eliminar
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDelete()  // Eliminar del carrito
            }
            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        },
        dismissContent = {
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = producto.img),
                        contentDescription = producto.product,
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Fit
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = producto.product,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = producto.category,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Precio: \$${producto.price}", fontSize = 15.sp, color = Color.DarkGray)
                    }
                }
            }
        }
    )
}
