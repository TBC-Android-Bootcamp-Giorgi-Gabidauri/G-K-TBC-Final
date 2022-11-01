package com.gabo.gk.ui.auth.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
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
            viewModel.state.collect {
                when {
                    (it.registeredSuccessfully.isNotEmpty()) -> {
                        Toast.makeText(
                            requireContext(), it.registeredSuccessfully, Toast.LENGTH_SHORT
                        ).show()
                        if (it.registeredSuccessfully == getString(R.string.registered_successfully)) {
                            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                        }
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.btnSignUp.setOnClickListener {
            register(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun register(email: String, password: String) {
        viewModel.registerUser(email, password)
    }

}