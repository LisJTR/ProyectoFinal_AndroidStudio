package com.torre.proyectofinal

import androidx.room.Entity
import androidx.room.PrimaryKey

// La anotación @Entity indica que esta clase es una entidad de base de datos.
// Una entidad representa una tabla en la base de datos.
// En este caso, la clase 'User' se convertirá en una tabla llamada "user" en la base de datos.
@Entity(tableName = "user")
data class User(
    // La anotación @PrimaryKey indica que esta propiedad es la clave primaria de la tabla.
    // El parámetro 'autoGenerate = true' significa que Room generará automáticamente el valor de 'id' cuando se inserte un nuevo registro.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // La propiedad 'name' es una columna de la tabla que almacenará el nombre del usuario.
    val name: String,

    // La propiedad 'email' es otra columna de la tabla que almacenará el correo electrónico del usuario.
    val email: String
)
