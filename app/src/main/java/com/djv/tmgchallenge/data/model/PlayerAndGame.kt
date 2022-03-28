package com.djv.tmgchallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerAndGame(
    @PrimaryKey val idGame: Int,
    val mainName: String,
    val mainScore: Int,
    val secondName: String,
    val secondScore: Int,
    val dateRegister: String
)
