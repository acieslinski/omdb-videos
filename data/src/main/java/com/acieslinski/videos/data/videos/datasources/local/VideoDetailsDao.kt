package com.acieslinski.videos.data.videos.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideoDetails

@Dao
interface VideoDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoDetails(localVideoDetails: LocalVideoDetails)

    @Query("SELECT * FROM 'video_details' WHERE videoId = :videoId LIMIT 1")
    suspend fun getVideoDetailsById(videoId: String): LocalVideoDetails?
}
