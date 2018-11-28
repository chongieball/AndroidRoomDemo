package me.chongieball.project.roomandroiddemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import me.chongieball.project.roomandroiddemo.data.entity.Word
import me.chongieball.project.roomandroiddemo.data.repository.WordRepository

class WordViewModel(app: Application) : AndroidViewModel(app) {

    private var wordRepository: WordRepository = WordRepository(app)
    var allWord: LiveData<List<Word>>?

    init {
        allWord = wordRepository.allWords
    }

    fun insert(word: Word) {
        wordRepository.insert(word)
    }
}