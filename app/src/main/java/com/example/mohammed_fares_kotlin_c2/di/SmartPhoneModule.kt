package com.example.mohammed_fares_kotlin_c2.di

import android.content.Context
import androidx.room.Room
import com.example.mohammed_fares_kotlin_c2.data.dao.SmartPhoneDao
import com.example.mohammed_fares_kotlin_c2.data.db.SmartPhoneDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SmartPhoneModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext ctx: Context): SmartPhoneDatabase {
        return Room.databaseBuilder(
            ctx.applicationContext,
            SmartPhoneDatabase::class.java, "smartphone_database"
        ).build()
    }


    @Singleton
    @Provides
    fun provideDao(smartPhoneDatabase: SmartPhoneDatabase): SmartPhoneDao {
        return smartPhoneDatabase.smartPhoneDao()
    }




}