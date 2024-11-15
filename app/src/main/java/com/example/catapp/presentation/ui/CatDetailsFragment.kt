package com.example.catapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.catapp.R
import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.databinding.FragmentCatDetailsBinding
import com.example.catapp.presentation.viewmodel.CatViewModel
import com.example.catapp.utils.ImageUrlUtils
import com.example.catapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CatDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCatDetailsBinding
    private val viewModel: CatViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.sharedCatBreed.value?.id?.let {
            viewModel.getBreedDetailsData(it)
            setupDataObserver()
        }
    }

    private fun setupDataObserver() {
        viewModel.breedDetailsData.observe(viewLifecycleOwner) { breedItem ->
            when (breedItem) {
                is Resource.Success -> {
                    binding.pbLoader.visibility = View.GONE
                    breedItem.data?.let { setupUI(it) } ?:  Toast.makeText(this@CatDetailsFragment.context, "Error", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    binding.pbLoader.visibility = View.GONE
                    Toast.makeText(this@CatDetailsFragment.context, breedItem.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> binding.pbLoader.visibility = View.VISIBLE
            }
        }
    }

    private fun setupUI(breedDetailModel: CatBreedDataModel) {
        loadImage(breedDetailModel.referenceImageId?.let { ImageUrlUtils.buildImageUrl(it) })

        viewModel.sharedCatBreed.value?.let {
            binding.apply {
                tvBreedName.text = resources.getString(R.string.breed_name, it.name)
                tvOrigin.text = resources.getString(R.string.origin, it.origin)
                tvDescription.text = resources.getString(R.string.description, it.description)
                tvLifeSpan.text = resources.getString(R.string.life_span, it.lifeSpan)
                pbLoader.visibility = View.GONE
            }
        }
    }

    private fun loadImage(imgUrl: String?) {
        val cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.image_corner_radius) ?: 0

        imgUrl?.let {
            Glide.with(binding.ivCat.context)
                .load(it)
                .placeholder(R.drawable.image_placeholder) // Show a placeholder while loading
                .error(R.drawable.image_placeholder) // Show this image if the URL fails
                .transform(CenterCrop(), RoundedCorners(cornerRadius))
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image for better performance
                .into(binding.ivCat)
        }
    }


    override fun onDestroy() {
        viewModel.breedDetailsData.removeObservers(this)
        super.onDestroy()
    }
}