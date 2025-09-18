package tec.lass.zazil_app.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.mindrot.jbcrypt.BCrypt

class UserRepository {
    private val database = FirebaseDatabase.getInstance().reference.child("users")

    /**
     * Función privada hashPassword
     *
     * Genera un hash seguro de la contraseña del usuario utilizando BCrypt.
     *
     * @param password Contraseña en texto claro.
     * @return Cadena con la contraseña cifrada.
     */

    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    /**
     * Función privada hashCurp
     *
     * Genera un hash seguro del CURP del usuario utilizando BCrypt.
     *
     * @param curp CURP en texto claro.
     * @return Cadena con el CURP cifrado.
     */

    private fun hashCurp(curp: String): String {
        return BCrypt.hashpw(curp, BCrypt.gensalt())
    }


    /**
     * Función signUpUser
     *
     * Registra un nuevo usuario en la base de datos de Firebase. Verifica si el usuario ya existe antes de
     * almacenar sus datos. Las contraseñas y el CURP son cifrados antes de almacenarse.
     *
     * @param name Nombre del usuario.
     * @param phone Número de teléfono del usuario.
     * @param password Contraseña del usuario.
     * @param email Correo electrónico del usuario.
     * @param birthdate Fecha de nacimiento del usuario.
     * @param location Ubicación del usuario.
     * @param curp CURP del usuario.
     * @param onSuccess Callback que se ejecuta si el registro es exitoso.
     * @param onFailure Callback que se ejecuta si ocurre un error, pasando el mensaje de error.
     */

    fun signUpUser(
        name: String,
        phone: String,
        password: String,
        email: String,
        birthdate: String,
        location: String,
        curp: String,
        direction: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userRef = database.child(phone)

        userRef.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                onFailure("El usuario ya existe")
            } else {
                // Hash de la contraseña y CURP
                val hashedPassword = hashPassword(password)
                val hashedCurp = hashCurp(curp)

                val userData = mapOf(
                    "name" to name,
                    "phone" to phone,
                    "password" to hashedPassword,  // Contraseña encriptada
                    "email" to email,
                    "birthdate" to birthdate,
                    "location" to location,
                    "curp" to hashedCurp, // CURP encriptado
                    "direction" to direction
                )

                // Crea el usuario en Firebase Authentication
                val normalizedEmail = email.trim().lowercase()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Una vez creado en Firebase Auth, guardamos los datos en Realtime Database
                            userRef.setValue(userData).addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    onSuccess()
                                } else {
                                    onFailure(
                                        dbTask.exception?.message
                                            ?: "Error al crear cuenta en la base de datos"
                                    )
                                }
                            }
                        } else {
                            onFailure(
                                task.exception?.message
                                    ?: "Error al registrar usuario en Firebase Auth"
                            )
                        }
                    }
            }
        }.addOnFailureListener { exception ->
            onFailure(exception.message ?: "Error al verificar usuario")
        }
    }


    /**
     * Función loginUser
     *
     * Inicia sesión verificando si el número de teléfono existe y si la contraseña proporcionada coincide con
     * la contraseña cifrada almacenada en la base de datos.
     *
     * @param phone Número de teléfono del usuario.
     * @param password Contraseña del usuario en texto claro.
     * @param onSuccess Callback que se ejecuta si el inicio de sesión es exitoso.
     * @param onFailure Callback que se ejecuta si ocurre un error, pasando el mensaje de error.
     */
    fun loginUser(
        phone: String,
        password: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userRef = database.child(phone)

        database.child(phone).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val user = dataSnapshot.getValue(User::class.java)

                userRef.get().addOnSuccessListener { dataSnapshot ->
                    if (dataSnapshot.exists()) {
                        val storedHashedPassword = dataSnapshot.child("password").value as String

                        // Comprobar si la contraseña es correcta
                        if (BCrypt.checkpw(password, storedHashedPassword)) {
                            val user = User(
                                name = dataSnapshot.child("name").value as String,
                                email = dataSnapshot.child("email").value as String,
                                phone = dataSnapshot.child("phone").value as String,
                                location = dataSnapshot.child("location").value as String,
                                birthdate = dataSnapshot.child("birthdate").value as String,
                                curp = dataSnapshot.child("curp").value as String
                            )
                            onSuccess(user)
                        } else {
                            onFailure("Contraseña incorrecta")
                        }
                    } else {
                        onFailure("El usuario no existe")
                    }
                }.addOnFailureListener { exception ->
                    onFailure(exception.message ?: "Error al verificar usuario")
                }
            }
        }
    }
}

