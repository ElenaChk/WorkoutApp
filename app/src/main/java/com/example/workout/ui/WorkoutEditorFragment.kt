package com.example.workout.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.App
import com.example.workout.MainActivity
import com.example.workout.R
import com.example.workout.adapters.ExerciseListLibraryAdapter
import com.example.workout.adapters.LibraryOnClickListener
import com.example.workout.data.database.Workout
import com.example.workout.data.model.ExerciseJson
import com.example.workout.databinding.FragmentWorkoutEditorBinding
import com.example.workout.viewmodels.ExerciseListViewModel
import com.example.workout.viewmodels.WorkoutEditorViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class WorkoutEditorFragment : Fragment() {

    companion object {
        private const val NO_WORKOUT_ID = -1L
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val workoutEditorViewModel by viewModels<WorkoutEditorViewModel> { factory }
    private val exerciseListViewModel by activityViewModels<ExerciseListViewModel> { factory }

    private val navController by lazy { findNavController() }

    private var _binding: FragmentWorkoutEditorBinding? = null
    private val binding
        get() = _binding!!

    private val args: WorkoutEditorFragmentArgs by navArgs()

    private var exerciseListAdapter: ExerciseListLibraryAdapter? = null

    private var currentExercises: MutableList<ExerciseJson>? = null

    private var isWorkoutChanged = false

    private val touchListener = View.OnTouchListener { v, event ->
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                isWorkoutChanged = true
            }
        }
        false
    }

    private val onClickListener = object : LibraryOnClickListener {
        override fun onClick(exercise: ExerciseJson) {
            Log.v("TESTED", "$exercise")
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
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeMovementFlags(UP or DOWN, RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition
            if (from < to) {
                for (item in from until to) {
                    Collections.swap(currentExercises!!, item, item.plus(1))
                }
            } else {
                for (item in from downTo to + 1) {
                    Collections.swap(currentExercises!!, item, item.minus(1))
                }
            }
            exerciseListAdapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            currentExercises?.removeAt(position)
            exerciseListAdapter?.notifyItemRemoved(position)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState == ACTION_STATE_DRAG) {
                viewHolder?.itemView?.alpha = 0.5f
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.alpha = 1.0f
            isWorkoutChanged = true
        }
    }
    private val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkoutEditorBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!isWorkoutChanged) {
                    navController.navigateUp()
                } else {
                    showUnsavedChangesDialog()
                }
            }
        })

        initToolbar()

        setHasOptionsMenu(true)

        binding.initViews()

        displayContent()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        exerciseListViewModel.clearSelectedExercises()
    }

    private fun displayContent() {
        if (args.workoutId != NO_WORKOUT_ID) {
            displayExistingWorkout()
        } else {
            displayNewWorkout()
        }
    }

    private fun displayExistingWorkout() {
        lifecycleScope.launch {
            val currentWorkout = workoutEditorViewModel.getCurrentWorkout(args.workoutId)
            binding.toolbar.title = "Edit a Workout"
            binding.editWorkoutName.setText(currentWorkout?.workoutName)
            binding.editWorkoutDuration.setText(currentWorkout?.duration)
            displayExercises()
        }
    }

    private fun displayNewWorkout() {
        currentExercises = exerciseListViewModel.selectedExercises
        exerciseListAdapter?.submitList(currentExercises)
        binding.toolbar.menu.findItem(R.id.action_delete).isVisible = false
    }

    private fun displayExercises() {
        if (exerciseListViewModel.selectedExercises.isNotEmpty()) {
            currentExercises = workoutEditorViewModel.exerciseList?.plus(exerciseListViewModel.selectedExercises) as MutableList<ExerciseJson>?
            exerciseListAdapter?.submitList(currentExercises)
        } else {
            currentExercises = workoutEditorViewModel.exerciseList as MutableList<ExerciseJson>?
            exerciseListAdapter?.submitList(currentExercises)
        }
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationOnClickListener {
                if (!isWorkoutChanged) {
                    navController.navigateUp()
                } else {
                    showUnsavedChangesDialog()
                }
            }
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_delete -> {
                        showDeleteConfirmationDialog()
                        true
                    }
                    R.id.action_save -> {
                        saveWorkout()
                        true
                    }
                    else -> super.onOptionsItemSelected(item)
                }
            }
        }
    }

    private fun FragmentWorkoutEditorBinding.initViews() {
        exerciseListAdapter = ExerciseListLibraryAdapter(onClickListener)
        exercisesList.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = exerciseListAdapter
        }
        itemTouchHelper.attachToRecyclerView(exercisesList)
        addExercises.setOnClickListener {
            val action = WorkoutEditorFragmentDirections.actionWorkoutEditorFragmentToCategoryEditorFragment2()
            navController.navigate(action)
        }

        nameField.apply {
            setOnTouchListener(touchListener)
            setOnClickListener { showEnterNameDialog() }
        }
        durationField.apply {
            setOnTouchListener(touchListener)
            setOnClickListener { showChooseDurationDialog() }
        }
        durationField.apply { setOnTouchListener(touchListener) }
        addExercises.apply { setOnTouchListener(touchListener) }
    }

    private fun saveWorkout() {
        val workoutName = binding.editWorkoutName.text.toString().trim()
        val workoutDuration = binding.editWorkoutDuration.text.toString().trim()
        if (args.workoutId == NO_WORKOUT_ID) {
            saveNewWorkout(workoutName, workoutDuration)
        } else {
            updateWorkout(workoutName, workoutDuration)
        }
    }

    private fun saveNewWorkout(name: String, duration: String) {
        if (!isNameEmpty(name)) {
            currentExercises?.let { workoutEditorViewModel.addNewWorkout(name, duration, it) }
            navController.navigateUp()
        } else return
    }

    private fun updateWorkout(name: String, duration: String) {
        if (isWorkoutChanged) {
            if (!isNameEmpty(name)) {
                currentExercises?.let { workoutEditorViewModel.updateWorkout(name, duration, it) }
                navController.navigateUp()
            } else return
        } else {
            navController.navigateUp()
        }
    }

    private fun isNameEmpty(name: String): Boolean {
        return when {
            name.isEmpty() -> {
                Toast.makeText(requireContext(), "Name can't be empty!", Toast.LENGTH_LONG).show()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Delete")
            setMessage("Delete this workout?")
            setPositiveButton("Delete") { _, _ ->
                deleteWorkout(workoutEditorViewModel.currentWorkout ?: return@setPositiveButton)
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun deleteWorkout(workout: Workout) {
        workoutEditorViewModel.deleteWorkout(workout)
        exerciseListViewModel.clearSelectedExercises()
        navController.navigateUp()
    }

    private fun showUnsavedChangesDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Save changes")
            setMessage("Discard your changes and quit editing?")
            setPositiveButton("Discard") { _, _ -> navController.navigateUp() }
            setNegativeButton("Keep editing") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    private fun showEnterNameDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val layout = layoutInflater.inflate((R.layout.alert_dialog_edittext), null)
        val editText = layout.findViewById<EditText>(R.id.editText)
        if (binding.editWorkoutName.text.isNotEmpty()) {
            editText.setText(binding.editWorkoutName.text)
        }
        builder.apply {
            setTitle("Enter workout name")
            setView(layout)
            setPositiveButton("OK") { dialog, _ ->
                binding.editWorkoutName.text = editText.text
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    private fun showChooseDurationDialog() {
        val durationList = arrayOf("10", "20", "30", "40", "50", "60")
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Set duration")
            setSingleChoiceItems(durationList, 1) { dialog, which ->
                val selectedDuration = durationList[which]
                binding.editWorkoutDuration.text = "$selectedDuration minutes"
                dialog.dismiss()
            }
            show()
        }
    }
}