package com.example.workout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.data.model.ExerciseJson
import com.example.workout.databinding.ItemExerciseLibraryBinding

class ExerciseListLibraryAdapter(private val libraryOnClickListener: LibraryOnClickListener) :
    ListAdapter<ExerciseJson, ExerciseListLibraryAdapter.ViewHolder>(LibraryDiffCallback) {

    class ViewHolder(
        private val binding: ItemExerciseLibraryBinding,
        private val libraryOnClickListener: LibraryOnClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseJson) {
            binding.root.setOnClickListener { libraryOnClickListener.onClick(exercise) }
            binding.tvItemExercise.text = exercise.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciseLibraryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, libraryOnClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }
}

object LibraryDiffCallback : DiffUtil.ItemCallback<ExerciseJson>() {
    override fun areItemsTheSame(oldItem: ExerciseJson, newItem: ExerciseJson): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExerciseJson, newItem: ExerciseJson): Boolean {
        return oldItem.id == newItem.id
    }

}

interface LibraryOnClickListener {
    fun onClick(exercise: ExerciseJson)
}

