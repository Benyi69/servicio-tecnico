package com.serviciotecnico.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ServiceTicketEntity::class, ArregloEntity::class], // ArregloEntity AÑADIDO
    version = 7, // Versión incrementada
    exportSchema = false // Warning solucionado
)
abstract class BaseDatosApp : RoomDatabase() {
    abstract fun serviceDao(): ServiceDao

    companion object {
        @Volatile private var INSTANCIA: BaseDatosApp? = null

        fun obtenerInstancia(contexto: Context): BaseDatosApp {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDatosApp::class.java,
                    "ordenes_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}
