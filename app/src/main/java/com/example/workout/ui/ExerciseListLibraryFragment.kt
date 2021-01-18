package com.example.workout.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout.App
import com.example.workout.adapters.ExerciseListLibraryAdapter
import com.example.workout.adapters.LibraryOnClickListener
import com.example.workout.data.model.ExerciseJson
import com.example.workout.databinding.FragmentExerciseListLibraryBinding
import com.example.workout.viewmodels.ExerciseListViewModel
import javax.inject.Inject


class ExerciseListLibraryFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<ExerciseListViewModel> { factory }

    private var _binding: FragmentExerciseListLibraryBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("Binding is null")

    private val args: ExerciseListLibraryFragmentArgs by navArgs()

    private var exerciseListAdapter: ExerciseListLibraryAdapter? = null

    private val onClickListener = object : LibraryOnClickListener {
        override fun onClick(exercise: ExerciseJson) {
            Log.v("TESTED", "$exercise")
            val imageUrl = exercise.exerciseImage.find { image -> image?.isMain == true }?.imageUrl
            val action =
                ExerciseListLibraryFragmentDirections.actionExerciseListLibraryFragmentToDetailsFragment(
                    exercise.name,
                    exercise.description,
                    imageUrl,
                    args.categoryName,
                    args.categoryImage
                )
            findNavController().navigate(action)
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
        _binding = FragmentExerciseListLibraryBinding.inflate(inflater, container, false)
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
        exerciseListAdapter = null
        _binding = null
    }

    private fun FragmentExerciseListLibraryBinding.initViews() {
        progressSpinner.visibility = View.VISIBLE
        exerciseListAdapter = ExerciseListLibraryAdapter(onClickListener)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = exerciseListAdapter
        }
        toolbar.apply {
            title = args.categoryName.orEmpty()
            setNavigationOnClickListener {
                view?.findNavController()?.navigateUp()
            }
        }
    }

    private fun observeExerciseList(list: List<ExerciseJson?>) {
        exerciseListAdapter?.submitList(list)
        binding.progressSpinner.visibility = View.GONE
    }
}