package com.example.workout.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.workout.ui.CategoryLibraryFragment
import com.example.workout.ui.WorkoutsListFragment
import java.lang.IndexOutOfBoundsException


const val MY_WORKOUTS_PAGE_INDEX = 0
const val CATEGORIES_PAGE_INDEX = 1

class TabsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf(
        MY_WORKOUTS_PAGE_INDEX to {
            WorkoutsListFragment()
        },
        CATEGORIES_PAGE_INDEX to {
            CategoryLibraryFragment()
        }
    )

    override fun getItemCount(): Int {
        return tabFragmentCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}