package com.gabo.gk.ui.auth.login

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.constants.TAG
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.txt
import com.gabo.gk.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    override fun setupView() {
        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                if (it.msg.isNotEmpty()) {
                    d(TAG,it.msg)
                    if (it.msg == getString(R.string.logged_in_successfully)) {
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        with(binding){
            btnSignIn.setOnClickListener {
                logIn(etEmail.txt(), etPassword.txt())
            }
            tvNotRegistered.setOnClickListener {
                with(findNavController()){
                    popBackStack(R.id.registerFragment, true)
                    navigate(R.id.registerFragment)
                }
            }
        }
    }

    private fun logIn(email: String, password: String) {
        viewModel.loginUser(email, password)
    }
}