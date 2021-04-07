package com.waqar.sampleproject.features.home.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.waqar.sampleproject.R
import com.waqar.sampleproject.base.view.BaseFragment
import com.waqar.sampleproject.features.home.adapters.VehicleRecyclerViewAdapter
import com.waqar.sampleproject.features.home.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel>(), SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_vehicles)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.tv_no_record)
    lateinit var tvNoRecordFound: TextView

    @BindView(R.id.swipe_container)
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupSwipeRefreshLayout()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData(arguments)
    }

    override fun getViewLayout(): Int {
        return R.layout.fragment_home
    }

    override fun getOrCreateViewModel(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun setupBindings() {
        super.setupBindings()

        viewModel.viewItems.observe(viewLifecycleOwner, { viewItems ->
            val adapter = recyclerView.adapter as VehicleRecyclerViewAdapter
            adapter.setViewItems(viewItems)
        })

        viewModel.showRefreshIndicator.observe(viewLifecycleOwner, { shouldRefresh ->
            swipeContainer.isRefreshing = shouldRefresh
        })

        viewModel.isRecordFound.observe(viewLifecycleOwner, { isRecordFound ->
            tvNoRecordFound.visibility = if (isRecordFound) View.GONE else View.VISIBLE
            recyclerView.visibility = if (isRecordFound) View.VISIBLE else View.GONE
        })
    }

    override fun onRefresh() {
        swipeContainer.isRefreshing = true
        viewModel.loadData()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = VehicleRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setupSwipeRefreshLayout() {
        swipeContainer.setOnRefreshListener(this)

        // Scheme colors for animation
        swipeContainer.setColorSchemeColors(
            ContextCompat.getColor(swipeContainer.context, android.R.color.holo_blue_bright),
            ContextCompat.getColor(swipeContainer.context, android.R.color.holo_green_light),
            ContextCompat.getColor(swipeContainer.context, android.R.color.holo_orange_light),
            ContextCompat.getColor(swipeContainer.context, android.R.color.holo_red_light)
        )
    }

    override fun onDestroyView() {
        viewModel.viewItems.removeObservers(viewLifecycleOwner)
        viewModel.showLoading.removeObservers(viewLifecycleOwner)
        viewModel.showRefreshIndicator.removeObservers(viewLifecycleOwner)
        viewModel.isRecordFound.removeObservers(viewLifecycleOwner)
        super.onDestroyView()
    }
}