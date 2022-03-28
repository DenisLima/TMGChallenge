package com.djv.tmgchallenge.ui

import com.djv.tmgchallenge.data.model.Game

object GameMock {

    val FAKE_GAME = Game(
        id = 0,
        mainPlayer = 0,
        mainScore = 0,
        secondPlayer = 0,
        secondScore = 0,
        dateRegister = "00/00/0000"
    )

    val FAKE_DIFERENT_GAME = Game(
        id = 0,
        mainPlayer = 0,
        mainScore = 0,
        secondPlayer = 1,
        secondScore = 0,
        dateRegister = "00/00/0000"
    )
}