package io.github.wangcheng.layback

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import androidx.leanback.widget.VerticalGridPresenter

open class GridFragment : Fragment() {
    private var gridViewHolder1: VerticalGridPresenter.ViewHolder? = null
    private lateinit var adapter: ArrayObjectAdapter
    private lateinit var gridPresenter: VerticalGridPresenter
    private lateinit var launcherActivitiesManager: LauncherActivitiesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setupGridPresenter()
        setupAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(
            R.layout.grid_fragment,
            container, false,
        ) as ViewGroup
        val gridFrame = root.findViewById<View>(R.id.grid_frame) as ViewGroup
        val gridViewHolder = gridPresenter.onCreateViewHolder(gridFrame)
        gridViewHolder1 = gridViewHolder
        gridFrame.addView(gridViewHolder.view)
        updateAdapter()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        gridViewHolder1!!.gridView.swapAdapter(null, true)
        gridViewHolder1 = null
    }

    private fun updateAdapter() {
        if (gridViewHolder1 != null) {
            gridPresenter.onBindViewHolder(gridViewHolder1, adapter)
        }
    }

    private fun setupAdapter() {
        launcherActivitiesManager = LauncherActivitiesManager(requireContext())
        adapter = ArrayObjectAdapter(CardPresenter())
        updateAdapter()
        val allItems = launcherActivitiesManager.getAllLauncherActivities()
        for (i in allItems) {
            adapter.add(i)
        }
    }

    private fun setupGridPresenter() {
        gridPresenter = VerticalGridPresenter(FocusHighlight.ZOOM_FACTOR_LARGE, false)
        gridPresenter.numberOfColumns = 5
        gridPresenter.onItemViewClickedListener = ItemViewClickedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?,
        ) {
            if (item is LauncherItem) {
                launcherActivitiesManager.launchActivity(item)
            }
        }
    }

    companion object {
        private const val TAG = "GridFragment"
    }
}
