package com.example.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
    ): ViewModel() {

    private lateinit var gameSettings: GameSettings

    private val context = application

    private val repository = GameRepositoryImpl

    private val _formattedTime = MutableLiveData<String>()
    val formatted: LiveData<String>
    get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _persentOfRightAnswers = MutableLiveData<Int>()
    val persentOfRightAnswers: LiveData<Int>
    get() = _persentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _minPersent = MutableLiveData<Int>()
    val minPersent: LiveData<Int>
        get() = _minPersent


    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private var timer:CountDownTimer? = null

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    init {
        startGame()
    }

    private fun startGame(){

        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings(){
        this.gameSettings = getGameSettingsUseCase(level)
        _minPersent.value = gameSettings.minPersentOfRightAnswers
    }
    private fun startTimer(){

        timer = object : CountDownTimer(
            gameSettings.gameTimeImSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()

    }

    private fun finishGame(){

        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    fun chooseAnswer(number: Int){

        checkAnswer(number)
        updateProgress()
        generateQuestion()

    }

    private fun checkAnswer(number: Int){

        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun updateProgress(){
        val percent = calculatePersentOfRightAnswers()
        _persentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPersentOfRightAnswers

    }

    private fun calculatePersentOfRightAnswers(): Int{

        if (countOfQuestions==0){
            return 0
        }

        return (countOfRightAnswers / countOfQuestions.toDouble() * 100).toInt()
    }

    private fun generateQuestion(){

        _question.value = generateQuestionUseCase(gameSettings.maxSummValue)

    }

    private fun formatTime(millisUntilFinished: Long): String{

        val seconds = millisUntilFinished/ MILLIS_IN_SECONDS
        val minutes = seconds/SECONDS_IN_MINUTE
        return String.format("%02d: %02d", minutes, seconds)

    }

     companion object {
         private const val MILLIS_IN_SECONDS = 1000L
         private const val SECONDS_IN_MINUTE = 60
     }
}