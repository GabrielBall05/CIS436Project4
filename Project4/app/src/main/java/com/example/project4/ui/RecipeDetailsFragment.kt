package com.example.project4.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.project4.R
import com.example.project4.databinding.FragmentRecipeDetailsBinding


class RecipeDetailsFragment : Fragment() {
    //Listener for fragment interaction
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    //Bindings
    private var _binding : FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        arguments?.let {
            val args = RecipeDetailsFragmentArgs.fromBundle(it)
            //TODO: This is catching the passed recipe id from RecipeListFragment and setting the title text view to just display the id.
            //TODO: Change to getting the full recipe from viewmodel and setting title/details text views from there.
            binding.tvRecipeTitle.text = args.id.toString()
        }
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

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}