package com.acieslinski.videos.featues.videos.list.ui.helpers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import timber.log.Timber

fun SnapHelper.snapToPosition(position: Int, recyclerView: RecyclerView) {
    with(recyclerView) {
        val currentPosition = findSelectedPosition(recyclerView)
        if (currentPosition != position) {
            scrollToPosition(position)
            post {
                val layoutManager = layoutManager as LinearLayoutManager
                layoutManager.findViewByPosition(position)?.let { view ->
                    calculateDistanceToFinalSnap(layoutManager, view)
                        ?.let { snapDistance ->
                            if (snapDistance[0] != 0 || snapDistance[1] != 0) {
                                scrollBy(snapDistance[0], snapDistance[1])
                            }
                        }
                } ?: Timber.w("no view with position $position to snap into")
            }
        }
    }
}

fun SnapHelper.findSelectedPosition(recyclerView: RecyclerView): Int? {
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    return findSnapView(layoutManager)?.let {
        layoutManager.getPosition(it)
    }
}