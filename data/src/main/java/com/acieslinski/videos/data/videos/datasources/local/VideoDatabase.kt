package com.acieslinski.videos.data.videos.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideoDetails

@Database(entities = [LocalVideo::class, LocalVideoDetails::class], version = 2)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao

    abstract fun videoDetailsDao(): VideoDetailsDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            "video_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
