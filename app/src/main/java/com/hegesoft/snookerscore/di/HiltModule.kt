package com.hegesoft.snookerscore.di

import android.content.Context
import androidx.room.Room
import com.hegesoft.snookerscore.data.BreakDatabase
import com.hegesoft.snookerscore.data.BreakRepositoryImpl
import com.hegesoft.snookerscore.data.SharedPref
import com.hegesoft.snookerscore.domain.BreakRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BreakDatabase {
        return Room.databaseBuilder(
            context,
            BreakDatabase::class.java,
            "break_table"
        ).build()
    }

    @Singleton
    @Provides
    fun provideBreakRepository(breakDatabase: BreakDatabase): BreakRepository {
        return BreakRepositoryImpl(breakDatabase)
    }

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPref {
        return SharedPref(context)
    }
}