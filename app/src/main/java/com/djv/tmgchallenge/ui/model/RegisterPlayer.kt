package com.djv.tmgchallenge.ui.model

import com.djv.tmgchallenge.data.model.Player

data class RegisterPlayer(
    val player: Player? = Player(),
    val newName: String = "",
    val isRegister: Boolean = false
)
