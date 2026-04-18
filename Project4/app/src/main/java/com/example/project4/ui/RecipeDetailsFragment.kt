package com.example.project4.ui

// Francisco youtube link imports
import android.content.Intent
import android.net.Uri

import coil.load
import android.text.method.LinkMovementMethod

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project4.R
import com.example.project4.databinding.FragmentRecipeDetailsBinding
import com.example.project4.viewmodels.RecipeViewModel
import kotlin.getValue


class RecipeDetailsFragment : Fragment() {
    //Bindings
    private var _binding : FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel: RecipeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get the id safe arg, then get the corresponding recipe from the ViewModel
        val args = RecipeDetailsFragmentArgs.fromBundle(requireArguments())
        val recipe = viewModel.getRecipeById(args.id)

        //Ensure there is a recipe to show
        if (recipe != null) {
            //Set name and category text views
            binding.tvRecipeName.text = recipe.name
            binding.tvRecipeCategory.text = recipe.category
            //Show image
            binding.imgRecipeImage.load(recipe.imageUrl) {
                //Default null image if there was image was null or load failed
                error(R.drawable.null_image)
                fallback(R.drawable.null_image)
            }

            //Set ingredients and instructions text views
            val ingredientsText = recipe.ingredients.joinToString("\n") {
                "${it.measurement} ${it.ingredient}"
            }
            binding.tvRecipeIngredients.text = ingredientsText
            binding.tvRecipeInstructions.text = recipe.instructions

            //Make Youtube text clickable if there is a link provided
            if (!recipe.youtubeVideo.isNullOrBlank()) {
                binding.tvRecipeYTVideo.text = "Watch on YouTube"

                //Launch an implicit intent to play the video in an external app (youtube/browser) when clicked
                binding.tvRecipeYTVideo.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.youtubeVideo))
                    startActivity(intent)
                }
            } else { //There was no youtube video link, show unavailable message
                binding.tvRecipeYTVideo.text = "No YouTube video available"
            }
        } else { //Something went wrong, there is no corresponding recipe. Show error message
            binding.tvRecipeName.text = "Recipe not found"
        }

        //Return to RecipeListFragment when back button is clicked
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}