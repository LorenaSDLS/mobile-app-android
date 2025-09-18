package tec.lass.zazil_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import tec.lass.zazil_app.datastore.ProductoDataStore
import tec.lass.zazil_app.model.Producto
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
/**
 * Clase ProductoVM
 * @param application Aplicación de Android.
 * @property firestore Instancia de FirebaseFirestore para interactuar con Firestore.
 * @property productoDataStore Instancia de ProductoDataStore para el manejo del carrito.
 * @property _listaProductos MutableLiveData para la lista de productos.
 * @property listaProductos LiveData que contiene la lista de productos.
 * @property _productosEnCarrito MutableLiveData para la lista de productos en el carrito.
 * @property productosEnCarrito LiveData que contiene la lista de productos en el carrito.
 * @property _productosFavoritos MutableLiveData para la lista de productos favoritos.
 * @property productosFavoritos LiveData que contiene la lista de productos favoritos.
 * @property escucharCambiosEnProductos Función para escuchar cambios en la colección de productos.
 */

class ProductoVM(application: Application) : AndroidViewModel(application) {

    private val firestore = FirebaseFirestore.getInstance()
    private val productoDataStore = ProductoDataStore.getInstance(application)

    // Productos desde Firebase
    private val _listaProductos = MutableLiveData<List<Producto>>()
    val listaProductos: LiveData<List<Producto>> = _listaProductos


    // Aquí aseguramos que productosEnCarrito sea una lista de identificadores de tipo String
    private val _productosEnCarrito = MutableLiveData<List<String>>()
    val productosEnCarrito: LiveData<List<String>> = _productosEnCarrito

    private val _productosFavoritos = MutableLiveData<List<String>>()
    val productosFavoritos: LiveData<List<String>> = _productosFavoritos


    // Escuchar cambios en Firebase
    fun escucharCambiosEnProductos() {
        FirebaseFirestore.getInstance().collection("products")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Error al escuchar productos", error)
                    return@addSnapshotListener
                }
                val productos = querySnapshot?.toObjects(Producto::class.java) ?: emptyList()
                _listaProductos.value = productos
            }
    }
    // Alternar estado de favorito
    fun toggleFavorito(producto: Producto) {
        viewModelScope.launch {
            val productosActuales = _productosFavoritos.value?.toMutableList() ?: mutableListOf()
            if (productosActuales.contains(producto.product)) {
                productosActuales.remove(producto.product)
            } else {
                productosActuales.add(producto.product)
            }
            _productosFavoritos.value = productosActuales // Actualizamos el LiveData
        }
    }

    fun toggleCarrito(producto: Producto) {
        viewModelScope.launch {
            val productosActuales = _productosEnCarrito.value?.toMutableList() ?: mutableListOf()
            val enCarrito = productosActuales.contains(producto.product)
            if (enCarrito) {
                productosActuales.remove(producto.product)
            } else {
                productosActuales.add(producto.product)
            }
            _productosEnCarrito.value = productosActuales
            productoDataStore.guardarEnCarrito(producto, !enCarrito)
        }
    }
    fun cargarProductosCarrito() {
        viewModelScope.launch {
            productoDataStore.obtenerCarrito().collect { carrito ->
                _productosEnCarrito.value = carrito.toList()
            }
        }
    }
    fun aumentarCantidadProducto(producto: Producto) {
        viewModelScope.launch {
            productoDataStore.aumentarCantidad(producto)
        }
    }

    fun disminuirCantidadProducto(producto: Producto) {
        viewModelScope.launch {
            productoDataStore.disminuirCantidad(producto)
        }
    }

    fun obtenerCantidadProducto(producto: Producto): LiveData<Int> {
        return productoDataStore.obtenerCantidadProducto(producto).asLiveData()
    }
}




