package com.acieslinski.videos.data.videos.repositories

import com.acieslinski.videos.data.videos.datasources.local.LocalDataSource
import com.acieslinski.videos.data.videos.datasources.remote.RemoteVideoDataSource
import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideo
import com.acieslinski.videos.data.videos.repositories.mappers.ExceptionMapper
import com.acieslinski.videos.data.videos.repositories.mappers.LocalVideoMapper
import com.acieslinski.videos.data.videos.repositories.mappers.RemoteVideoMapper
import com.acieslinski.videos.domain.videos.search.VideoSearcher
import com.acieslinski.videos.domain.videos.search.models.VideosResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val remoteDataSource: RemoteVideoDataSource,
    private val localDataSource: LocalDataSource,
    private val localVideoMapper: LocalVideoMapper,
    private val remoteVideoMapper: RemoteVideoMapper,
    private val exceptionMapper: ExceptionMapper,
) : VideoSearcher {
    // NOTE: fallback to local data, prioritized remote strategy used
    // different strategies could be applied like:
    // - Local-Remote Sequential Flow Pattern
    // - Offline First (with a data sync)
    // different strategies for sync
    // - Exponential backoff
    // - push notifications for sync
    // - last write wins
    // - conflict resolution
    override suspend fun getVideos(searchQuery: String): VideosResult {
        return combine(
            retrieveLocalVideos(searchQuery),
            retrieveRemoteVideos(searchQuery)
        ) { localVideoResult, remoteVideoResult ->
            if (remoteVideoResult.isSuccessful || localVideoResult.isEmpty) {
                remoteVideoResult
            } else {
                localVideoResult
            }
        }
            .first()
    }

    private fun retrieveLocalVideos(searchQuery: String): Flow<VideosResult> = flow {
        emit(
            runCatching {
                localDataSource.searchVideos(searchQuery)
                    .let { localVideoMapper.mapToDomain(it) }
            }.getOrElse { exceptionMapper.mapToDomain(it) }
        )
    }

    private fun retrieveRemoteVideos(searchQuery: String): Flow<VideosResult> = flow {
        emit(
            runCatching {
                remoteDataSource.searchVideos(searchQuery)
                    .also { cacheVideos(it) }
                    .let { remoteVideoMapper.mapToDomain(it) }
            }.getOrElse { exceptionMapper.mapToDomain(it) }
        )
    }

    private suspend fun cacheVideos(videos: List<RemoteVideo>) {
        videos.map { remoteVideoMapper.mapToLocal(it) }
            .also { localDataSource.saveVideos(it) }
    }
}