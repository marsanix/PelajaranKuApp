package com.marsanix.pelajarankuapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materi")
data class MateriEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val materiId: Int? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "icon")
    val icon: Int,

    @ColumnInfo(name = "slug")
    val slug: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "youtube")
    val youtube: String,

    @ColumnInfo(name = "is_opened")
    val isOpened: Boolean = false
)

@Entity(tableName = "settings")
data class MateriSettingEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "value")
    val value: String,

)