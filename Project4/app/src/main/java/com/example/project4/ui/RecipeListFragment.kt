package com.example.project4.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.findNavController
import com.example.project4.databinding.FragmentRecipeListBinding
import com.example.project4.viewmodels.RecipeViewModel


class RecipeListFragment : Fragment() {

    // Francisco: View binding
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    // Francisco: Shared ViewModel
    private val viewModel: RecipeViewModel by activityViewModels()

    // Francisco: RecyclerView adapter
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeRecipes()
        observeCategories()
        setupSearchBar()
        setupSpinner()
    }

    // Francisco: Set up recycler view
    private fun setupRecyclerView() {
        //Handle item clicks from the adapter
        recipeAdapter = RecipeAdapter { recipe ->
            //Navigate to RecipeDetailsFragment passing the clicked id with Safe Args
            val action = RecipeListFragmentDirections.listToDetails(recipe.id)
            binding.root.findNavController().navigate(action)
        }

        //Linear layout and attach adapter
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.adapter = recipeAdapter
    }

    // Francisco: Observe recipes from ViewModel
    private fun observeRecipes() {
        //If displayedRecipes changes, show empty message if applicable and give the adapter the updated list
        viewModel.displayedRecipes.observe(viewLifecycleOwner) { recipes ->
            binding.tvEmptyMsg.visibility = if (recipes.isEmpty()) View.VISIBLE else View.INVISIBLE
            recipeAdapter.submitList(recipes)
        }
    }

    // Francisco: Observe categories for spinner
    private fun observeCategories() {
        //Observe list of categories from ViewModel to populate Spinner
        viewModel.categories.observe(viewLifecycleOwner) { categoryList ->
            //Standard adapter which is given the list of categories
            val spinnerAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categoryList
            )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = spinnerAdapter //Attach adapter

            //Restore previously selected category (for orientation changes or returning from Details screen)
            val position = categoryList.indexOf(viewModel.currentSelectedCategory)
            if (position >= 0) {
                binding.categorySpinner.setSelection(position, false)
            }
        }
    }

    // Francisco: Search bar listener
    private fun setupSearchBar() {
        //Whenever the user changes the text in the search bar edit text, update the searchQuery in the ViewModel
        //which in turn updates displayedRecipes (the list for the recyclerView's adapter)
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.onSearchValueChange(s.toString()) //Update
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    // Francisco: Spinner filter listener
    private fun setupSpinner() {
        //Whenever the user selects a category, update the selected category in the ViewModel,
        //which in turn updates displayedRecipes (the list for the recyclerView's adapter)
        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCategory = parent?.getItemAtPosition(position).toString()
                    viewModel.onCategoryFilterChange(selectedCategory) //Update
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}