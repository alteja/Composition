package com.example.composition.domain.entity

data class GameSettings(

    val maxSummValue: Int,
    val minCountOfRightAnswers: Int,
    val minPersentOfRightAnswers: Int,
    val gameTimeImSeconds: Int

)