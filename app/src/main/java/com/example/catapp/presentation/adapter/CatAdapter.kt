package com.example.catapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.databinding.ItemCatBinding
import com.example.catapp.presentation.adapter.viewholder.CatAdapterViewHolder
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CatAdapter @Inject constructor(
    private val picasso: Picasso): ListAdapter<CatBreedDataModel, CatAdapterViewHolder>(CatDataDiffCallback()) {


    private var onClick: ((CatBreedDataModel) -> Unit)? = null

    fun setOnClick(onClick: (CatBreedDataModel) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatAdapterViewHolder {
        val binding = ItemCatBinding.inflate(
                LayoutInflater.from(parent.context),
            parent,
        false
        )
        return CatAdapterViewHolder(binding, onClick, picasso)
    }

    override fun onBindViewHolder(holder: CatAdapterViewHolder, position: Int) {
        val catDetails = getItem(position)
        holder.bind(catDetails)
    }
}
