package com.torre.proyectofinal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define la base de datos con la entidad User y la versión
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Definimos el DAO que la base de datos utilizará
    abstract fun userDao(): UserDao

    companion object {

        // Volatile asegura que las actualizaciones de la base de datos sean visibles inmediatamente a otros hilos
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método para obtener la instancia de la base de datos
        fun getDatabase(context: Context): AppDatabase {
            // Si la instancia ya existe, la retornamos
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
