package com.rojer_ko.translator.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rojer_ko.translator.R
import com.rojer_ko.translator.data.model.SearchResult
import kotlinx.android.extensions.LayoutContainer

class MainAdapter(private val onItemClickListener: OnListItemClickLestener, private var items: List<SearchResult>): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return MainViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainViewHolder(override val containerView: View):
    RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val translation = containerView.findViewById<TextView>(R.id.translationTextView)
        private val meanings = containerView.findViewById<TextView>(R.id.meaningTextView)

        fun bind(data: SearchResult) {
            translation.text = data.text
            meanings.text = data.meanings?.get(0)?.translation?.translation
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(data)
            }
        }
    }

    interface OnListItemClickLestener{
        fun onItemClick(data: SearchResult)
    }

    fun setData(data: List<SearchResult>){
        this.items = data
        notifyDataSetChanged()
    }
}