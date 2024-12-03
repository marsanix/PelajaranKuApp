package com.marsanix.pelajarankuapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MateriEntity::class, MateriSettingEntity::class], version = 1, exportSchema = false)
abstract class MateriDatabase: RoomDatabase() {

    abstract fun materiDao(): MateriDao

    companion object {
        private var INSTANCE: MateriDatabase? = null

        fun getInstance(context: Context): MateriDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                MateriDatabase::class.java,
                "db_materi"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            INSTANCE = instance
            instance
        }
    }

}