package com.gabo.gk.ui.auth.register

import android.net.Uri
import android.view.Gravity
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.comon.extensions.txt
import com.gabo.gk.databinding.FragmentRegisterBinding
import com.gabo.gk.ui.model.user.UserModelUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    private val imageFromGallery: ActivityResultLauncher<String> = loadImageFromGallery()
    private var profileUri: Uri? = null
    override fun setupView() {
        setUpListeners()
        setUpObservers()
        setupAppBar()
    }

    private fun setupAppBar() {
        with(binding.appBar) {
            tvTitle.text = getString(R.string.create_account)
            tvTitle.gravity = Gravity.CENTER
            ivArrowBack.visibility = View.GONE
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                binding.progressBar.isVisible = it.loading
                binding.btnGetStarted.isClickable = !it.loading
                if (it.msg.isNotEmpty()) {
                    if (it.msg == getString(R.string.registered_successfully)) {
                        findNavController().navigate(R.id.homeFragment)
                    } else {
                        binding.root.snackBar(it.msg)
                    }
                }
            }
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnGetStarted.setOnClickListener {
                register()
            }
            tvAlreadyReg.setOnClickListener {
                with(findNavController()) {
                    popBackStack(R.id.registerFragment, true)
                    navigate(R.id.loginFragment)
                }
            }
            addImage.ivCross.setOnClickListener {
                profileUri = null
                addImage.ivImage.setImageResource(R.drawable.ic_add_photo)
                addImageViewChange()
            }
            addImage.root.setOnClickListener { imageFromGallery.launch("image/*") }
            chipAsAPerson.setOnClickListener {
                chipAsAPerson.isChecked = true
                chipAsAShop.isChecked = false
            }
            chipAsAShop.setOnClickListener {
                chipAsAShop.isChecked = true
                chipAsAPerson.isChecked = false
            }
        }
    }

    private fun register() {
        with(binding) {
            val userModel = UserModelUi(
                email = etEmail.txt(),
                password = etPassword.txt(),
                userName = etUserName.txt(),
                type = if (chipAsAPerson.isChecked) getString(R.string.person) else getString(R.string.shop),
                contactNumber = etPhoneNumber.txt(),
            )
            if (profileUri == null){
                tvAlreadyReg.snackBar(getString(R.string.choose_profile_pic))
            }else{
                profileUri?.let { viewModel.registerUser(userModel, etRepeatPassword.txt(), it) }
            }
        }
    }

    private fun loadImageFromGallery() =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            profileUri = it
            addImageViewChange()
        }

    private fun addImageViewChange() {
        with(binding.addImage) {
            if (profileUri != null) {
                profileUri?.let { uri -> ivImage.loadImage(uri) }
                tvUpload.visibility = View.GONE
                ivCross.visibility = View.VISIBLE
                ivImage.setPadding(0,0,0,0)
            } else {
                ivImage.setImageResource(R.drawable.ic_add_photo)
                tvUpload.visibility = View.VISIBLE
                ivCross.visibility = View.GONE
                ivImage.setPadding(0,0,0,20)
            }
        }
    }
}