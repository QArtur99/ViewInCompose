package com.artf.composeinview.ui.component

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.artf.composeinview.databinding.ComponentCounterBinding


class CounterView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    companion object {
        const val SuperState = "SuperState"
        const val Result = "Result"
    }

    var binding: ComponentCounterBinding
    var result: Int = 0

    init {
        binding = ComponentCounterBinding.inflate(LayoutInflater.from(context), this, true)
        binding.down.setOnClickListener {
            subtractPoint()
        }
        binding.up.setOnClickListener {
            addPoint()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        val bundle = Bundle()
        bundle.putInt(Result, result)
        bundle.putParcelable(SuperState, super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as Bundle?)?.let {
            result = it.getInt(Result)
            binding.result.text = result.toString()
            super.onRestoreInstanceState(it.getParcelable(SuperState))
        }
    }

    private fun addPoint() {
        result++
        binding.result.text = result.toString()
    }

    private fun subtractPoint() {
        if (result == 0) return
        result--
        binding.result.text = result.toString()
    }

    fun reset() {
        result = 0
        binding.result.text = result.toString()
    }
}