package com.example.subs_inter.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "income")
    var title: String? = null,

    @ColumnInfo(name = "category")
    var description: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable