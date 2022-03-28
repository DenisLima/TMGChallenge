package com.djv.tmgchallenge.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "playerName") val name: String = ""
): Parcelable {
    override fun toString(): String {
        return name
    }
}
