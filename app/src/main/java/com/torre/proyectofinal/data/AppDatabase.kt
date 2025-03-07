package com.torre.proyectofinal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define la base de datos con la entidad User y la versión
@Database(entities = [User::class], version = 2)  // Aumenta el número de versión
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Elimina la base de datos manualmente para evitar problemas con versiones antiguas
           // context.deleteDatabase("user_database")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()  // Esta línea es importante si no necesitas migraciones específicas
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
