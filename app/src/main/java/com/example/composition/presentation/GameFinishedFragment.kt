package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.GameFinishedFragmentBinding
import com.example.composition.domain.entity.GameResult
import java.lang.RuntimeException

class GameFinishedFragment: Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: GameFinishedFragmentBinding? = null
    private val binding: GameFinishedFragmentBinding
        get() = _binding ?: throw RuntimeException("GameFinishedFragment == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFinishedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      setupOnClickListeners()
      binding.gameResult = args.gameResult

    }

    private fun setupOnClickListeners(){

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }
    private fun retryGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}