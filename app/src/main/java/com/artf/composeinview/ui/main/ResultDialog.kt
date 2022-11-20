package com.artf.composeinview.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.artf.composeinview.databinding.ComponentCounterBinding
import com.artf.composeinview.databinding.DialogResultBinding
import com.artf.composeinview.ui.component.CounterView
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
                    AlertDialog(
                        modifier = Modifier.fillMaxWidth(),
                        onDismissRequest = { dismiss() },
                        text = {
                            Text(
                                text = "Compose",
                                textAlign = TextAlign.Center
                            )
                        },
                        buttons = {
                            Column {
                                XMLCounter()
                                XMLCounterBinding()
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

@Composable
fun XMLCounter() {
    var resultState by remember { mutableStateOf(0) }
    AndroidView(
        factory = { context ->
            CounterView(context).apply {
                // Example of View -> Compose communication
                resultCallback = {
                    resultState = it
                }
            }
        },
        update = { counterView ->
            // Example of Compose -> View communication
            counterView.binding.result.text = resultState.toString()
        }
    )
}

@Composable
fun XMLCounterBinding() {
    AndroidViewBinding(ComponentCounterBinding::inflate) {
        down.setOnClickListener { /*...*/ }
        up.setOnClickListener { /*...*/ }
    }
}