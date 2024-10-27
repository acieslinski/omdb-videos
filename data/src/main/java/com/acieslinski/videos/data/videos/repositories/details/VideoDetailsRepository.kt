package com.acieslinski.videos.data.videos.repositories.details

import com.acieslinski.videos.data.videos.datasources.local.LocalDataSource
import com.acieslinski.videos.data.videos.datasources.remote.RemoteVideoDataSource
import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideoDetails
import com.acieslinski.videos.data.videos.repositories.details.mappers.ExceptionMapper
import com.acieslinski.videos.data.videos.repositories.details.mappers.LocalVideoDetailsMapper
import com.acieslinski.videos.data.videos.repositories.details.mappers.RemoteVideoDetailsMapper
import com.acieslinski.videos.domain.videos.details.VideoDetailsProvider
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoDetailsRepository @Inject constructor(
    private val remoteDataSource: RemoteVideoDataSource,
    private val localDataSource: LocalDataSource,
    private val exceptionMapper: ExceptionMapper,
    private val remoteVideoDetailsMapper: RemoteVideoDetailsMapper,
    private val localVideoDetailsMapper: LocalVideoDetailsMapper,
) : VideoDetailsProvider {

    override fun getVideoDetails(videoId: String): Flow<VideoDetailsResult> = flow {
        coroutineScope {
            val localFetch = async { fetchLocalVideosDetails(videoId) }
            val remoteFetch = async { fetchRemoteVideoDetails(videoId) }
            val localResult = localFetch.await()
            if (localResult.isSuccessful) {
                emit(localResult)
            }
            val remoteResult = remoteFetch.await()
            if (localResult.isSuccessful.not() || remoteResult.isSuccessful) {
                emit(remoteResult)
            }
        }
    }

    private suspend fun fetchLocalVideosDetails(videoId: String): VideoDetailsResult = runCatching {
        localDataSource.getVideoDetails(videoId)
            .let { localVideoDetailsMapper.mapToDomain(it) }
    }.getOrElse { exceptionMapper.mapToDomain(it) }

    private suspend fun fetchRemoteVideoDetails(videoId: String): VideoDetailsResult =
        runCatching {
            remoteDataSource.getVideoDetails(videoId)
                .also { cacheVideoDetails(it) }
                .let {
                    remoteVideoDetailsMapper.mapToDomain(it)
                }
        }.getOrElse { exceptionMapper.mapToDomain(it) }

    private suspend fun cacheVideoDetails(remoteVideoDetails: RemoteVideoDetails) {
        remoteVideoDetailsMapper.mapToLocal(remoteVideoDetails)
            .let { localDataSource.insertVideoDetails(it) }
    }
}