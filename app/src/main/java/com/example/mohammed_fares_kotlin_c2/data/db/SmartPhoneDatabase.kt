package com.example.mohammed_fares_kotlin_c2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mohammed_fares_kotlin_c2.data.dao.SmartPhoneDao
import com.example.mohammed_fares_kotlin_c2.model.SmartPhone

@Database(entities = [SmartPhone::class], version = 1, exportSchema = false)
abstract class SmartPhoneDatabase: RoomDatabase() {
    abstract fun smartPhoneDao(): SmartPhoneDao
}