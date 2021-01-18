package com.example.workout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workout.models.CategoryWithImage
import com.example.workout.databinding.ItemCategoryLibraryBinding


class CategoriesAdapter(
    private val onClickListenerCategory: OnClickListenerCategory
) : ListAdapter<CategoryWithImage, CategoriesAdapter.ViewHolder>(CategoryLibraryDiffCallback) {

    class ViewHolder(
        private val binding: ItemCategoryLibraryBinding,
        private val onClickListenerCategory: OnClickListenerCategory
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryWithImage) {
            binding.root.setOnClickListener { onClickListenerCategory.onClick(category) }
            binding.categoryName.text = category.categoryName
            binding.imageCategory.setImageResource(category.categoryImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryLibraryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onClickListenerCategory)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}

object CategoryLibraryDiffCallback : DiffUtil.ItemCallback<CategoryWithImage>() {
    override fun areItemsTheSame(oldItem: CategoryWithImage, newItem: CategoryWithImage): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryWithImage, newItem: CategoryWithImage): Boolean {
        return oldItem.id == newItem.id
    }
}

interface OnClickListenerCategory {
    fun onClick(category: CategoryWithImage)
}
