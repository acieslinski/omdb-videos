package com.acieslinski.videos.data

import android.content.Context
import com.acieslinski.videos.data.videos.datasources.local.LocalDataSource
import com.acieslinski.videos.data.videos.datasources.local.LocalDataSourceImpl
import com.acieslinski.videos.data.videos.datasources.local.VideoDao
import com.acieslinski.videos.data.videos.datasources.local.VideoDatabase
import com.acieslinski.videos.data.videos.datasources.remote.RemoteVideoDataSource
import com.acieslinski.videos.data.videos.datasources.remote.RemoteVideoDataSourceImpl
import com.acieslinski.videos.data.videos.repositories.VideoRepository
import com.acieslinski.videos.domain.videos.search.VideoSearcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Named("ioDispatcher")
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideLocalVideoDataSource(source: LocalDataSourceImpl): LocalDataSource = source

    @Provides
    fun provideRemoteVideoDataSource(source: RemoteVideoDataSourceImpl): RemoteVideoDataSource =
        source

    @Provides
    fun provideVideoSearcher(repository: VideoRepository): VideoSearcher = repository

    @Provides
    @Singleton
    fun provideOmdbHttpClient(): HttpClient = insecureOmdbHttpClient

    @Provides
    @Singleton
    fun provideVideoDatabase(@ApplicationContext context: Context): VideoDatabase =
        VideoDatabase.build(context)

    @Provides
    @Singleton
    fun provideVideoDao(videoDatabase: VideoDatabase): VideoDao = videoDatabase
            .videoDao()
}