package com.example.catapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.R
import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.databinding.FragmentCatBreedBinding
import com.example.catapp.presentation.adapter.CatAdapter
import com.example.catapp.presentation.viewmodel.CatViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CatBreedFragment : Fragment() {
    private lateinit var binding: FragmentCatBreedBinding
    private val viewModel: CatViewModel by activityViewModels()

    @Inject
    internal lateinit var catAdapter: CatAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatBreedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
        setupRecyclerView()
        getCatBreedData()
    }

    private fun setupToolbar() {
        // Setting up the toolbar
        binding.toolbar.title = "Cat Breeds"

        // Handle back button click
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getCatBreedData() {
        if (viewModel.catBreedData.value.isNullOrEmpty()) {
            viewModel.getCatBreedData()
        }
    }

    private fun setupViewModel() {
        viewModel.catBreedData.observe(viewLifecycleOwner) {
            submitList(it)
            binding.pbLoader.visibility = View.GONE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(this@CatBreedFragment.context, it, Toast.LENGTH_SHORT).show()
            binding.pbLoader.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        binding.rvCat.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(
                this@CatBreedFragment.context,
                RecyclerView.VERTICAL, false
            )
        }
        catAdapter.setOnClick { model ->
            viewModel.setCatBread(model)
            startFragment()
        }
    }

    private fun submitList(catBreedList: List<CatBreedDataModel>){
        catAdapter.submitList(catBreedList)
    }

    private fun startFragment() {
        val catDetailsFragment = CatDetailsFragment()
        parentFragmentManager
            .beginTransaction()
            .add(R.id.container, catDetailsFragment)
            .addToBackStack(null)
            .commit()
    }
}