package com.djv.tmgchallenge.ui

import com.djv.tmgchallenge.data.model.Player

object PlayersMock {

    val LIST_PLAYERS_MOCK = listOf(
        Player(
            id = 0,
            name = "fakeName"
        )
    )

    val LIST_PLAYERS_EMPTY = listOf<Player>()

    val FAKE_PLAYER = Player(
        id = 0,
        name = "fakeName"
    )
}