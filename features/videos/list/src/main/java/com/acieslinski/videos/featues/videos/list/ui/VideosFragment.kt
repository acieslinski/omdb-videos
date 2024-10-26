package com.acieslinski.videos.featues.videos.list.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import coil.Coil
import coil.ImageLoader
import com.acieslinski.videos.featues.videos.list.ui.helpers.AlertDialogHelper
import com.acieslinski.videos.featues.videos.list.ui.helpers.VideosScalingHelper
import com.acieslinski.videos.featues.videos.list.ui.helpers.VideosSelectingHelper
import com.acieslinski.videos.featues.videos.list.ui.helpers.snapToPosition
import com.acieslinski.videos.videos.databinding.FragmentVideosBinding
import com.acieslinski.videos.featues.videos.list.ui.mappers.UiStateMapper
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModel
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideoUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@AndroidEntryPoint
class VideosFragment : Fragment() {
    private val uiStateMapper = UiStateMapper()
    private val snapHelper = LinearSnapHelper()
    private val scalingHelper = VideosScalingHelper()
    private val videosSelectingHelper = VideosSelectingHelper()
    private val alertDialogHelper = AlertDialogHelper()
    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<VideosViewModel>()
    var onVideoItemClickListener: ((videoItem: VideoUiModel) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alertDialogHelper.attachToFragment(this)

        // NOTE: just to make it work, please ignore it
        setUpInsecureCoilImageLoader(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        collectVideosUiState()
    }

    override fun onDestroyView() {
        alertDialogHelper.onDestroyView()
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews() {
        with(binding) {
            videosRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            snapHelper.attachToRecyclerView(videosRecyclerView)
            scalingHelper.attachToRecyclerView(snapHelper, videosRecyclerView)
            videosSelectingHelper.attachToRecyclerView(snapHelper, videosRecyclerView)

            videosSelectingHelper.onSelectedListener = {
                viewModel.onSelectionAction(it)
            }
            searchButton.setOnClickListener {
                viewModel.onSearchAction(searchEditText.text.toString())
            }
        }
        with(alertDialogHelper) {
            onDismissListener = viewModel::onDismissAlert
        }
    }

    private fun collectVideoItems() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.videosUiState
                .map { it.videos }
                .distinctUntilChanged()
                .collectLatest {
                    VideosAdapter(
                        videosList = it,
                        onVideoItemClickListener = onVideoItemClickListener
                    ).apply {
                        binding.videosRecyclerView.adapter = this
                    }
                }
        }
    }

    private fun collectVideosUiState() {
        collectVideoItems()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.videosUiState
                    .collectLatest {
                        with(binding) {
                            searchButton.isEnabled = it.isLoading.not()
                            searchEditText.setText(it.searchQuery)
                            searchEditText.setSelection(it.searchQuery.length)
                            snapHelper.snapToPosition(it.selectedVideoPosition, videosRecyclerView)
                        }
                        with(alertDialogHelper) {
                            title = "Example Alert Dialog"
                            message = uiStateMapper.mapToStringResource(it.failure)
                            isVisible = it.failure != null && it.failure.isDismissed.not()
                        }
                    }
            }
        }
    }
}

private fun setUpInsecureCoilImageLoader(context: Context) {
    val trustAllCerts = arrayOf<TrustManager>(
        @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> =
                arrayOf()
        }
    )

    val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, java.security.SecureRandom())
    }

    val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .build()

    val imageLoader = ImageLoader.Builder(context)
        .okHttpClient { okHttpClient }
        .build()

    Coil.setImageLoader(imageLoader)
}