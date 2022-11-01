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
                    it.repeatedPasswordValidation.isNotEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            it.repeatedPasswordValidation,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                if (it.registeredSuccessfully.isNotEmpty()) {
                    Toast.makeText(requireContext(), it.registeredSuccessfully, Toast.LENGTH_SHORT)
                        .show()
                    if (it.registeredSuccessfully == "Registered Successfully") {
                        findNavController()
                            .navigate(R.id.action_registerFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        with(binding) {

            btnSignUp.setOnClickListener {

                register(
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etRepeatPassword.text.toString()
                )
            }
        }
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        viewModel.registerUser(email, password, repeatPassword)

    }
}
