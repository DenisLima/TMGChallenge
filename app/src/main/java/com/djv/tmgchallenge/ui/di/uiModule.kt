package com.djv.tmgchallenge.ui.di

import com.djv.tmgchallenge.ui.players.PlayerViewModel
import com.djv.tmgchallenge.ui.home.HomeViewModel
import com.djv.tmgchallenge.ui.matches.MatchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel { HomeViewModel(get()) }
    viewModel { PlayerViewModel(get()) }
    viewModel { MatchViewModel(get()) }
}