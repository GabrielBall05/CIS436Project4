package com.example.project4.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.project4.data.Recipe
import com.example.project4.data.IngredientMeasurement
import com.android.volley.toolbox.JsonObjectRequest
import com.example.project4.data.Constants
import org.json.JSONObject

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
    public var currentSelectedCategory = "All"

    //For making Volley requests
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(getApplication())
    }

    //Automatically fetch initial recipes list and categories list when app starts
    init {
        Log.d("RecipeViewModel", "Viewmodel Initialized. Fetching Recipes List and Categories.")
        fetchRecipes()
        fetchCategories()
    }

    // Francisco: Fetch recipes with ingredients + youtube
    fun fetchRecipes() {
        val url = "${Constants.BASE_URL}${Constants.GET_ALL}"
        Log.d("RecipeViewModel", url)

        //Make request (JsonObjectRequest because the api returns the array inside an object first)
        val request = JsonObjectRequest(
            url,
            null,
            { response ->

                //Get the array of meals
                val mealsArray = response.getJSONArray("meals")
                val tempList = mutableListOf<Recipe>()

                //Parse each object in array
                for (i in 0 until mealsArray.length()) {
                    val obj = mealsArray.getJSONObject(i)

                    val ingredientsList = mutableListOf<IngredientMeasurement>()

                    // TheMealDB gives 20 ingredient slots
                    for (j in 1..20) {
                        val ingredient = obj.getString("strIngredient$j")
                        val measure = obj.getString("strMeasure$j")

                        if (ingredient.isNotBlank() && ingredient != "null") {
                            ingredientsList.add(
                                IngredientMeasurement(
                                    ingredient = ingredient,
                                    measurement = measure
                                )
                            )
                        }
                    }

                    //Make the recipe object
                    val recipe = Recipe(
                        id = obj.getString("idMeal").toInt(),
                        name = obj.getString("strMeal"),
                        category = obj.getString("strCategory"),
                        ingredients = ingredientsList,
                        instructions = obj.getString("strInstructions"),
                        //Null testing
                        //imageUrl = "",
                        //youtubeVideo = ""
                        imageUrl = obj.getString("strMealThumb"),
                        youtubeVideo = obj.optString("strYoutube", null)
                    )

                    //Add to temp list
                    tempList.add(recipe)
                }

                //Update the master list and the LiveData display list
                allRecipes = tempList
                _displayedRecipes.value = allRecipes

            },
            { error ->
                Log.e("RecipeViewModel", "Error fetching recipes: ${error.message}")
            }
        )

        //Send request
        requestQueue.add(request)
    }

    // Francisco: Fetch categories for spinner
    fun fetchCategories() {
        val url = "${Constants.BASE_URL}${Constants.GET_CATEGORIES}"
        Log.d("RecipeViewModel", url)

        //Make request
        val request = JsonObjectRequest(
            url,
            null,
            { response ->

                //Get the array of categories
                val categoriesArray = response.getJSONArray("categories")
                val tempList = mutableListOf("All") //Give first slot value of "All"

                //Get each category
                for (i in 0 until categoriesArray.length()) {
                    val obj = categoriesArray.getJSONObject(i)
                    val category = obj.getString("strCategory")
                    tempList.add(category)
                }

                //Update categories LiveData list
                _categories.value = tempList
            },
            { error ->
                Log.e("RecipeViewModel", "Error fetching categories: ${error.message}")
            }
        )

        //Send request
        requestQueue.add(request)
    }

    //Will be called every time the user changes the value in the edit text search bar
    fun onSearchValueChange(q: String) {
        currentSearchQuery = q
        performFilter() //Filter out recipes to display
    }

    //Will be called every time the user changes the category selection in the spinner
    fun onCategoryFilterChange(c: String) {
        currentSelectedCategory = c
        performFilter() //Filter out recipes to display
    }

    //Updates the _displayedRecipes list based on the filters.
    //This changes displayedRecipes list which notifies whoever is observing it (the recyclerView)
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