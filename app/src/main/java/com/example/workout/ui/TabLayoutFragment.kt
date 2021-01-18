package com.example.workout.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.workout.R
import com.example.workout.adapters.TabsPagerAdapter
import com.example.workout.databinding.FragmentTabLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutFragment : Fragment() {
    private var _binding: FragmentTabLayoutBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("Binding is null")

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (position == 1) {
                binding.fabButton.hide()
            } else {
                binding.fabButton.show()
            }
            super.onPageSelected(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabLayoutBinding.inflate(inflater, container, false)
        binding.tabLayout.tabMode = TabLayout.MODE_FIXED
        val adapter = TabsPagerAdapter(this@TabLayoutFragment)
        binding.tabsViewpager.adapter = adapter
        binding.tabsViewpager.isUserInputEnabled = true

        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "My Workouts"
                }
                1 -> {
                    tab.text = "Library"
                }
            }
        }.attach()

        binding.fabButton.setOnClickListener {
            val action = TabLayoutFragmentDirections.actionTabLayoutFragmentToWorkoutEditorFragment()
            it.findNavController().navigate(action)
        }

        binding.tabsViewpager.registerOnPageChangeCallback(onPageChangeCallback)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.tabsViewpager.unregisterOnPageChangeCallback(onPageChangeCallback)
        _binding = null
    }
}