package com.example.composition.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.GameFragmentBinding
import com.example.composition.domain.entity.GameResult
import java.lang.RuntimeException
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {

    private  var _binding: GameFragmentBinding? = null
    private val binding: GameFragmentBinding
        get() = _binding ?: throw RuntimeException("GameFragment")

    private val viewModelFactory by lazy {
        val args = GameFragmentArgs.fromBundle(requireArguments())
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()

    }


    private fun observeViewModel(){

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }

    }



    private fun launchGameFinishedFragment(gameResult: GameResult) {

        findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}