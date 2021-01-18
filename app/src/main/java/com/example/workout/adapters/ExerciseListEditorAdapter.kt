package com.example.workout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.data.model.ExerciseJson
import com.example.workout.databinding.ItemExerciseEditorBinding

class ExerciseListEditorAdapter(private val EditorOnClickListener: EditorOnClickListener) :
    ListAdapter<ExerciseJson, ExerciseListEditorAdapter.ViewHolder>(EditorDiffCallback) {

    class ViewHolder(
        private val binding: ItemExerciseEditorBinding,
        private val EditorOnClickListener: EditorOnClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseJson) {
            binding.root.setOnClickListener { EditorOnClickListener.onClick(exercise) }
            binding.tvItemExercise.text = exercise.name
            binding.checkbox.setOnClickListener {
                if (binding.checkbox.isChecked) {
                    EditorOnClickListener.onCheckboxClick(exercise, true)
                } else {
                    EditorOnClickListener.onCheckboxClick(exercise, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciseEditorBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, EditorOnClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout)
    }
}

object EditorDiffCallback : DiffUtil.ItemCallback<ExerciseJson>() {
    override fun areItemsTheSame(oldItem: ExerciseJson, newItem: ExerciseJson): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExerciseJson, newItem: ExerciseJson): Boolean {
        return oldItem.id == newItem.id
    }

}

interface EditorOnClickListener {
    fun onClick(exercise: ExerciseJson)
    fun onCheckboxClick(exercise: ExerciseJson, isChecked: Boolean)
}

