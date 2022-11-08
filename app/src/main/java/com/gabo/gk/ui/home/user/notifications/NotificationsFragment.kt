package com.gabo.gk.ui.home.user.notifications

import android.content.SharedPreferences
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.databinding.FragmentNotificationsBinding
import com.gabo.gk.ui.adapters.NotificationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {
    private val viewModel: NotificationsViewModel by viewModels()
    private lateinit var notificationsAdapter: NotificationsAdapter

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun setupView() {
        setupObservers()
        setupAdapters()
        setupAppBar()
    }

    private fun setupAppBar() {
        with(binding.appBar) {
            tvTitle.text = getString(R.string.notifications)
            ivArrowBack.visibility = View.GONE
        }
    }

    private fun setupAdapters() {
        notificationsAdapter = NotificationsAdapter { }
        with(binding) {
            rvNotifications.adapter = notificationsAdapter
            rvNotifications.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                with(binding) {
                    progressBar.isVisible = it.loading
                    rvNotifications.isVisible = !it.loading
                    it.data?.let { list -> notificationsAdapter.submitList(list) }
                    when {
                        it.msg.isNotEmpty() -> binding.root.snackBar(getString(R.string.no_notifications_here))
                    }
                }
            }

        }
    }
}