package com.torre.proyectofinal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// El uso de la anotación @Dao indica que esta interfaz es un "Data Access Object" (DAO).
// Un DAO es responsable de acceder y manipular los datos de la base de datos (por ejemplo, consultas, inserciones, actualizaciones y eliminaciones).
@Dao
interface UserDao {

    // La anotación @Insert se utiliza para indicar que este método es responsable de insertar un objeto User en la base de datos.
    // La palabra clave 'suspend' permite que este método sea ejecutado de forma asincrónica utilizando coroutines.
    @Insert
    suspend fun insert(user: User)

    // La anotación @Query se usa para definir una consulta SQL que se ejecutará contra la base de datos.
    // En este caso, la consulta "SELECT * FROM user" obtiene todos los registros de la tabla "user".
    // Al ser un método suspend, también será ejecutado de forma asincrónica.
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User> // Devuelve una lista de usuarios (objetos User)
}
