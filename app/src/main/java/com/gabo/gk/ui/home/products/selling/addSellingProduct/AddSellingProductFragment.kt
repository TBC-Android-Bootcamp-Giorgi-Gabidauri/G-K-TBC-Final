package com.gabo.gk.ui.home.products.selling.addSellingProduct

import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentAddSellingProductBinding
import com.gabo.gk.ui.adapters.AddImagesAdapter
import com.gabo.gk.ui.model.addImage.ImageModel
import com.gabo.gk.ui.model.product.ProductModelUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AddSellingProductFragment :
    BaseFragment<FragmentAddSellingProductBinding>(FragmentAddSellingProductBinding::inflate) {
    private val viewModel: AddSellingProductViewModel by viewModels()
    private lateinit var adapter: AddImagesAdapter
    private var imageList = mutableListOf<ImageModel>()
    private var uriList = mutableListOf<Uri>()

    @Inject
    lateinit var auth: FirebaseAuth

    private val imageFromGallery: ActivityResultLauncher<String> = loadImageFromGallery()

    override fun setupView() {
        setupAdapters()
        setupListeners()
        setUpObservers()
    }

    private fun setupListeners() {
        with(binding) {
            swNegotiablePrice.setOnCheckedChangeListener { _, _ ->
                if (swNegotiablePrice.isChecked) tilPrice.visibility = View.GONE
                else tilPrice.visibility = View.VISIBLE
            }
            btnUpload.setOnClickListener { uploadProduct() }
            appBar.ivArrowBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun loadImageFromGallery() =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            it?.let { uris ->
                uriList = it.toMutableList()
                if (imageList.isNotEmpty()) {
                    for (i in uris.indices) {
                        if (ImageModel(uris[i]) != imageList[i]) {
                            imageList.add(ImageModel(uris[i]))
                        }
                    }
                } else {
                    imageList = uris.map { uri -> ImageModel(uri) }.toMutableList()
                }
            }
            adapter.submitList(imageList.toList())
        }

    private fun setupAdapters() {
        setupDropDownAdapters()
        setupRvAdapters()
    }

    private fun setupDropDownAdapters() {
        val languages = resources.getStringArray(R.array.ProductCategory)
        val languagesAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item_view, languages)
        binding.autoCTvCategory.setAdapter(languagesAdapter)

        val locations = resources.getStringArray(R.array.Location)
        val locationAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item_view, locations)
        binding.autoCTvLocation.setAdapter(locationAdapter)

        val types = resources.getStringArray(R.array.ProductType)
        val typesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item_view, types)
        binding.autoCTvType.setAdapter(typesAdapter)
    }

    private fun setupRvAdapters() {
        adapter = AddImagesAdapter(addClick = { imageFromGallery.launch("image/*") },
            deleteClick = { deleteImage(it) })
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter.submitList(listOf())
    }

    private fun deleteImage(model: ImageModel) {
        imageList.remove(model)
        adapter.submitList(imageList.toList())
    }

    private fun uploadProduct() {
        with(binding) {
            val product = ProductModelUi(
                uid = auth.currentUser?.uid ?: "",
                title = etTitle.text.toString(),
                productCondition = when {
                    chipDetails.isChecked -> chipDetails.text.toString()
                    chipBrandNew.isChecked -> chipBrandNew.text.toString()
                    chipSecondHand.isChecked -> chipSecondHand.text.toString()
                    chipLikeANew.isChecked -> chipLikeANew.text.toString()
                    else -> chipAny.text.toString()
                },
                description = etDescription.text.toString(),
                productType = autoCTvType.text.toString(),
                productCategory = autoCTvCategory.text.toString(),
                canBeSoldOnline = swSellOnline.isChecked,
                price = tvPrice.text.toString(),
                negotiablePrice = swNegotiablePrice.isChecked,
                photos = null,
                sellerName = etSellerName.text.toString(),
                sellerPhoneNumber = etPhoneNumber.text.toString(),
                location = autoCTvLocation.text.toString()
            )
            viewLifecycleOwner.launchStarted {
                withContext(Dispatchers.Main) {
                    viewModel.uploadProduct(product, uriList)
                }
            }
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.state.collect {
                if (it.uploadInfo.isNotEmpty()) {
                    Toast.makeText(requireContext(), it.uploadInfo, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}