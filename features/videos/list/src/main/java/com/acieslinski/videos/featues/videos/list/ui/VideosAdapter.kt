package com.acieslinski.videos.featues.videos.list.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideoUiModel
import com.acieslinski.videos.videos.databinding.FragmentVideosItemBinding

class VideosAdapter(
    private val videosList: List<VideoUiModel>
) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = FragmentVideosItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videosList[position])
    }

    override fun getItemCount(): Int = videosList.size

    inner class VideoViewHolder(private val binding: FragmentVideosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: VideoUiModel) {
            binding.titleTextView.text = video.title
            binding.releaseYearTextView.text = video.year
            binding.posterImageView.load(video.posterUrl) {
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_report_image)
                listener(
                    onError = { _, result ->
                        Log.e("Coil", "Image loading failed", result.throwable)
                    }
                )
            }
        }
    }
}
