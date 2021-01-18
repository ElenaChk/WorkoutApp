package com.example.workout.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.workout.App
import com.example.workout.adapters.OnClickListenerWorkout
import com.example.workout.adapters.WorkoutsAdapter
import com.example.workout.data.database.Workout
import com.example.workout.databinding.FragmentMyWorkoutsBinding
import com.example.workout.viewmodels.WorkoutEditorViewModel
import com.example.workout.viewmodels.WorkoutsListViewModel
import javax.inject.Inject

class WorkoutsListFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<WorkoutsListViewModel> { factory }

    private var _binding: FragmentMyWorkoutsBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("Binding is null")

    private var workoutsAdapter: WorkoutsAdapter? = null

    private val onClickListenerWorkout = object : OnClickListenerWorkout {
        override fun onClick(workout: Workout) {
            val action = TabLayoutFragmentDirections.actionTabLayoutFragmentToWorkoutEditorFragment(workout.workoutId)
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyWorkoutsBinding.inflate(inflater, container, false)

        workoutsAdapter = WorkoutsAdapter(onClickListenerWorkout)
        binding.workoutsRecyclerView.adapter = workoutsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWorkouts()
        viewModel.workouts.observe(viewLifecycleOwner, {
            Log.d("TESTED", "$it")
            workoutsAdapter?.submitList(it)
            if (it.isEmpty()) {
                binding.workoutsRecyclerView.visibility = View.GONE
                binding.emptyTv.visibility = View.VISIBLE
            } else {
                binding.emptyTv.visibility = View.GONE
                binding.workoutsRecyclerView.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        workoutsAdapter = null
        _binding = null
    }
}