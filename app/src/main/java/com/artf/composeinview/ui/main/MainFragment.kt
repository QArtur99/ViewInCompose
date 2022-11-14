package com.artf.composeinview.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.artf.composeinview.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        viewModel.reset.observe(viewLifecycleOwner) {
            it?.let {
                binding.counterA.reset()
                binding.counterB.reset()
                viewModel.setReset(null)
            }
        }

        binding.buttonSubmit.setOnClickListener {
            viewModel.setMsg(binding.counterA.result, binding.counterB.result)
            ResultDialog().show(childFragmentManager, ResultDialog.TAG)
        }
        return binding.root
    }

}