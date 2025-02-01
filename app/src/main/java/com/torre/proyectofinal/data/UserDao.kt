package com.torre.proyectofinal.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// El uso de la anotación @Dao indica que esta interfaz es un "Data Access Object" (DAO).
// Un DAO es responsable de acceder y manipular los datos de la base de datos (por ejemplo, consultas, inserciones, actualizaciones y eliminaciones).
@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("UPDATE user SET accessCount = accessCount + 1 WHERE email = :email")
    suspend fun incrementAccessCount(email: String)

    @Query("UPDATE user SET accessCount = :newCount WHERE email = :email")
    suspend fun updateAccessCount(email: String, newCount: Int)

    @Query("UPDATE user SET registrationDate = :newDate WHERE email = :email")
    suspend fun updateUserRegistrationDate(email: String, newDate: String)

    @Update
    suspend fun update(user: User)

    @Query("UPDATE user SET previousAccessCount = accessCount, accessCount = accessCount + 1 WHERE email = :email")
    suspend fun updateAccessCounts(email: String)

    @Query("UPDATE user SET accessCount = :newCount WHERE email = :email")
    suspend fun setAccessCount(email: String, newCount: Int)
}