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
            viewModel.state.collect{
                if (it.registerOk.isNotEmpty()){
                    Toast.makeText(requireContext(), it.registerOk, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            register(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun register(email: String, password: String) {
        viewLifecycleOwner.launchStarted {
            withContext(Dispatchers.IO){
                viewModel.registerUser(email, password)
            }
        }
    }
}