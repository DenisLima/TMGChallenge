package com.djv.tmgchallenge.ui

import com.djv.tmgchallenge.data.model.PlayerAndGame

object PlayerAndGameMock {

    val FAKE_PLAYERANDGAME = listOf(
        PlayerAndGame(
            idGame = 0,
            mainName = "fakeName",
            mainScore = 0,
            secondName = "fakeSecond",
            secondScore = 0,
            dateRegister = "00/00/0000"
        )
    )

    val FAKE_PLAYERANDGAME_SIMPLE =
        PlayerAndGame(
            idGame = 0,
            mainName = "fakeName",
            mainScore = 0,
            secondName = "fakeSecond",
            secondScore = 0,
            dateRegister = "00/00/0000"
        )
}