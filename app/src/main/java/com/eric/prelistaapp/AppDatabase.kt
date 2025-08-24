package com.eric.prelistaapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PreLista::class, Item::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preListaDao(): PreListaDao
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pre_lista_db"
                )
                    .fallbackToDestructiveMigration() // ESSENCIAL
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


