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

        val args = RecipeDetailsFragmentArgs.fromBundle(requireArguments())
        val recipe = viewModel.getRecipeById(args.id)

        if (recipe != null) {
            binding.tvRecipeName.text = recipe.name
            binding.tvRecipeCategory.text = recipe.category
            binding.imgRecipeImage.load(recipe.imageUrl) {
                //Null Image
                error(R.drawable.null_image)
                fallback(R.drawable.null_image)
            }

            val ingredientsText = recipe.ingredients.joinToString("\n") {
                "${it.measurement} ${it.ingredient}"
            }
            binding.tvRecipeIngredients.text = ingredientsText
            binding.tvRecipeInstructions.text = recipe.instructions

            if (!recipe.youtubeVideo.isNullOrBlank()) {
                binding.tvRecipeYTVideo.text = "Watch on YouTube"

                binding.tvRecipeYTVideo.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.youtubeVideo))
                    startActivity(intent)
                }
            } else {
                binding.tvRecipeYTVideo.text = "No YouTube video available"
            }

        } else {
            binding.tvRecipeName.text = "Recipe not found"
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}