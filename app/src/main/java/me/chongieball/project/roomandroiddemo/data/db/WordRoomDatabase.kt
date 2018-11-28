package me.chongieball.project.roomandroiddemo.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import me.chongieball.project.roomandroiddemo.data.dao.WordDao
import me.chongieball.project.roomandroiddemo.data.entity.Word
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.annotation.NonNull



@Database(entities = [Word::class], version = 1)
abstract class WordRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

        fun getDatabase(context: Context) : WordRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(WordRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, WordRoomDatabase::class.java, "word_database")
                        .addCallback(roomDatabaseCallback)
                        .build()
                }
            }

            return INSTANCE
        }

        class PopulateDbAsync(db: WordRoomDatabase?): AsyncTask<Void, Void, Void>() {

            private val dao = db?.wordDao()

            override fun doInBackground(vararg params: Void?): Void? {
                dao?.deleteAll()
                val word = Word("Hello")
                dao?.insert(word)

                val otherWord = word.copy("World")
                dao?.insert(otherWord)

                return null
            }
        }
    }

    abstract fun wordDao() : WordDao
}