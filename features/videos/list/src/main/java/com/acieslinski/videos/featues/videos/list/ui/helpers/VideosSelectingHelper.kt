package com.acieslinski.videos.featues.videos.list.ui.helpers

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.SnapHelper

class VideosSelectingHelper {
    var onSelectedListener: ((position: Int) -> Unit)? = null

    fun attachToRecyclerView(snapHelper: SnapHelper, recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    snapHelper.findSelectedPosition(recyclerView)?.let { position ->
                        onSelectedListener?.invoke(position)
                    }
                }
            }
        })
    }
}