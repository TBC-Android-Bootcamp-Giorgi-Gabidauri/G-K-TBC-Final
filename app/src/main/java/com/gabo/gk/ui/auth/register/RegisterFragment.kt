package com.gabo.gk.ui.auth.register

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.comon.extensions.txt
import com.gabo.gk.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun setupView() {
        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                if (it.msg.isNotEmpty()) {
                    binding.root.snackBar(it.msg)
                    if (it.msg == getString(R.string.registered_successfully)) {
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnSignUp.setOnClickListener {
                register(etEmail.txt(), etPassword.txt(), etRepeatPassword.txt())
            }
            tvAlreadyReg.setOnClickListener {
                with(findNavController()){
                    popBackStack(R.id.registerFragment, true)
                    navigate(R.id.loginFragment)
                }
            }

        }
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        viewModel.registerUser(email, password, repeatPassword)
    }
}