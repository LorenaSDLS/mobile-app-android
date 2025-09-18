package tec.lass.zazil_app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tec.lass.zazil_app.model.Producto

class ProductoDataStore private constructor(private val context: Context) {

    // Claves para DataStore
    private val FAVORITOS_KEY = stringSetPreferencesKey("favoritos")
    private val CARRITO_KEY = stringSetPreferencesKey("carrito")


    companion object {
        @Volatile
        private var INSTANCE: ProductoDataStore? = null

        fun getInstance(context: Context): ProductoDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = ProductoDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }

    // Acceso al DataStore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "productos_datastore")

    // Obtener lista de productos en carrito
    fun obtenerCarrito(): Flow<Set<String>> {
        return context.dataStore.data
            .map { preferences ->
                preferences[CARRITO_KEY] ?: emptySet()
            }
    }

    fun obtenerFavoritos(): Flow<Set<String>> {
        return context.dataStore.data
            .map { preferences ->
                preferences[FAVORITOS_KEY] ?: emptySet()
            }
    }

    // Generar clave dinámica para la cantidad del producto
    private fun getCantidadKey(producto: Producto): Preferences.Key<Int> {
        return intPreferencesKey("cantidad_${producto.product}")  // Usa el identificador único del producto
    }


    // Guardar o eliminar producto de favoritos
    suspend fun guardarFavorito(producto: Producto, esFavorito: Boolean) {
        context.dataStore.edit { preferences ->
            val favoritosActuales = preferences[FAVORITOS_KEY]?.toMutableSet() ?: mutableSetOf()
            if (esFavorito) {
                favoritosActuales.add(producto.product)
            } else {
                favoritosActuales.remove(producto.product)
            }
            preferences[FAVORITOS_KEY] = favoritosActuales
        }
    }

    // Guardar/Eliminar producto en carrito
    suspend fun guardarEnCarrito(producto: Producto, enCarrito: Boolean) {
        context.dataStore.edit { preferences ->
            val carrito = preferences[CARRITO_KEY]?.toMutableSet() ?: mutableSetOf()
            if (enCarrito) {
                carrito.add(producto.product)
            } else {
                carrito.remove(producto.product)
            }
            preferences[CARRITO_KEY] = carrito
        }
    }

    suspend fun aumentarCantidad(producto: Producto) {
        context.dataStore.edit { preferences ->
            val currentCantidad = preferences[intPreferencesKey(producto.product)] ?: 1
            preferences[intPreferencesKey(producto.product)] = currentCantidad + 1
        }
    }

    suspend fun disminuirCantidad(producto: Producto) {
        context.dataStore.edit { preferences ->
            val currentCantidad = preferences[intPreferencesKey(producto.product)] ?: 1
            if (currentCantidad > 1) {
                preferences[intPreferencesKey(producto.product)] = currentCantidad - 1
            }
        }
    }

    // Obtener la cantidad actual de un producto
    fun obtenerCantidadProducto(producto: Producto): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            val cantidadKey = getCantidadKey(producto)
            preferences[cantidadKey] ?: 1  // Devuelve 1 si no hay valor almacenado
        }
    }
}


