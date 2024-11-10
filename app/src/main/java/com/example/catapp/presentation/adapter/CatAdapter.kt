package com.example.catapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.databinding.ItemCatBinding
import com.example.catapp.presentation.adapter.viewholder.CatAdapterViewHolder
import javax.inject.Inject

class CatAdapter @Inject constructor() : ListAdapter<CatBreedDataModel, CatAdapterViewHolder>(CatDataDiffCallback()) {

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
        return CatAdapterViewHolder(binding).apply {
            setItemClickListener(onClick)
        }
    }

    override fun onBindViewHolder(holder: CatAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
