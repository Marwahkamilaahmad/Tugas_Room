package com.example.tugasroom2

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "data_ruangan")

data class Note(
    @PrimaryKey(autoGenerate = true)

    @NonNull val id : Int = 0,

    @ColumnInfo(name = "title")
    val title : String = "",
    @ColumnInfo(name = "jumlah")
    val jumlah : String = "",
    @ColumnInfo(name = "description")
    val description : String = "",
    @ColumnInfo(name = "tanggal")
    val tanggal: String = "",

    )