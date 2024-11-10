package com.example.catapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.R
import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.databinding.ItemCatBinding
import com.example.catapp.utils.ImageUrlUtils
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CatAdapterViewHolder(
    private val binding: ItemCatBinding,
    private val catItemClickListener: ((CatBreedDataModel) -> Unit)?,
    private val picasso: Picasso
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(catDetails: CatBreedDataModel) {
        binding.tvCatBreedName.text = catDetails.name
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
        catDetails.referenceImageId?.let { posterPath ->
            picasso.load(ImageUrlUtils.buildImageUrl(posterPath))
                .transform(RoundedCornersTransformation(cornerRadius, 0))
                .into(binding.ivCatBreedImage)
        }

        handleCatItemClick(catDetails)
    }

    private fun handleCatItemClick(catDetails: CatBreedDataModel) {
        binding.root.setOnClickListener {
            catItemClickListener?.invoke(catDetails)
        }
    }
}
