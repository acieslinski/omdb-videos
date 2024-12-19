package com.acieslinski.videos.featues.videos.list.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.acieslinski.videos.videos.test.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply { id = R.id.test_container })
    }
}
