package com.artf.composeinview.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.artf.composeinview.R
import com.artf.composeinview.databinding.DialogResultBinding
import android.app.AlertDialog as AlertDialogWidget

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

        val view = ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val state = viewModel.msg.observeAsState()
                    AlertDialog(
                        onDismissRequest = { dismiss() },
                        modifier = Modifier.fillMaxWidth(),
                        title = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = state.value ?: ""
                            )
                        },
                        buttons = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(onClick = {
                                    viewModel.setReset(true)
                                    dismiss()
                                }) {
                                    Text(text = getString(R.string.reset))
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = { dismiss() }) {
                                    Text(text = getString(R.string.ok))
                                }
                            }
                        }
                    )
                }
            }
        }


        val dialog = AlertDialogWidget.Builder(requireContext()).setView(view).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}