package com.gabo.gk.ui.auth.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var auth: FirebaseAuth
    override fun setupView() {
        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        auth.currentUser?.uid?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        viewLifecycleOwner.launchStarted {
            viewModel.state.collect {
                if (it.loginOk.isNotEmpty()) {
                    Toast.makeText(requireContext(), it.loginOk, Toast.LENGTH_SHORT).show()
                    if (it.loginOk == getString(R.string.logged_in_successfully)) {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            logIn(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun logIn(email: String, password: String) {
        viewModel.loginUser(email, password)
    }
}