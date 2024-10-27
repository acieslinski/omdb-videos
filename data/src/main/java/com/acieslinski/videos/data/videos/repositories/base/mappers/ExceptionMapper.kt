package com.acieslinski.videos.data.videos.repositories.base.mappers

import com.acieslinski.videos.domain.videos.search.models.VideosResult
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import javax.inject.Inject

class ExceptionMapper @Inject constructor() {
    fun mapToDomain(exception: Throwable): VideosResult = when (exception) {
        // NOTE: more exceptions can be mapped
        is ServerResponseException -> VideosResult.ServerFailure
        is IOException -> VideosResult.NetworkFailure
        else -> VideosResult.UnknownFailure("${exception.message}")
    }
}