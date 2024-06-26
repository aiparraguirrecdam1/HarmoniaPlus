package com.mariana.harmonia.activities.otros

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mariana.harmonia.R

class RankingReciclerViewAdapter(private val rankingList: List<Map<String, Any>>) : RecyclerView.Adapter<RankingReciclerViewAdapter.RankingViewHolder>() {

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewIndex: TextView = itemView.findViewById(R.id.textViewIndex)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewScore: TextView = itemView.findViewById(R.id.textViewScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val item = rankingList[position]
        holder.textViewIndex.text = (position + 1).toString()
        holder.textViewIndex.setTypeface(null, Typeface.BOLD)

        holder.textViewName.text = item["nombre"].toString()
        holder.textViewName.setTypeface(null, Typeface.BOLD)

        // No poner en negrita las notas
        holder.textViewScore.text = item["notas"].toString()
        holder.textViewScore.setTypeface(null, Typeface.BOLD)
    }

    override fun getItemCount(): Int {
        return rankingList.size
    }
}
