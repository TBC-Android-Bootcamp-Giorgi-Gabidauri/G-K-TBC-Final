package com.gabo.gk.ui.home.user.profile

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.comon.extensions.snackBar
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
                        it.msg.isNotEmpty() -> binding.root.snackBar(it.msg)
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
                    "Messages" -> navigate(R.id.messagesFragment)
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