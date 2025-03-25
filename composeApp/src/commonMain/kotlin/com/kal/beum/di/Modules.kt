package com.kal.beum.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kal.beum.core.data.HttpClientFactory
import com.kal.beum.core.data.database.DatabaseFactory
import com.kal.beum.home.data.network.MockHomeDataSource
import com.kal.beum.home.data.network.RemoteHomeDataSource
import com.kal.beum.home.data.repository.DefaultHomeRepository
import com.kal.beum.home.domain.HomeRepository
import com.kal.beum.home.presentation.HomeViewModel
import com.kal.beum.main.data.DefaultAppRepository
import com.kal.beum.main.data.database.AppDatabase
import com.kal.beum.main.domain.AppRepository
import com.kal.beum.main.presentation.MainViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModules = module {
    single {
        HttpClientFactory.create(get())
    }

    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build()
    }

    singleOf(::MockHomeDataSource).bind<RemoteHomeDataSource>()
//    singleOf(::KtorRemoteHomeDataSource).bind<RemoteHomeDataSource>()

    singleOf(::DefaultHomeRepository).bind<HomeRepository>()
    singleOf(::DefaultAppRepository).bind<AppRepository>()

    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)

    single { get<AppDatabase>().appDao }


//    viewModelOf(::Select)


}