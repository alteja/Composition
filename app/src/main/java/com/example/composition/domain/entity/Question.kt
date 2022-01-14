package com.example.composition.domain.entity

data class Question (

    val summ: Int,
    val visibleNumber: Int,
    val options: List<Int>

){
    val rightAnswer: Int
    get() = summ - visibleNumber
}