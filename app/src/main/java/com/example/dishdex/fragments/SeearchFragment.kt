package com.example.dishdex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdex.R
import com.example.dishdex.activities.MainActivity
import com.example.dishdex.activities.MealActivity
import com.example.dishdex.adapters.MealsAdapter
import com.example.dishdex.databinding.FragmentSearchBinding
import com.example.dishdex.viewModel.HomeViewModel

class SeearchFragment: Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchRecyclerViewAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imageSearchArrow.setOnClickListener{ searchMeals()}
        observersearchedLiveData()


    }

    private fun observersearchedLiveData() {
        viewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList ->

            searchRecyclerViewAdapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeals() {
        val searchQuery=binding.edSearchBox.text.toString()
        if(searchQuery.isNotBlank()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {

        searchRecyclerViewAdapter= MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecyclerViewAdapter
        }
    }
}