package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface onOptionClickListener{
    fun onOptionClick(option: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView,count:Int){

    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )

}

@BindingAdapter("requiredScore")
fun bindingRequiredScore(textView: TextView, score: Int){

    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )

}

@BindingAdapter("requiredPercentage")
fun bindingRequiredPercentage(textView: TextView, percent: Int){

    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percent
    )

}

@BindingAdapter("scorePercentege")
fun bindingscorePercentege(textView: TextView, gameResult: GameResult){

    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPersentFromAnswers(gameResult)
    )

}

fun getPersentFromAnswers(gameResult: GameResult) = with(gameResult) {

    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("resultEmojy")
fun bindResultEmojy(imageView: ImageView, winner: Boolean){
    imageView.setImageResource(getSmileResId(winner))
}

fun getSmileResId(winner: Boolean): Int{
    return if (winner) {
        R.drawable.smile
    } else {
        R.drawable.sad
    }
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean){
    textView.setTextColor(getColorByState(textView.context,enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean){
    val color = getColorByState(progressBar.context,enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberAsText")
fun bindnumberAsText(textView: TextView, number:Int){
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOptionClickListener(textView: TextView, clickListener: onOptionClickListener){
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}


private fun getColorByState(context:Context, goodState: Boolean): Int{

    val colorResIt = if (goodState){
        android.R.color.holo_green_light
    }else{
        android.R.color.holo_red_light
    }

    return ContextCompat.getColor(context, colorResIt)

}