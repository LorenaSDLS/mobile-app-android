@file:OptIn(ExperimentalMaterial3Api::class)

package tec.lass.zazil_app.view

import PantallaTienda
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import mx.sphr.zazil.viewmodel.LoginViewModel
import mx.sphr.zazil.viewmodel.SignUpViewModel
import tec.lass.zazil_app.R
import tec.lass.zazil_app.model.Producto
import tec.lass.zazil_app.model.UserRepository
import tec.lass.zazil_app.model.integrantes
import tec.lass.zazil_app.viewmodel.PagoViewModel
import tec.lass.zazil_app.viewmodel.PasswordRecoveryViewModel
import tec.lass.zazil_app.viewmodel.ProductoVM
import tec.lass.zazil_app.viewmodel.SessionViewModel
import tec.lass.zazil_app.viewmodel.UserProfileViewModel

/**
 * Composable MyApp
 *
 * Esta función define la estructura general de la aplicación utilizando Jetpack Compose.
 * Dependiendo de la ruta de navegación actual, decide si mostrar la pantalla de inicio de sesión o el menú lateral.
 *
 * @param navController Controlador de navegación utilizado para manejar las rutas entre pantallas.
 * @param sessionViewModel ViewModel que maneja el número de teléfono de la sesión.
 * @param productosState Lista mutable que contiene los productos de la tienda.
 * @param categoriasState Lista mutable que contiene las categorías de productos.
 * @param carritoState Lista mutable que contiene los productos en el carrito.
 * @param carritoVM ViewModel que maneja la lógica del carrito.
 * @param productoVM ViewModel que maneja la lógica de los productos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun MyApp(navController: NavHostController, sessionViewModel: SessionViewModel) {
    //val productosState = remember { mutableStateListOf<Producto>() }
    val productoVM: ProductoVM = viewModel()  // Instancia del ViewModel


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination?.route

    //val productosState = remember { mutableStateListOf<Producto>()}


    if (currentRoute == "login" || currentRoute == "signup" || currentRoute == "password_recovery") {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    NavigationComponent(navController = navController, productoVM = productoVM, sessionViewModel = sessionViewModel)
                }
            }
        )
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    DrawerHeader(
                        sessionViewModel = sessionViewModel, // Pasamos el SessionViewModel
                        navController = navController,
                        currentRoute = currentRoute,
                        onHeaderClick = {
                            val phone = sessionViewModel.phoneNumber.value
                            if (currentRoute != "perfil" && phone != null) {
                                navController.navigate("perfil/$phone") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        },
                        onCloseDrawer = {
                            scope.launch { drawerState.close() }
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    DrawerContent(navController = navController, onCloseDrawer = {
                        scope.launch { drawerState.close() }
                    })
                }
            },
            content = {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        val backStackEntry = navController.currentBackStackEntryAsState().value
                        val currentRoute = backStackEntry?.destination?.route
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = when (currentRoute) {
                                        "inicio" -> "INICIO"
                                        "tienda" -> "TIENDA"
                                        "productos/{categoriaSeleccionada}/{textoBusqueda}" -> "TIENDA"
                                        "calendario" -> "CALENDARIO"
                                        "hablemos" -> "HABLEMOS DE..."
                                        "perfil" -> "PERFIL"
                                        "carrito" -> "CARRITO"
                                        "conocenos" -> "CONÓCENOS"
                                        "aviso_priv" -> "AVISO DE PRIVACIDAD"
                                        "terminos_con" -> "TÉRMINOS Y CONDICIONES"
                                        "favoritos" -> "FAVORITOS"
                                        "creditos" -> "CRÉDITOS"
                                        "tema/{titulo}/{descripcion}/{imagen}/{infoCompleta}" -> "HABLEMOS DE..."
                                        "donaciones" -> "DONACIONES"
                                        else -> " "
                                    }
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                    Icon(
                                        Icons.Filled.Menu,
                                        contentDescription = "Abrir Menú",
                                        tint = Color.White
                                    )
                                }
                            },
                            actions = {
                                // Aquí agregamos el botón del carrito
                                IconButton(onClick = {
                                    navController.navigate("carrito") // Navegar a la pantalla del carrito
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_carrito),  // Cargar un vector como icono
                                        contentDescription = "Carrito",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFFFDD5507C),
                                titleContentColor = Color.White
                            )
                        )
                    },

                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            NavigationComponent(navController = navController, productoVM = productoVM, sessionViewModel = sessionViewModel)
                        }
                    }
                )
            }
        )
    }
}

/**
 * Composable NavigationComponent
 *
 * Esta función define la navegación entre las diferentes pantallas de la aplicación utilizando NavHost.
 *
 * @param navController Controlador de navegación utilizado para cambiar de pantalla.
 * @param productosState Lista mutable que contiene los productos de la tienda.
 * @param sessionViewModel ViewModel que maneja el número de teléfono de la sesión.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationComponent(
    navController: NavHostController,
    productoVM: ProductoVM,  // Ahora pasamos el ViewModel aquí
    sessionViewModel: SessionViewModel) {

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = LoginViewModel(),
                sessionViewModel = sessionViewModel // Pasamos el SessionViewModel a la pantalla de login
            )
        }
        composable("signup") {
            SignUpScreen(
                navController = navController,
                viewModel = SignUpViewModel(userRepository = UserRepository())
            )
        }

        composable("password_recovery") {
            PasswordRecoveryScreen(
                navController = navController,
                viewModel = PasswordRecoveryViewModel()
            )
        }
        composable("inicio") {
            // Pasamos el SessionViewModel a la pantalla de inicio para acceder al teléfono
            PantallaInicio(navController = navController, sessionViewModel = SessionViewModel())
        }

        composable("tienda") {
            PantallaTienda(
                navController = navController,
                productoVM = productoVM  // Pasa el ViewModel aquí
            )
        }

        composable(
            "detalle_producto/{product}/{img}/{price}/{description}/{category}",
            arguments = listOf(
                navArgument("product") { type = NavType.StringType },
                navArgument("img") { type = NavType.StringType },
                navArgument("price") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val product = backStackEntry.arguments?.getString("product") ?: ""
            val img = backStackEntry.arguments?.getString("img") ?: ""
            val price = backStackEntry.arguments?.getString("price") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val category = backStackEntry.arguments?.getString("category") ?: ""

            // Crear un producto temporal con los datos recibidos
            val producto = Producto(
                product = product,
                category = category,
                img = img,
                price = price,
                description = description,
                //discountPercent = "",
                //stock = "",
                //timeStamp = null,
                //favorito = false,
                //carrito = false,
            )
            PantallaProducto(navController, producto)
        }

        composable(route = "perfil/{phone}") { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            PantallaPerfil(viewModel = UserProfileViewModel(), phone = phone)
        }

        composable("calendario") {
            PantallaCalendario()
        }

        composable("hablemos") {
            PantallaHablemosDe(navController = navController)
        }

        composable("conocenos") {
            PantallaConocenos()
        }

        composable("aviso_priv") {
            PantallaAvisoPriv()
        }

        composable("creditos") {
            PantallaCreditos(integrantes = integrantes)
        }

        composable("favoritos") {
            PantallaFavoritos(
                navController = navController,  // Pasa el NavHostController
                productoVM = productoVM  // Pasa el ViewModel
            )
        }
        composable(
            "tema/{titulo}/{descripcion}/{imagen}/{infoCompleta}",
            arguments = listOf(
                navArgument("titulo") { type = NavType.StringType },
                navArgument("descripcion") { type = NavType.StringType },
                navArgument("imagen") { type = NavType.IntType },
                navArgument("infoCompleta") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: "Sin título"
            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: "Sin descripción"
            val imagen = backStackEntry.arguments?.getInt("imagen") ?: 0
            val infoCompleta = backStackEntry.arguments?.getString("infoCompleta") ?: "Sin información completa"
            PantallaTema(
                titulo = titulo,
                descripcion = descripcion,
                imagen = imagen,
                infoCompleta = infoCompleta,
                navController = navController
            )
        }

        composable("donaciones") {
            PantallaDonaciones()
        }

       /* composable("pantallaPago") {
            val pagoViewModel: PagoViewModel = viewModel()
            PantallaPago(pagoViewModel = pagoViewModel)
        }*/

        /*composable(
            route = "pantallaPago/{totalPrecio}",
            arguments = listOf(navArgument("totalPrecio") { type = NavType.FloatType })
        ) { backStackEntry ->

            val totalPrecio = backStackEntry.arguments?.getFloat("totalPrecio") ?: 0.0f

            // Obtener el contexto actual y convertirlo a ComponentActivity
            val activity = LocalContext.current as ComponentActivity

            // Obtener el mismo ViewModel que está en la actividad
            val pagoViewModel: PagoViewModel = viewModel(activity)

            PantallaPago(
                pagoViewModel = pagoViewModel,
                totalPrecio = totalPrecio,
                activity = activity
            )
        }*/






        composable("carrito") {
            PantallaCarrito(
                navController = navController,
                productoVM = productoVM,
                pagoViewModel = viewModel()  // Pasa el ViewModel aquí
            )
        }
    }
}
