package com.andpjt.catchfood.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getInstance(context: Context): FoodDatabase {
            INSTANCE?.let { return it } ?: return initInstance(context)
        }

        private fun initInstance(context: Context): FoodDatabase {
            synchronized(FoodDatabase::class) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, FoodDatabase::class.java, "foods")
                        .fallbackToDestructiveMigration()
                        .build()
                return INSTANCE as FoodDatabase
            }
        }
    }
}