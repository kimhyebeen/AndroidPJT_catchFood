package com.andpjt.catchfood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class Food(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        var food: String,
        var prefer: Int
)