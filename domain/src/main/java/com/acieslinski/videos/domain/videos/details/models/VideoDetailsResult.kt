package com.acieslinski.videos.domain.videos.details.models

sealed class VideoDetailsResult(
    val isSuccessful: Boolean = false,
) {
    data class Success(
        val videoDetails: VideoDetails
    ) : VideoDetailsResult(isSuccessful = true)

    // NOTE: Additional error types can be specified here if required, such as:
    // - Server errors (e.g., 500 Internal Server Error)
    // - Malformed JSON or response structure issues
    // - Security-related errors (e.g., 401 Unauthorized, 403 Forbidden)
    // - Timeout or client-side errors (e.g., 400 Bad Request)
    // This can be extended further based on specific requirements.

    sealed class Failure : VideoDetailsResult()

    data object NotFoundFailure : Failure()
    data object NetworkFailure : Failure()
    data object ServerFailure : Failure()
    data class UnknownFailure(val message: String) : Failure()
}