import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import tec.lass.zazil_app.R
import tec.lass.zazil_app.model.Producto
import tec.lass.zazil_app.viewmodel.ProductoVM
import android.net.Uri
/**
 * Pantalla de la tienda
 *@param navController controlador de navegación
 * @param productoVM ViewModel para la tienda
 */
@Composable
fun PantallaTienda(navController: NavHostController, productoVM: ProductoVM) {
    // Llama a escucharCambiosEnProductos() al iniciar la pantalla
    LaunchedEffect(Unit) {
        productoVM.escucharCambiosEnProductos() // O llama a obtenerListaProductos()
    }

    val listaProductos by productoVM.listaProductos.observeAsState(emptyList())
    val productosEnCarrito by productoVM.productosEnCarrito.observeAsState(emptySet())  // Set de identificadores
    val productosFavoritos by productoVM.productosFavoritos.observeAsState(emptySet())  // Set de identificadores

    var textoBusqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<String?>(null) }

    // Filtrar productos según la búsqueda y la categoría seleccionada
    val productosFiltrados = listaProductos.filter { producto ->
        (textoBusqueda.isEmpty() || producto.product.contains(textoBusqueda, ignoreCase = true)) &&
                (categoriaSeleccionada == null || producto.category == categoriaSeleccionada)
    }

    // UI para mostrar la lista de productos
    Scaffold {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            Spacer(modifier = Modifier.height(8.dp))

            // Barra de búsqueda
            SearchBar(texto = textoBusqueda) { nuevoTexto ->
                textoBusqueda = nuevoTexto
                categoriaSeleccionada = null  // Resetea la categoría al buscar
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filtros de categorías
            CategoriesRow(categoriaSeleccionada) { categoria ->
                categoriaSeleccionada = if (categoriaSeleccionada == categoria) null else categoria
                textoBusqueda = ""  // Resetea la búsqueda al seleccionar categoría
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar productos filtrados
            if (productosFiltrados.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productosFiltrados) { producto ->

                        val esFavorito = productosFavoritos.contains(producto.product)  // Verifica si el ID está en favoritos
                        val estaEnCarrito = productosEnCarrito.contains(producto.product)  // Verifica si el ID está en carrito

                        ProductoCard(
                            producto = producto,
                            esFavorito = esFavorito,
                            estaEnCarrito = estaEnCarrito,
                            onFavoriteClick = { productoVM.toggleFavorito(producto) },
                            onCarritoClick = { productoVM.toggleCarrito(producto) },
                            navController = navController
                        )
                    }
                }
            } else {
                Text(
                    text = "No se encontraron productos",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@Composable
fun SearchBar(texto : String, onSearch: (String) -> Unit) {
    var textoBusqueda by remember { mutableStateOf (texto) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        androidx.compose.material3.Icon(
            painter = painterResource(id = R.drawable.ic_buscar),
            contentDescription = "Buscar",
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = textoBusqueda,
            onValueChange = {
                textoBusqueda = it
                onSearch(textoBusqueda) // Llama a la función de búsqueda cuando cambia el texto
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Gray)
        )
        androidx.compose.material3.Icon(
            painter = painterResource(id = R.drawable.ic_filtro),
            contentDescription = "Filtrar",
            tint = Color.Black
        )
    }
}

fun spacer(modifier: Modifier) {

}

@Composable
fun CategoriesRow(
    categoriaSeleccionada: String?,
    onCategorySelected: (String) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CategoryItem(
            label = "Nocturnas",
            imageRes = R.drawable.nocturne,
            isSelected = categoriaSeleccionada == "Nocturnas",
            onClick = { onCategorySelected("Nocturnas") })
        CategoryItem(
            label = "Teens",
            imageRes = R.drawable.teen,
            isSelected = categoriaSeleccionada == "Teens",
            onClick = { onCategorySelected("Teens") })
        CategoryItem(
            label = "Regulares",
            imageRes = R.drawable.regular,
            isSelected = categoriaSeleccionada == "Regulares",
            onClick = { onCategorySelected("Regulares") })
        CategoryItem(
            label = "Kits",
            imageRes = R.drawable.kit,
            isSelected = categoriaSeleccionada == "Kits",
            onClick = { onCategorySelected("Kits") })
        CategoryItem(
            label = "Protectores",
            imageRes = R.drawable.protector,
            isSelected = categoriaSeleccionada == "Pantiprotectores diarios",
            onClick = { onCategorySelected("Pantiprotectores diarios") })
    }
}


@Composable
fun CategoryItem(label: String, imageRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF95E3EB) else Color(0xFFF2CAD5)
    val borderColor = if (isSelected) Color(0xFFE91E63) else Color(0xFFE91E63)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick()}
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier.size(48.dp), // Ajustar el tamaño de la imagen
            contentScale = ContentScale.Crop // Ajustar la escala de la imagen
        )
        androidx.compose.material3.Text(text = label, fontSize = 12.sp, color = Color(0xFFE91E63))
    }
}



@Composable
fun ProductoCard(
    producto: Producto,
    esFavorito: Boolean,
    estaEnCarrito: Boolean,
    onFavoriteClick: () -> Unit,
    onCarritoClick: () -> Unit,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
            .padding(8.dp)
            .clickable {
                // Navega a la pantalla de detalles pasando los parámetros del producto
                navController.navigate("detalle_producto/${Uri.encode(producto.category)}/${Uri.encode(producto.img)}/${Uri.encode(producto.price)}/${Uri.encode(producto.description)}/${Uri.encode(producto.product)}")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = producto.category,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )

            Text(
                text = producto.product,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = rememberAsyncImagePainter(model = producto.img),
                contentDescription = "Imagen de ${producto.product}",
                modifier = Modifier.height(140.dp).fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Precio: \$${producto.price}",
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onFavoriteClick() }){
                    Icon(
                        painter = painterResource(id = if (esFavorito) R.drawable.ic_favorito_lleno else R.drawable.ic_favorito_vacio),
                        contentDescription = "Favorito",
                        tint = if (esFavorito) Color.Red else Color.Gray
                    )
                }

                IconButton(onClick = {
                    onCarritoClick()})
                {
                    Icon(
                        painter = painterResource(id = if (estaEnCarrito) R.drawable.ic_carrito_lleno else R.drawable.ic_carrito_vacio),
                        contentDescription = "Carrito",
                        tint = if (estaEnCarrito) Color.Magenta else Color.Gray
                    )
                }
            }
        }
    }
}
