package com.acieslinski.videos.data.videos.datasources.local

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideoDetails
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val videoDao: VideoDao,
    private val videoDetailsDao: VideoDetailsDao,
) : LocalDataSource {
    override suspend fun saveVideos(videos: List<LocalVideo>) {
        videoDao.insertVideos(videos)
    }

    override suspend fun searchVideos(searchQuery: String): List<LocalVideo> {
        return videoDao.searchVideosByTitle(searchQuery)
    }

    override suspend fun insertVideoDetails(localVideoDetails: LocalVideoDetails) {
        videoDetailsDao.insertVideoDetails(localVideoDetails)
    }

    override suspend fun getVideoDetails(videoId: String): LocalVideoDetails? {
        return videoDetailsDao.getVideoDetailsById(videoId)
    }
}