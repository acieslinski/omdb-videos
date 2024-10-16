package com.acieslinski.videos.data.videos.datasources.local

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val videoDao: VideoDao,
) : LocalDataSource {
    override suspend fun saveVideos(videos: List<LocalVideo>) {
        videoDao.insertVideos(videos)
    }

    override suspend fun searchVideos(searchQuery: String): List<LocalVideo> {
        return videoDao.searchVideosByTitle(searchQuery)
    }
}