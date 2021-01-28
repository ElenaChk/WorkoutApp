package com.example.workout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.data.model.ExerciseJson
import com.example.workout.databinding.ItemSelectedExerciseBinding

class SelectedExercisesAdapter(private val onClickListener: LibraryOnClickListener):
    ListAdapter<ExerciseJson, SelectedExercisesAdapter.ViewHolder>(LibraryDiffCallback){

    class ViewHolder(
        private val binding: ItemSelectedExerciseBinding,
        private val onClickListener: LibraryOnClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseJson) {
            binding.root.setOnClickListener { onClickListener.onClick(exercise) }
            binding.tvItemExercise.text = exercise.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSelectedExerciseBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }
}