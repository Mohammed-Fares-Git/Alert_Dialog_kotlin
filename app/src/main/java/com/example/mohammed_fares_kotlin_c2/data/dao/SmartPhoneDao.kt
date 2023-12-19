package com.example.mohammed_fares_kotlin_c2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mohammed_fares_kotlin_c2.model.SmartPhone
import kotlinx.coroutines.flow.Flow

@Dao
interface SmartPhoneDao {
    @Insert
    suspend fun insertPhone(smartPhone: SmartPhone): Long

    @Update
    suspend fun updatePhone(smartPhone: SmartPhone): Int

    @Delete
    suspend fun deletePhone(smartPhone: SmartPhone): Int

    @Query("SELECT * FROM smartphones")
    fun getAll(): Flow<List<SmartPhone>>
}