package com.hegesoftware.snookerscore.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hegesoftware.snookerscore.data.BreakDatabase
import com.hegesoftware.snookerscore.data.BreakRepositoryImpl
import com.hegesoftware.snookerscore.data.SharedPref
import com.hegesoftware.snookerscore.domain.BreakRepository
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