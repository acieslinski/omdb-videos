package com.acieslinski.videos.domain.videos.search.models

sealed class VideosResult(
    val isSuccessful: Boolean = false,
    val isEmpty: Boolean = true
) {
    data class Success(
        val videos: List<Video>
    ) : VideosResult(isSuccessful = true, isEmpty = videos.isEmpty())

    // NOTE: Additional error types can be specified here, such as:
    // - Server errors (e.g., 500 Internal Server Error)
    // - Malformed JSON or response structure issues
    // - Security-related errors (e.g., 401 Unauthorized, 403 Forbidden)
    // - Timeout or client-side errors (e.g., 400 Bad Request)
    // This can be extended further based on specific requirements.

    sealed class Failure : VideosResult(isSuccessful = false)

    data object NetworkFailure : Failure()
    data object ServerFailure : Failure()
    data class UnknownFailure(val message: String) : Failure()
}