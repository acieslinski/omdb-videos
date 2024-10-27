package com.acieslinski.videos.featues.videos.list.viewmodels.models

sealed class VideosFailureType {
    data object ServerFailure : VideosFailureType()
    data object NetworkFailure : VideosFailureType()
    data object UnknownFailure : VideosFailureType()
}