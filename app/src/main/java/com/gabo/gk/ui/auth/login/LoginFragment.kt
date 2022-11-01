package com.gabo.gk.ui.auth.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.comon.extensions.txt
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
            findNavController().navigate(R.id.homeFragment)
        }
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                if (it.msg.isNotEmpty()) {
                    binding.root.snackBar(it.msg)
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