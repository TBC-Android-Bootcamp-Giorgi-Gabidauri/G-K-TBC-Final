package com.gabo.gk.ui.auth.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setupView() {
        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.state.collect{
                if (it.loginOk.isNotEmpty()){
                    Toast.makeText(requireContext(), it.loginOk, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.btnSignIn.setOnClickListener {
            logIn(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

    }


    private fun logIn(email: String, password: String) {
        viewLifecycleOwner.launchStarted {
            withContext(Dispatchers.IO){

                viewModel.loginUser(email, password)
            }
        }
    }
}