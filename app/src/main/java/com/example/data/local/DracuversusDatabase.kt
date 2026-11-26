package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.FigureEntity

@Database(
    entities = [FigureEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DracuversusDatabase : RoomDatabase() {

    abstract fun figureDao(): FigureDao

    companion object {
        @Volatile
        private var INSTANCE: DracuversusDatabase? = null

        fun getInstance(context: Context): DracuversusDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DracuversusDatabase::class.java,
                    "dracuversus_museum.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
