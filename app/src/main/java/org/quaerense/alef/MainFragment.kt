package org.quaerense.alef

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.quaerense.alef.network.InternetChecker
import kotlin.math.sqrt


class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var srlRefresh: SwipeRefreshLayout
    private lateinit var rvImages: RecyclerView

    private lateinit var adapter: ImageAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvImages = view.findViewById(R.id.rvImages)
        srlRefresh = view.findViewById(R.id.srlRefresh)

        adapter = ImageAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        rvImages.adapter = adapter
        rvImages.layoutManager = GridLayoutManager(activity, getColumnCount())

        srlRefresh.setOnRefreshListener(this)
        loadData()

        adapter.onImageClickListener = object : ImageAdapter.OnImageClickListener {
            override fun invoke(imageUrl: String) {
                runFullScreenFragment(imageUrl)
            }
        }

        viewModel.imageUrls.observe(viewLifecycleOwner) {
            adapter.imageUrls = it
        }
    }

    override fun onRefresh() {
        srlRefresh.isRefreshing = true
        loadData()
        srlRefresh.isRefreshing = false
    }

    private fun loadData() {
        val internetChecker = InternetChecker(requireContext())
        if (internetChecker.isConnected()) {
            viewModel.loadData()
        } else {
            Toast.makeText(requireContext(), R.string.error_no_internet_connection, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getColumnCount(): Int {
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val yInches = metrics.heightPixels / metrics.ydpi
        val xInches = metrics.widthPixels / metrics.xdpi
        val diagonalInches = sqrt((xInches * xInches + yInches * yInches).toDouble())
        return if (diagonalInches >= 7.0) {
            3
        } else {
            2
        }
    }

    private fun runFullScreenFragment(imageUrl: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, FullScreenImageFragment.getInstance(imageUrl))
            .addToBackStack(null)
            .commit()
    }
}