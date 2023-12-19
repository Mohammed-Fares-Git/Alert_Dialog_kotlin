package com.example.mohammed_fares_kotlin_c2.data.repository

import com.example.mohammed_fares_kotlin_c2.data.dao.SmartPhoneDao
import com.example.mohammed_fares_kotlin_c2.model.SmartPhone
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmartPhoneRepository @Inject constructor(
    val smartPhoneDao: SmartPhoneDao
) {

    suspend fun insert(smartPhone: SmartPhone): Long {
        return smartPhoneDao.insertPhone(smartPhone)
    }

    suspend fun update(smartPhone: SmartPhone): Int {
        return smartPhoneDao.updatePhone(smartPhone)
    }

    suspend fun delete(smartPhone: SmartPhone): Int {
        return smartPhoneDao.deletePhone(smartPhone)
    }

    fun getAll(): Flow<List<SmartPhone>> = smartPhoneDao.getAll()

}