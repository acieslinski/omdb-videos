package com.acieslinski.videos.featues.videos.list.ui

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.acieslinski.videos.domain.videos.search.VideoSearcher
import com.acieslinski.videos.domain.videos.selection.VideoSelector
import io.mockk.mockk

@Module
@InstallIn(SingletonComponent::class)
object VideosTestModule {
    val videoSearcherMock = mockk<VideoSearcher>()
    val videoSelectorMock = mockk<VideoSelector>()

    @Provides
    fun provideVideoSearcher(): VideoSearcher = videoSearcherMock

    @Provides
    fun provideVideoSelector(): VideoSelector = videoSelectorMock
}
