package com.example.workout.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.App
import com.example.workout.MainActivity
import com.example.workout.R
import com.example.workout.adapters.ExerciseListEditorAdapter
import com.example.workout.adapters.EditorOnClickListener
import com.example.workout.data.model.ExerciseJson
import com.example.workout.databinding.FragmentExerciseListEditorBinding
import com.example.workout.viewmodels.ExerciseListViewModel
import javax.inject.Inject


class ExerciseListEditorFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<ExerciseListViewModel> { factory }

    private var _binding: FragmentExerciseListEditorBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("Binding is null")

    private val args: ExerciseListEditorFragmentArgs by navArgs()

    private var exerciseListEditorAdapter: ExerciseListEditorAdapter? = null

    private val selectedExercisesList = mutableListOf<ExerciseJson>()

    private val onClickListener = object : EditorOnClickListener {
        override fun onClick(exercise: ExerciseJson) {
            Log.v("BottomSheet", "called")
            val imageUrl = exercise.exerciseImage.find { image -> image?.isMain == true }?.imageUrl
            val bottomSheetFragment = BottomSheetDetailsFragment()
            val bundle = Bundle()
            bundle.putString("name", exercise.name)
            bundle.putString("description", exercise.description)
            bundle.putString("image", imageUrl)
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show(
                (context as MainActivity).supportFragmentManager,
                "BottomSheetFragment"
            )
        }

        override fun onCheckboxClick(exercise: ExerciseJson, isChecked: Boolean) {
            if (isChecked) {
                binding.fabButton.show()
                selectedExercisesList.add(exercise)
                Log.v("Selected Exercises", "$selectedExercisesList")
                setTextFAB()
            } else {
                selectedExercisesList.remove(exercise)
                Log.v("Selected Exercises", "$selectedExercisesList")
                setTextFAB()
                if (selectedExercisesList.isEmpty()) {
                    binding.fabButton.hide()
                }
            }
        }
    }
    private fun setTextFAB() {
        if (selectedExercisesList.size == 1) {
            binding.fabButton.text = "Add ${selectedExercisesList.size} exercise"
        } else {
            binding.fabButton.text = "Add ${selectedExercisesList.size} exercises"
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseListEditorBinding.inflate(inflater, container, false)
        binding.initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListExercise(args.categoryId ?: -1)
        viewModel.exercises.observe(viewLifecycleOwner, ::observeExerciseList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exerciseListEditorAdapter = null
        _binding = null
        viewModel.clearEvent()
        Log.v("ExerciseEditorFragment", "$exerciseListEditorAdapter, $_binding")
    }

    private fun FragmentExerciseListEditorBinding.initViews() {
        progressSpinner.visibility = View.VISIBLE
        exerciseListEditorAdapter = ExerciseListEditorAdapter(onClickListener)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = exerciseListEditorAdapter
        }
        toolbar.apply {
            title = args.categoryName.orEmpty()
            setNavigationOnClickListener {
                view?.findNavController()?.navigateUp()
            }
        }
        if (selectedExercisesList.isNotEmpty()) {
            fabButton.visibility = View.VISIBLE
            fabButton.text
        }
        fabButton.setOnClickListener {
            viewModel.setSelectedExercises(selectedExercisesList)
            findNavController().navigateUp()
        }
    }

    private fun observeExerciseList(list: List<ExerciseJson?>) {
        if (list.isEmpty()) return
        exerciseListEditorAdapter?.submitList(list)
        binding.progressSpinner.visibility = View.GONE
    }
}