package com.kal.beum.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kal.beum.community.data.network.KtorCommunityDataSource
import com.kal.beum.community.data.network.RemoteCommunityDataSource
import com.kal.beum.community.data.repository.DefaultCommunityRepository
import com.kal.beum.community.domain.CommunityRepository
import com.kal.beum.community.presentation.CommunityViewModel
import com.kal.beum.content.data.network.KtorContentDataSource
import com.kal.beum.content.data.network.RemoteContentDataSource
import com.kal.beum.content.data.repository.DefaultContentDetailRepository
import com.kal.beum.content.data.repository.DefaultReplyRepository
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.content.domain.ReplyRepository
import com.kal.beum.content.presentation.ReplyDetailViewModel
import com.kal.beum.core.data.HttpClientFactory
import com.kal.beum.core.data.database.DatabaseFactory
import com.kal.beum.getPlatformContext
import com.kal.beum.home.data.network.KtorRemoteHomeDataSource
import com.kal.beum.home.data.network.MockHomeDataSource
import com.kal.beum.home.data.network.RemoteHomeDataSource
import com.kal.beum.home.data.repository.DefaultHomeRepository
import com.kal.beum.home.domain.HomeRepository
import com.kal.beum.home.presentation.HomeViewModel
import com.kal.beum.level.data.network.KtorRankerUserInfoDataSource
import com.kal.beum.level.data.network.MockRankerUserInfoDataSource
import com.kal.beum.level.data.network.RemoteRankerUserInfoDataSource
import com.kal.beum.level.data.repository.DefaultRankerUserInfoRepository
import com.kal.beum.level.domain.RankerUserInfoRepository
import com.kal.beum.level.presentaion.RankingViewModel
import com.kal.beum.login.data.client.KaKaoLoginClient
import com.kal.beum.login.data.client.NaverLoginClient
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.main.data.DefaultAppRepository
import com.kal.beum.main.data.database.AppDatabase
import com.kal.beum.main.data.database.MIGRATION_3_4
import com.kal.beum.main.data.network.RemoteLoginDataSource
import com.kal.beum.main.data.network.SdkLoginDataSource
import com.kal.beum.main.domain.AppRepository
import com.kal.beum.main.domain.SocialType
import com.kal.beum.main.presentation.MainViewModel
import com.kal.beum.myinfo.data.network.KtorMyInfoDataSource
import com.kal.beum.myinfo.data.network.RemoteMyInfoDataSource
import com.kal.beum.myinfo.data.repository.DefaultMyInfoRepository
import com.kal.beum.myinfo.domain.MyInfoRepository
import com.kal.beum.myinfo.presentation.MyInfoViewModel
import com.kal.beum.notice.data.DefaultNoticeRepository
import com.kal.beum.notice.data.network.KtorRemoteNoticeDataSource
import com.kal.beum.notice.data.network.RemoteNoticeDataSource
import com.kal.beum.notice.domain.NoticeRepository
import com.kal.beum.notice.presentaion.NoticeViewModel
import com.kal.beum.write.data.network.KtorWriteDataSource
import com.kal.beum.write.data.network.MockWriteCategoryDataSource
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
            .addMigrations(MIGRATION_3_4).build()
    }

    single<Map<Int, LoginClient>> {
        mapOf(
            SocialType.KAKAO_CODE to KaKaoLoginClient(getPlatformContext()),
            SocialType.NAVER_CODE to NaverLoginClient(getPlatformContext())
        )
    }

    singleOf(::SdkLoginDataSource).bind<RemoteLoginDataSource>()
    singleOf(::KtorWriteDataSource).bind<RemoteWriteDataSource>()
    singleOf(::KtorContentDataSource).bind<RemoteContentDataSource>()

    singleOf(::KtorRemoteHomeDataSource).bind<RemoteHomeDataSource>()
//    singleOf(::MockHomeDataSource).bind<RemoteHomeDataSource>()
    singleOf(::KtorCommunityDataSource).bind<RemoteCommunityDataSource>()
    singleOf(::MockWriteCategoryDataSource).bind<RemoteWriteCategoryDataSource>()

    singleOf(::MockRankerUserInfoDataSource).bind<RemoteRankerUserInfoDataSource>()
    singleOf(::KtorMyInfoDataSource).bind<RemoteMyInfoDataSource>()
//    singleOf(::MockRemoteNoticeDataSource).bind<RemoteNoticeDataSource>()
    singleOf(::KtorRemoteNoticeDataSource).bind<RemoteNoticeDataSource>()


    singleOf(::DefaultAppRepository).bind<AppRepository>()
    singleOf(::DefaultHomeRepository).bind<HomeRepository>()
    singleOf(::DefaultCommunityRepository).bind<CommunityRepository>()
    singleOf(::DefaultWriteCategoryRepository).bind<WritingCategoryRepository>()
    singleOf(::DefaultWritingRepository).bind<WritingRepository>()
    singleOf(::DefaultContentDetailRepository).bind<ContentsRepository>()
    singleOf(::DefaultRankerUserInfoRepository).bind<RankerUserInfoRepository>()
    singleOf(::DefaultMyInfoRepository).bind<MyInfoRepository>()
    singleOf(::DefaultNoticeRepository).bind<NoticeRepository>()
    singleOf(::DefaultReplyRepository).bind<ReplyRepository>()

    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::CommunityViewModel)
    viewModelOf(::WritingViewModel)
    viewModelOf(::RankingViewModel)
    viewModelOf(::MyInfoViewModel)
    viewModelOf(::NoticeViewModel)
    viewModelOf(::ReplyDetailViewModel)

    single { get<AppDatabase>().appDao }
    single { get<AppDatabase>().writingDao }


//    viewModelOf(::Select)


}