package com.acieslinski.videos.data.videos.datasources.local.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "video",
    indices = [Index(value = ["title"])]
)
data class LocalVideo(
    @PrimaryKey
    val videoId: String,
    val posterUrl: String,
    val title: String,
    val year: Int
)
