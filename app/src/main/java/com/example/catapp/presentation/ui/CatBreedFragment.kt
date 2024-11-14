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
import com.example.catapp.utils.Resource
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
        viewModel.getCatBreedData()
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.cat_breeds_title)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupViewModel() {
        viewModel.catBreedData.observe(viewLifecycleOwner) { catData ->
            when (catData) {
                is Resource.Success -> {
                    binding.pbLoader.visibility = View.GONE
                    catData.data?.let { submitList(it) } ?:  Toast.makeText(this@CatBreedFragment.context, "Error", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    binding.pbLoader.visibility = View.GONE
                    Toast.makeText(this@CatBreedFragment.context, catData.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> binding.pbLoader.visibility = View.VISIBLE
            }
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