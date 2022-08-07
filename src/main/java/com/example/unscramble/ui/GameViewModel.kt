package com.example.unscramble.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameViewModel: ViewModel() {

    private val _score =  MutableLiveData(0)
    val score:LiveData<Int>
        get() = _score


    private var wordsList: MutableList<String> = mutableListOf()

    private lateinit var currentWord: String // will initialise later

    // backing property
    private val _currentScrambledWord = MutableLiveData<String>() //object wrapper
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    private var _currentWordCount =  MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    init{
        getNextWord()
    }

     fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord =  currentWord.toCharArray()
        tempWord.shuffle()

        // keep shuffling until shuffled word isn't same as the original word
        while (String(tempWord).equals(currentWord,false)){
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)){
            getNextWord()
        } else{
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc() // increases by 1
            wordsList.add(currentWord)
        }
    }

     fun nextWord(): Boolean{
        if(currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            return true
        }
        else return false
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord:String):Boolean{
        if(playerWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
}