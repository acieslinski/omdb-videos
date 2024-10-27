package com.acieslinski.videos.data.videos.repositories.details.mappers

import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import javax.inject.Inject

class ExceptionMapper @Inject constructor() {
    fun mapToDomain(exception: Throwable): VideoDetailsResult = when (exception) {
        // NOTE: more exceptions can be mapped
        is ServerResponseException -> {
            if (exception.response.status.value == 404) {
                VideoDetailsResult.NotFoundFailure
            } else {
                VideoDetailsResult.ServerFailure
            }
        }
        is IOException -> VideoDetailsResult.NetworkFailure
        else -> VideoDetailsResult.UnknownFailure("${exception.message}")
    }
}