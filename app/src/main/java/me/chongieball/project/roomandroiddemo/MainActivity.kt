package me.chongieball.project.roomandroiddemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.chongieball.project.roomandroiddemo.data.entity.Word
import me.chongieball.project.roomandroiddemo.viewmodel.WordViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    }

    private lateinit var wordViewModel: WordViewModel

    private val words: MutableList<Word> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewWordActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }

        val adapter = WordListAdapter(this, words)
        rv_words.adapter = adapter
        rv_words.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        wordViewModel.allWord?.observe(this, Observer {
            adapter.setWords(it as MutableList<Word>)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val word = Word(data?.getStringExtra(NewWordActivity.EXTRA_REPLY)!!)
            wordViewModel.insert(word)
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()
        }
    }
}
