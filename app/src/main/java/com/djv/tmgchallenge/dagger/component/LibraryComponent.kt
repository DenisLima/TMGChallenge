package com.djv.tmgchallenge.dagger.component

import com.djv.tmgchallenge.dagger.module.GameModule
import com.djv.tmgchallenge.dagger.module.RoomDatabaseModule
import com.djv.tmgchallenge.ui.home.HomeFragment
import com.djv.tmgchallenge.ui.matches.MatchInsertFragment
import com.djv.tmgchallenge.ui.matches.MatchFragment
import com.djv.tmgchallenge.ui.players.PlayerUpdateFragment
import com.djv.tmgchallenge.ui.players.PlayerFragment
import com.djv.tmgchallenge.ui.players.PlayerInsertFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDatabaseModule::class, GameModule::class])
interface LibraryComponent {
    fun inject(playerFragment: PlayerFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(matchFragment: MatchFragment)
    fun inject(matchInsertFragment: MatchInsertFragment)
    fun inject(playerInsertFragment: PlayerInsertFragment)
    fun inject(playerUpdateFragment: PlayerUpdateFragment)
}