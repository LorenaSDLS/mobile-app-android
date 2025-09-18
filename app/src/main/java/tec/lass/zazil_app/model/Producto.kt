package tec.lass.zazil_app.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
/**
 * Clase que representa un producto con un nombre, categoría, descripción, porcentaje de descuento, imagen, precio, y stock.
 */
data class Producto(
    @PropertyName("product") val product: String = "",       // Nombre del producto
    @PropertyName("category") val category: String = "",     // Categoría
    @PropertyName("description") val description: String = "", // Descripción
    @PropertyName("discountPercent") val discountPercent: String = "",
    @PropertyName("img") val img: String = "",               // URL de la imagen
    @PropertyName("price") val price: String = "",
    @PropertyName("stock") val stock: String = "",
    @PropertyName("timeStamp") val timeStamp: com.google.firebase.Timestamp? = null,  // Agrega este campo si es necesario
    @Exclude var favorito: Boolean = false,  // Excluido de Firestore
    @Exclude var carrito: Boolean = false    // Excluido de Firestore

)
