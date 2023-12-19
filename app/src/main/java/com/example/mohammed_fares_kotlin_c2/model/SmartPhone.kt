package com.example.mohammed_fares_kotlin_c2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "smartphones")
data class SmartPhone(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val price: Int,
    val image: String,
    val fingerPrint: Boolean
)
