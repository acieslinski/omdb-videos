package com.acieslinski.videos

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.acieslinski.videos.databinding.ActivityMainBinding
import com.acieslinski.videos.featues.videos.list.ui.VideosFragment
import com.acieslinski.videos.features.videos.details.ui.VideoDetailsScreen
import com.acieslinski.videos.ui.theme.VideosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AndroidViewBinding(ActivityMainBinding::inflate) {
                        root.findViewById<ComposeView?>(R.id.composeView)?.setContent {
                            VideoDetailsScreen()
                        }
                    }
                }
            }
        }
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val videosFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? VideosFragment
        videosFragment?.apply {
            onVideoItemClickListener = { _ ->
                val isPortrait =
                    resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                if (isPortrait) {
                    val intent = Intent(context, VideoDetailsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
