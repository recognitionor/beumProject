package com.kal.beum.di

import com.kal.beum.di.platformModule
import com.kal.beum.di.sharedModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModules, platformModule)
    }
}