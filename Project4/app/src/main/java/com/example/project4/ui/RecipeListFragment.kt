package com.example.project4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.project4.R
import com.example.project4.databinding.FragmentRecipeListBinding
import androidx.navigation.findNavController
import com.example.project4.viewmodels.RecipeViewModel


class RecipeListFragment : Fragment() {

    //Bindings
    private var _binding : FragmentRecipeListBinding? = null
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
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: This tempBtn will be replaced with the recycler view's onItemSelected listener and pass the selected recipe id
        binding.tempBtn.setOnClickListener {
            //TODO: This 100 integer will be replaced with the id of the selected recipe
            it.findNavController().navigate(RecipeListFragmentDirections.listToDetails(100))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}