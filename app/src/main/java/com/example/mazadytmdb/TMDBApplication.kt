package com.example.mazadytmdb

import android.app.Application
import com.example.mazadytmdb.core.dependecies.databaseModule
import com.example.mazadytmdb.core.dependecies.networkModule
import com.example.mazadytmdb.core.dependecies.repositoryModules
import com.example.mazadytmdb.core.dependecies.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TMDBApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TMDBApplication)

            modules(
                databaseModule,
                networkModule,
                repositoryModules,
                viewModelModules
            )
        }
    }
}