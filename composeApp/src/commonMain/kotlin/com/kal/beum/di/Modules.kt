package com.kal.beum.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kal.beum.core.data.database.DatabaseFactory
import com.kal.beum.core.data.HttpClientFactory
import com.kal.beum.core.data.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModules = module {
    single {
        HttpClientFactory.create(get())
    }

    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build()
    }

    single { get<AppDatabase>().appDao }


//    viewModelOf(::Select)


}