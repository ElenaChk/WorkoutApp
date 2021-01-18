package com.example.workout.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.workout.databinding.BottomSheetDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetDetailsFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            behavior = BottomSheetBehavior.from(bottomSheet!!)
            setupFullHeight(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exerciseName = arguments?.getString("name")
        val exerciseDescription = arguments?.getString("description")
        val imageUrl = arguments?.getString("image")
        binding.exerciseName.text = exerciseName
        binding.exerciseDescription.text = exerciseDescription
        if (imageUrl != null) {
            Glide.with(requireContext()).load(imageUrl).into(binding.exerciseImage)
        } else {
            binding.exerciseImage.visibility = View.GONE
        }
        binding.close.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            dialog.cancel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}