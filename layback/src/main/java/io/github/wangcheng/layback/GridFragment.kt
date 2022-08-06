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
    private lateinit var mAdapter: ArrayObjectAdapter
    private lateinit var mGridPresenter: VerticalGridPresenter
    private var mGridViewHolder: VerticalGridPresenter.ViewHolder? = null
    private lateinit var launcherActivitiesManager: LauncherActivitiesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setupGridPresenter()
        setupAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(
            R.layout.grid_fragment,
            container, false
        ) as ViewGroup
        val gridFrame = root.findViewById<View>(R.id.grid_frame) as ViewGroup
        val gridViewHolder = mGridPresenter.onCreateViewHolder(gridFrame)
        mGridViewHolder = gridViewHolder
        gridFrame.addView(gridViewHolder.view)
        updateAdapter()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mGridViewHolder!!.gridView.swapAdapter(null, true)
        mGridViewHolder = null
    }

    private fun updateAdapter() {
        if (mGridViewHolder != null) {
            mGridPresenter.onBindViewHolder(mGridViewHolder, mAdapter)
        }
    }

    private fun setupAdapter() {
        launcherActivitiesManager = LauncherActivitiesManager(requireContext())
        mAdapter = ArrayObjectAdapter(CardPresenter())
        updateAdapter()
        val allItems = launcherActivitiesManager.getAllLauncherActivities()
        for (i in allItems) {
            mAdapter.add(i)
        }
    }

    private fun setupGridPresenter() {
        mGridPresenter = VerticalGridPresenter(FocusHighlight.ZOOM_FACTOR_LARGE, false)
        mGridPresenter.numberOfColumns = 5
        mGridPresenter.onItemViewClickedListener = ItemViewClickedListener()
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
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
