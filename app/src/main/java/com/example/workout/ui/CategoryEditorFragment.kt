package com.example.workout.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.App
import com.example.workout.adapters.CategoriesEditorAdapter
import com.example.workout.adapters.OnClickListenerCategoryEditor
import com.example.workout.databinding.FragmentCategoryEditorBinding
import com.example.workout.models.CategoryWithImage
import com.example.workout.viewmodels.CategoryViewModel
import javax.inject.Inject

class CategoryEditorFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<CategoryViewModel> { factory }

    private var _binding: FragmentCategoryEditorBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("Binding is null")

    private var categoryAdapter: CategoriesEditorAdapter? = null

    private val onClickListenerCategory = object : OnClickListenerCategoryEditor {
        override fun onClick(category: CategoryWithImage) {
            val action = CategoryEditorFragmentDirections.actionCategoryEditorFragmentToExerciseListFragment(category.categoryName, category.id)
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryEditorBinding.inflate(inflater, container, false)
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

    private fun FragmentCategoryEditorBinding.initViews() {
            toolbar.apply {
                title = "Choose a category"
                setNavigationOnClickListener {
                    root.findNavController().navigateUp()
                }
            }
        progressSpinner.visibility = View.VISIBLE
        categoryAdapter = CategoriesEditorAdapter(onClickListenerCategory)
        categoryList.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = categoryAdapter
        }
    }

    private fun observeCategoriesList(list: List<CategoryWithImage>) {
        categoryAdapter?.submitList(list)
        binding.progressSpinner.visibility = View.GONE
    }
}
