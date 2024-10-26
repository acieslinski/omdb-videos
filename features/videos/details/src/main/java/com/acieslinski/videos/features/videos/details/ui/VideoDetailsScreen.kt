package com.acieslinski.videos.features.videos.details.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acieslinski.videos.features.videos.details.viewmodel.VideoDetailsViewModel
import com.acieslinski.videos.features.videos.details.viewmodel.models.VideoDetailsUiModel
import com.acieslinski.videos.features.videos.details.viewmodel.models.VideoDetailsUiState

@Composable
fun VideoDetailsScreen(
    viewModel: VideoDetailsViewModel = viewModel()
) {
    val uiState = viewModel.videoDetailsUiState.collectAsStateWithLifecycle()

    VideoDetailsContent(uiState.value)
}

@Composable
fun VideoDetailsContent(uiState: VideoDetailsUiState) {
    when (uiState) {
        is VideoDetailsUiState.Loading -> LoadingView()
        is VideoDetailsUiState.Empty -> EmptyDetailsView()
        is VideoDetailsUiState.Success -> VideoDetailsContent(uiState.videoDetailsUiModel)
        is VideoDetailsUiState.Error -> ErrorView(uiState.message)
        is VideoDetailsUiState.NotFound -> NotFoundView()
    }
}

@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyDetailsView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "No details available",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun VideoDetailsContent(videoDetailsUiModel: VideoDetailsUiModel) = with(videoDetailsUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Title: $title",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Summary: $plotSummary",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Released: $releaseYear",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ErrorView(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Error: $message",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun NotFoundView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Video not found (404)",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview(showBackground = true)
@Composable
fun VideoDetailsPreview() {
    VideoDetailsContent(
        uiState = VideoDetailsUiState.Success(
            videoDetailsUiModel = VideoDetailsUiModel(
                title = "Sample Video",
                plotSummary = "This is a sample plot summary for the video.",
                releaseYear = "2023"
            )
        )
    )
}
