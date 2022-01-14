package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(

    val maxSummValue: Int,
    val minCountOfRightAnswers: Int,
    val minPersentOfRightAnswers: Int,
    val gameTimeImSeconds: Int


): Parcelable{

}