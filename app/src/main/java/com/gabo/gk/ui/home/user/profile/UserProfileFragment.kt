package com.gabo.gk.ui.home.user.profile

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log.d
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.constants.TAG
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.comon.util.ProfileDirectionsListProvider.profileDirections
import com.gabo.gk.databinding.FragmentUserProfileBinding
import com.gabo.gk.ui.adapters.ProfileDirectionsAdapter
import com.gabo.gk.ui.model.user.UserModelUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var directionsAdapter: ProfileDirectionsAdapter
    private var user: UserModelUi? = null
    override fun setupView() {
        setupListeners()
        setupAdapters()
        setupObservers()
        setupCard()

    }

    private fun setupCard() {
        with(binding) {
            ivWalletBg.setImageResource(
                if (isDarkMode()) R.drawable.bg_wallet_card else R.drawable.bg_wallet_card_light
            )
        }
    }

    private fun isDarkMode(): Boolean {
        val darkModeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                with(binding) {
                    clProfile.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    when {
                        it.data != null -> {
                            user = it.data
                            setInfo(it.data)
                            clProfile.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                        it.msg.isNotEmpty() -> d(TAG, it.msg)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setInfo(user: UserModelUi) {
        with(binding) {
            ivImage.loadImage(user.profileImg)
            tvUserName.text = user.userName
            tvBalance.text = "My Balance: ${user.availableAmount.toLong()} $"
            val list = profileDirections.toMutableList()
            list[2].info = "${user.purchasedProducts.size} items"
            list[3].info = "${user.soldProducts.size} sold"
            directionsAdapter.submitList(list)
        }
    }

    private fun setupAdapters() {
        directionsAdapter = ProfileDirectionsAdapter {
            with(findNavController()) {
                when (it.title) {
                    "Saved" -> navigate(R.id.savedItemsFragment)
                    "Wallet" -> navigate(R.id.walletFragment)
                    "Purchases" -> navigate(R.id.purchasesFragment)
                    "Selling" -> navigate(R.id.sellingFragment)
                    "Notifications" -> navigate(R.id.notificationsFragment)
                    "Categories" -> navigate(R.id.categoriesFragment)
                }
            }
        }
        with(binding) {
            rvDirections.adapter = directionsAdapter
            rvDirections.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        directionsAdapter.submitList(profileDirections)
    }

    private fun setupListeners() {
        with(binding) {
            tvSignOut.setOnClickListener {
                viewModel.logOut()
                findNavController().navigate(R.id.loginFragment)
            }
            tvSellProduct.setOnClickListener { findNavController().navigate(R.id.addSellingProductFragment) }
            tvEditProfile.setOnClickListener {
                user?.let {
                    findNavController().navigate(
                        UserProfileFragmentDirections.actionUserProfileFragmentToEditProfileFragment(
                            it
                        )
                    )
                }
            }
        }
    }
}