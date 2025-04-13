package com.example.mazadytmdb.core.dependecies

import androidx.room.Room
import com.example.mazadytmdb.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movies_database.db"
        ).build()
    }
    single { get<AppDatabase>().moviesDao() }
}