package com.acieslinski.videos.data.videos.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo

@Database(entities = [LocalVideo::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            "video_database"
        )
            .build()
    }
}
