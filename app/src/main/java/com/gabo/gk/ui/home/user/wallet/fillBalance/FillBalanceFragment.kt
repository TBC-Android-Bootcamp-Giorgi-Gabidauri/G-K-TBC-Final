package com.gabo.gk.ui.home.user.wallet.fillBalance

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.comon.constants.TAG
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.comon.extensions.txt
import com.gabo.gk.databinding.FragmentFillBalanceBinding
import com.gabo.gk.ui.home.user.wallet.WalletViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillBalanceFragment : BottomSheetDialogFragment() {
    private var binding: FragmentFillBalanceBinding? = null
    private val viewModel: WalletViewModel by viewModels()
    var current = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                binding!!.progressBar.isVisible = it.loading
                if (it.msg.isNotEmpty()) {
                    d(TAG, it.msg)
                    binding!!.root.snackBar(it.msg)
                }
                if (it.msg == getString(R.string.balance_filled_successfully)) {
                    findNavController().popBackStack(R.id.walletFragment, true)
                    findNavController().navigate(R.id.walletFragment)
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding!!) {
            etCardNumber.text?.let {
                etCardNumber.doOnTextChanged { text, start, before, count ->
                    viewModel.formatCardNumber(it)
                }
            }
            etExpirationDate.text?.let {
                etExpirationDate.doOnTextChanged { text, start, before, count ->
                    viewModel.formatDate(it)
                }
            }
            etCVC.text?.let {
                etCVC.doOnTextChanged { text, start, before, count ->
                    viewModel.formatCVC(it)
                }
            }
            btnPay.setOnClickListener {
                viewModel.fillBalance(etAmount.txt().toIntOrNull() ?: 0)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFillBalanceBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}