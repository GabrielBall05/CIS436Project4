package com.example.project4.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.project4.data.Recipe

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    //To hold all initial recipes
    private var allRecipes = listOf<Recipe>()

    //Filtered list to show in UI. _displayedRecipes is the one to change its value. displayedRecipes is the one to be observed.
    private val _displayedRecipes = MutableLiveData<List<Recipe>>()
    val displayedRecipes: LiveData<List<Recipe>> = _displayedRecipes

    //List of recipe categories for category filter Spinner. _categories is the one to change its value. categories is the one to be observed.
    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    //For filtering the list
    private var currentSearchQuery = ""
    private var currentSelectedCategory = "All"

    //No need for a selectedRecipe var since RecipeDetailsFragment already catches the id as safe arg and calls getRecipeById() to retrieve the actual data.

    //TODO: Use this queue for the fetches
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(getApplication())
    }

    //Automatically fetch initial recipes list and categories list when app starts
    init {
        Log.d("RecipeViewModel", "Viewmodel Initialized. Fetching Recipes List and Categories.")
        fetchRecipes()
        fetchCategories()
    }

    fun fetchRecipes() {
        //TODO: Do the same tempList thing. Once done filling in the tempList, do "allRecipes = tempList" as well as "_displayedRecipes.value = allRecipes"
        //TODO: btw I think you'll need to do JsonObjectRequest instead of JsonArrayRequest because of how the API returns its info
    }

    fun fetchCategories() {
        //TODO: Do the same tempList thing. Once done filling in the tempList, do "_categories.value = tempList"
        //TODO: The tempList should have a default first value of "All" so I think that would look like: val tempList = mutableListOf("All") then add more
        //TODO: btw I think you'll need to do JsonObjectRequest instead of JsonArrayRequest because of how the API returns its info
    }

    //Will be called every time the user changes the value in the edit text search bar (something like binding.editText.onValueChanged {})
    fun onSearchValueChange(q: String) {
        currentSearchQuery = q
        performFilter()
    }

    //Will be called every time the user changes the category selection in the spinner (something like binding.spinner.onItemSelectedListener {})
    fun onCategoryFilterChange(c: String) {
        currentSelectedCategory = c
        performFilter()
    }

    //Updates the _displayedRecipes list based on the filters.
    //This changes displayedRecipes list which notifies whoever is observing it (the recyclerview)
    fun performFilter() {
        _displayedRecipes.value = allRecipes.filter { recipe ->
            val matchesSearch = recipe.name.contains(currentSearchQuery, ignoreCase = true)
            val matchesCategory = currentSelectedCategory == "All" || recipe.category == currentSelectedCategory

            matchesSearch && matchesCategory
        }
    }

    //Called from the RecipeDetailsFragment which passes in the id that it received from RecipeListFragment when a recipe was clicked
    fun getRecipeById(id: Int): Recipe? {
        return allRecipes.find { it.id == id }
    }
}