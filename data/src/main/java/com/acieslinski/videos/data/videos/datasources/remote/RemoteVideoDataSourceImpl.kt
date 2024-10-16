package com.acieslinski.videos.data.videos.datasources.remote

import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideo
import com.acieslinski.videos.data.videos.datasources.remote.models.SearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class RemoteVideoDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher
) : RemoteVideoDataSource {
    override suspend fun searchVideos(searchQuery: String): List<RemoteVideo> =
        withContext(dispatcher) {
            val movieResponse = async { searchVideos(searchQuery, MOVIE_SEARCH_TYPE) }
            val response = async { searchVideos(searchQuery, SERIES_SEARCH_TYPE) }
            movieResponse.await() + response.await()
        }

    private suspend fun searchVideos(searchQuery: String, videoSearchType: String) =
        httpClient.get("http://www.omdbapi.com/") {
            parameter("s", searchQuery)
            parameter("type", videoSearchType)
            parameter("apikey", API_KEY)
        }.body<SearchResult>().Search

    companion object {
        const val MOVIE_SEARCH_TYPE = "movie"
        const val SERIES_SEARCH_TYPE = "series"
    }
}