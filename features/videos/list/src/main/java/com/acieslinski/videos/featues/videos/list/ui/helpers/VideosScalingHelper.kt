package com.acieslinski.videos.featues.videos.list.ui.helpers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class VideosScalingHelper {
    fun attachToRecyclerView(snapHelper: SnapHelper, recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val snapView = snapHelper.findSnapView(layoutManager) ?: return
                val snapPosition = recyclerView.getChildAdapterPosition(snapView)

                for (i in 0 until layoutManager.childCount) {
                    val child = layoutManager.getChildAt(i) ?: continue
                    val position = layoutManager.getPosition(child)

                    if (position == snapPosition) {
                        child.scaleX = MAGNITUDE
                        child.scaleY = MAGNITUDE
                    } else {
                        child.scaleX = 1.0f
                        child.scaleY = 1.0f
                    }
                }
            }
        })
    }

    companion object {
        const val MAGNITUDE = 1.15f
    }
}