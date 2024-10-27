package com.acieslinski.videos

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.acieslinski.videos.features.videos.details.ui.VideoDetailsScreen
import com.acieslinski.videos.ui.theme.VideosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoDetailsScreen()
                }
            }
        }
    }
}