package com.acieslinski.videos.data.videos.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "video_details",
)
data class LocalVideoDetails(
    @PrimaryKey
    val videoId: String,
    val title: String,
    val plotSummary: String,
    val releaseYear: String,
)
