package me.chongieball.project.roomandroiddemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_word.view.*
import me.chongieball.project.roomandroiddemo.data.entity.Word

class WordListAdapter(context: Context, private val words: MutableList<Word>): RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    class ViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        fun bind(word: Word?) {
            val wordString = if (word == null) "No Word" else word.word
            itemView.tv_word.text = wordString
        }
    }

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_word, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(words[position])
    }

    fun setWords(words: MutableList<Word>) {
        this.words.clear()
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = words.size

}