package com.example.workout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.databinding.ItemCategoryEditorBinding
import com.example.workout.models.CategoryWithImage

class CategoriesEditorAdapter(
    private val onClickListenerCategory: OnClickListenerCategoryEditor
) : ListAdapter<CategoryWithImage, CategoriesEditorAdapter.ViewHolder>(CategoryEditorDiffCallback) {

    class ViewHolder(
        private val binding: ItemCategoryEditorBinding,
        private val onClickListenerCategory: OnClickListenerCategoryEditor
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryWithImage) {
            binding.root.setOnClickListener { onClickListenerCategory.onClick(category) }
            binding.categoryName.text = category.categoryName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryEditorBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onClickListenerCategory)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}

object CategoryEditorDiffCallback : DiffUtil.ItemCallback<CategoryWithImage>() {
    override fun areItemsTheSame(oldItem: CategoryWithImage, newItem: CategoryWithImage): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryWithImage, newItem: CategoryWithImage): Boolean {
        return oldItem.id == newItem.id
    }
}

interface OnClickListenerCategoryEditor {
    fun onClick(category: CategoryWithImage)
}
