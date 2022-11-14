package com.artf.composeinview.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.artf.composeinview.databinding.DialogResultBinding

class ResultDialog : DialogFragment() {

    companion object {
        val TAG = ResultDialog::class.simpleName
    }

    private val viewModel by lazy {
        ViewModelProvider(requireParentFragment())[MainViewModel::class.java]
    }

    private lateinit var binding: DialogResultBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogResultBinding.inflate(layoutInflater)
        viewModel.msg.observe(this) {
            binding.body.text = it
        }
        binding.buttonCancel.setOnClickListener {
            viewModel.setReset(true)
            dismiss()
        }
        binding.buttonOk.setOnClickListener {
            dismiss()
        }
        val dialog = AlertDialog.Builder(requireContext()).setView(binding.root).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}