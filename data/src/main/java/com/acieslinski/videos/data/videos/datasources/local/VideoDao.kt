package com.acieslinski.videos.data.videos.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<LocalVideo>)

    @Query("SELECT * FROM 'video' WHERE title LIKE '%' || :title || '%'")
    suspend fun searchVideosByTitle(title: String): List<LocalVideo>
}
