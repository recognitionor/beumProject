package com.kal.beum.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kal.beum.community.data.network.MockCommunityDataSource
import com.kal.beum.community.data.network.RemoteCommunityDataSource
import com.kal.beum.community.data.repository.DefaultCommunityRepository
import com.kal.beum.community.domain.CommunityRepository
import com.kal.beum.community.presentation.CommunityViewModel
import com.kal.beum.community.presentation.ContentDetailViewModel
import com.kal.beum.content.data.network.MockContentDataSource
import com.kal.beum.content.data.network.RemoteContentDataSource
import com.kal.beum.content.data.repository.DefaultContentDetailRepository
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.data.HttpClientFactory
import com.kal.beum.core.data.database.DatabaseFactory
import com.kal.beum.home.data.network.MockHomeDataSource
import com.kal.beum.home.data.network.RemoteHomeDataSource
import com.kal.beum.home.data.repository.DefaultHomeRepository
import com.kal.beum.home.domain.HomeRepository
import com.kal.beum.home.presentation.HomeViewModel
import com.kal.beum.main.data.DefaultAppRepository
import com.kal.beum.main.data.database.AppDatabase
import com.kal.beum.main.data.database.MIGRATION_1_2
import com.kal.beum.main.data.network.MockLoginDataSource
import com.kal.beum.main.data.network.RemoteLoginDataSource
import com.kal.beum.main.domain.AppRepository
import com.kal.beum.main.presentation.MainViewModel
import com.kal.beum.write.data.network.MockWriteCategoryDataSource
import com.kal.beum.write.data.network.MockWriteDataSource
import com.kal.beum.write.data.network.RemoteWriteCategoryDataSource
import com.kal.beum.write.data.network.RemoteWriteDataSource
import com.kal.beum.write.data.repository.DefaultWriteCategoryRepository
import com.kal.beum.write.data.repository.DefaultWritingRepository
import com.kal.beum.write.domain.WritingCategoryRepository
import com.kal.beum.write.domain.WritingRepository
import com.kal.beum.write.presentation.WritingViewModel
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
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver())
            .addMigrations(MIGRATION_1_2).build()
//            .fallbackToDestructiveMigration(true).build()
    }
    singleOf(::MockHomeDataSource).bind<RemoteHomeDataSource>()
    singleOf(::MockLoginDataSource).bind<RemoteLoginDataSource>()
    singleOf(::MockCommunityDataSource).bind<RemoteCommunityDataSource>()
    singleOf(::MockWriteCategoryDataSource).bind<RemoteWriteCategoryDataSource>()
    singleOf(::MockWriteDataSource).bind<RemoteWriteDataSource>()
    singleOf(::MockContentDataSource).bind<RemoteContentDataSource>()

    singleOf(::DefaultAppRepository).bind<AppRepository>()
    singleOf(::DefaultHomeRepository).bind<HomeRepository>()
    singleOf(::DefaultCommunityRepository).bind<CommunityRepository>()
    singleOf(::DefaultWriteCategoryRepository).bind<WritingCategoryRepository>()
    singleOf(::DefaultWritingRepository).bind<WritingRepository>()
    singleOf(::DefaultContentDetailRepository).bind<ContentsRepository>()

    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::CommunityViewModel)
    viewModelOf(::ContentDetailViewModel)
    viewModelOf(::WritingViewModel)


    single { get<AppDatabase>().appDao }


//    viewModelOf(::Select)


}