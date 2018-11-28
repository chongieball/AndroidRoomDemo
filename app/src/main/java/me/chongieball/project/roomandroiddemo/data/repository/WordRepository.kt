package me.chongieball.project.roomandroiddemo.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import me.chongieball.project.roomandroiddemo.data.dao.WordDao
import me.chongieball.project.roomandroiddemo.data.db.WordRoomDatabase
import me.chongieball.project.roomandroiddemo.data.entity.Word

class WordRepository(application: Application) {

    private var wordDao: WordDao?
    var allWords: LiveData<List<Word>>?

    init {
        val db : WordRoomDatabase? = WordRoomDatabase.getDatabase(application)
        wordDao = db?.wordDao()
        allWords = wordDao?.getAllWords()
    }

    fun insert(word: Word) {
        InsertAsyncTask(wordDao!!).execute(word)
    }

    companion object {
        class InsertAsyncTask(dao: WordDao) : AsyncTask<Word, Void, Void>() {
            private var asyncTaskDao: WordDao = dao

            override fun doInBackground(vararg params: Word?): Void? {
                asyncTaskDao.insert(params[0]!!)
                return null
            }
        }
    }
}