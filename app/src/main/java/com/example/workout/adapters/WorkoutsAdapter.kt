package com.example.workout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.data.database.Workout
import com.example.workout.databinding.ItemWorkoutBinding

class WorkoutsAdapter (private val onClickListenerWorkout: OnClickListenerWorkout
) : ListAdapter<Workout, WorkoutsAdapter.ViewHolder>(WorkoutsDiffCallback) {
    class ViewHolder(
        private val binding: ItemWorkoutBinding,
        private val onClickListenerWorkout: OnClickListenerWorkout
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(workout: Workout){
            binding.root.setOnClickListener {onClickListenerWorkout.onClick(workout)}
            binding.workoutName.text = workout.workoutName
            binding.exercisesNumber.text = "${workout.exercisesList?.size} exercises"
            binding.duration.text = "${workout.duration}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onClickListenerWorkout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }
}

object WorkoutsDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.workoutId == newItem.workoutId
    }

}

interface OnClickListenerWorkout {
    fun onClick(workout: Workout)
}