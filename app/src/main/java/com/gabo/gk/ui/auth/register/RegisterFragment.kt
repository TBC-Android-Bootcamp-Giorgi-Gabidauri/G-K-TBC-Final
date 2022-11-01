package com.gabo.gk.ui.auth.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        binding.btnSignUp.setOnClickListener {
            register(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun register(email: String, password: String) {
        viewLifecycleOwner.launchStarted {
            withContext(Dispatchers.IO){
                viewModel.registerUser(email, password)
            }
        }
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        viewModel.registerUser(email, password, repeatPassword)

}