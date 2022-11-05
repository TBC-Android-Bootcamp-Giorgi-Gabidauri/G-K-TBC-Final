package com.gabo.gk.ui.home.user.wallet

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.databinding.FragmentWalletBinding
import com.gabo.gk.ui.adapters.TransactionsAdapter
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {
    private val viewModel: WalletViewModel by viewModels()
    private lateinit var transactionsAdapter: TransactionsAdapter

    @Inject
    lateinit var auth: FirebaseAuth
    override fun setupView() {
        setupAppBar()
        setupObservers()
        setupAdapters()
        setupCard()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            tvFillBalance.setOnClickListener {
                findNavController().navigate(R.id.fillBalanceFragment)
            }
        }
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

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        with(binding) {
            viewLifecycleOwner.launchStarted {
                clWallet.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                viewModel.getUserBalance().collect {
                    if (it.isNotEmpty()) binding.tvBalance.text = "$it $"
                }
                viewModel.defaultState.collect {
                    if (it.msg.isNotEmpty()) binding.root.snackBar(it.msg)
                    it.data?.let { list -> transactionsAdapter.submitList(list) }

                    clWallet.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setupAppBar() {
        with(binding.appBar) {
            tvTitle.text = getString(R.string.wallet)
            ivArrowBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapters() {
        transactionsAdapter = TransactionsAdapter(auth.currentUser!!.uid) {}
        with(binding) {
            rvTransactions.adapter = transactionsAdapter
            rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}