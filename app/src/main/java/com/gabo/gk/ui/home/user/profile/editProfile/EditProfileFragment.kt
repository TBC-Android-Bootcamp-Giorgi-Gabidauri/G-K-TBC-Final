package com.gabo.gk.ui.home.user.profile.editProfile

import android.net.Uri
import android.util.Log.d
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.constants.TAG
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.comon.extensions.txt
import com.gabo.gk.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()
    private val imageFromGallery: ActivityResultLauncher<String> = loadImageFromGallery()
    private var profileUri: Uri? = null
    override fun setupView() {
        setupAppBar()
        setInfo()
        setupListeners()
        setupObservers()
    }

    private fun setupAppBar() {
        with(binding.appBar){
            ivArrowBack.setOnClickListener { findNavController().navigateUp() }
            tvTitle.text = getString(R.string.edit_profile)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            with(binding) {
                viewModel.defaultState.collect {
                    progressBar.isVisible = it.loading
                    when {
                        it.msg == getString(R.string.user_updated_successfully) -> {
                            d(TAG,it.msg)
                            findNavController().popBackStack(R.id.userProfileFragment, true)
                            findNavController().navigate(R.id.userProfileFragment)
                        }
                        it.msg.isNotEmpty() -> {
                            d(TAG,it.msg)
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            addImage.root.setOnClickListener { imageFromGallery.launch("image/*") }
            btnSetChanges.setOnClickListener { updateUser() }
        }
    }

    private fun updateUser() {
        with(binding) {
            val userModel = args.user.copy(
                userName = etUserName.txt(),
                contactNumber = etPhoneNumber.txt(),
            )
            viewModel.updateProfile(userModel, profileUri)
        }
    }

    private fun setInfo() {
        with(binding) {
            etUserName.setText(args.user.userName)
            etPhoneNumber.setText(args.user.contactNumber)
            addImage.ivImage.loadImage(args.user.profileImg)

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
                ivImage.setPadding(0, 0, 0, 0)
            } else {
                ivImage.setImageResource(R.drawable.ic_add_photo)
                tvUpload.visibility = View.VISIBLE
                ivCross.visibility = View.GONE
                ivImage.setPadding(0, 0, 0, 20)
            }
        }
    }
}