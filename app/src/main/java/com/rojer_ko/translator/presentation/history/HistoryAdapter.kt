package com.rojer_ko.translator.presentation.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.SearchResult
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter(private var items: List<SearchResult>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(data: SearchResult){
            itemView.historyTranslationTextView.text = data.text
            itemView.historyMeaningTextView.text = data.meanings!![0].translation!!.translation
        }
    }

    fun setData(data: List<SearchResult>){
        this.items = data
        notifyDataSetChanged()
    }
}