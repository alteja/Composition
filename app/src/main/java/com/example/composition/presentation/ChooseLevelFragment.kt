package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.databinding.ChooseLevelFragmentBinding
import com.example.composition.domain.entity.Level
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {

    private var _binding: ChooseLevelFragmentBinding? = null
    private val binding: ChooseLevelFragmentBinding
        get() = _binding ?: throw RuntimeException("ChooseLevelFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChooseLevelFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonLevelTest.setOnClickListener {
                lounchGameFragment(Level.TEST)
            }

            buttonLevelEasy.setOnClickListener {
                lounchGameFragment(Level.EASY)
            }

            buttonLevelNormal.setOnClickListener {
                lounchGameFragment(Level.NORMAL)
            }

            buttonLevelHard.setOnClickListener {
                lounchGameFragment(Level.HARD)
            }
        }


    }

    private fun lounchGameFragment(level:Level){

        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   }