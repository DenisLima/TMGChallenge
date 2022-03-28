package com.djv.tmgchallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val mainPlayer: Int,
    val mainScore: Int,
    val secondPlayer: Int,
    val secondScore: Int,
    val dateRegister: String
)
