package com.example.catapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.catapp.R
import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.databinding.ItemCatBinding
import com.example.catapp.utils.ImageUrlUtils

class CatAdapterViewHolder(
    private val binding: ItemCatBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var catItemClickListener: ((CatBreedDataModel) -> Unit)? = null

    fun setItemClickListener(listener: ((CatBreedDataModel) -> Unit)?) {
        catItemClickListener = listener
    }

    fun bind(catDetails: CatBreedDataModel) {
        binding.tvCatBreedName.text = catDetails.name

        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
        val imageUrl = catDetails.referenceImageId?.let { ImageUrlUtils.buildImageUrl(it) }

        Glide.with(binding.ivCatBreedImage.context)
            .load(imageUrl)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivCatBreedImage)

        binding.root.setOnClickListener {
            catItemClickListener?.invoke(catDetails)
        }
    }
}
