package com.example.project4.ui

import coil.load
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project4.R
import com.example.project4.data.Recipe
import com.example.project4.databinding.ItemRecipeBinding

// Francisco: RecyclerView Adapter
class RecipeAdapter(
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipeList: List<Recipe> = emptyList()

    inner class RecipeViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        //Map recipe data to UI components (item_recipe.xml)
        holder.binding.tvRecipeName.text = recipe.name
        holder.binding.tvCategory.text = recipe.category
        holder.binding.imgRecipeThumb.load(recipe.imageUrl) {
            //Default null image if there was image was null or load failed
            error(R.drawable.null_image)
            fallback(R.drawable.null_image)
        }
        //Send clicked recipe to callback in RecipeListFragment
        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }
    }

    //Return size
    override fun getItemCount(): Int = recipeList.size

    // Francisco: Update list
    fun submitList(list: List<Recipe>) {
        recipeList = list
        notifyDataSetChanged()
    }
}