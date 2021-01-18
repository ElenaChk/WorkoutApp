package com.example.workout.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.workout.App
import com.example.workout.adapters.CategoriesAdapter
import com.example.workout.adapters.OnClickListenerCategory
import com.example.workout.databinding.FragmentCategoryLibraryBinding
import com.example.workout.models.CategoryWithImage
import com.example.workout.viewmodels.CategoryViewModel
import javax.inject.Inject

class CategoryLibraryFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<CategoryViewModel> { factory }

    private var _binding: FragmentCategoryLibraryBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("Binding is null")

    private var categoryAdapter: CategoriesAdapter? = null

    private val onClickListenerCategory = object : OnClickListenerCategory {
        override fun onClick(category: CategoryWithImage) {
            val action = TabLayoutFragmentDirections.actionTabLayoutFragmentToExerciseListLibraryFragment(category.categoryName, category.id, category.categoryImage)
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCategoryLibraryBinding.inflate(inflater, container, false)
        binding.initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategoriesList()
        viewModel.categoriesList.observe(viewLifecycleOwner, ::observeCategoriesList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        categoryAdapter = null
        _binding = null
    }

    private fun FragmentCategoryLibraryBinding.initViews() {
        progressSpinner.visibility = View.VISIBLE
        categoryAdapter = CategoriesAdapter(onClickListenerCategory)
        categoryList.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = categoryAdapter
        }
    }

    private fun observeCategoriesList(list: List<CategoryWithImage>) {
        categoryAdapter?.submitList(list)
        binding.progressSpinner.visibility = View.GONE
    }
}
