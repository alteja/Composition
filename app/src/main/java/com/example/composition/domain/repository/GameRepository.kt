package com.example.composition.domain.repository

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question

interface GameRepository {

    fun GenerateQuestion(
        maxSummValue: Int,
        countOfOptions: Int
    ): Question

    fun GetGameSettings(level: Level): GameSettings

}