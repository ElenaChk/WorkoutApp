package com.example.workout.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.workout.R
import com.example.workout.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("Binding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        setToolbarImage()
        _binding?.initViews()
        return view
    }

    private fun setToolbarImage() {
        binding.categoryImage.setImageResource(args.categoryImage ?: 0)
    }

    private fun FragmentDetailsBinding.initViews() {
        exerciseName.text = args.exerciseName
        exerciseDescription.text = args.exerciseDescription
        val imageUrl = args.exerciseImage
        if (imageUrl != null) {
            Glide.with(requireContext()).load(imageUrl).into(exerciseImage)
        } else {
            exerciseImage.visibility = View.GONE
        }
        toolbarLayout.title = args.categoryName.orEmpty()
        toolbar.setNavigationOnClickListener {
            view?.findNavController()?.navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

