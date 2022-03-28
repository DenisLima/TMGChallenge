package com.djv.tmgchallenge.domain.di

import com.djv.tmgchallenge.domain.GameUseCases
import com.djv.tmgchallenge.domain.GameUseCasesImpl
import org.koin.dsl.module

val domainModule = module {

    single<GameUseCases> {
        GameUseCasesImpl(get())
    }
}