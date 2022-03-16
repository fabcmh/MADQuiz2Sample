package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MRTListAdapter(private val MRTList: List<MRT>) :
    RecyclerView.Adapter<MRTListAdapter.MRTViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MRTViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MRTViewHolder(view)
    }

    override fun onBindViewHolder(holder: MRTViewHolder, position: Int) {
        val mrtItem = MRTList[position]
        holder.mrtStationName.text =
            mrtItem.stationName
        holder.mrtStationCode.text =
            mrtItem.stationCode
        holder.mrtLineName.text =
            mrtItem.LineName
    }

    override fun getItemCount(): Int {
        return MRTList.size
    }

    class MRTViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mrtStationCode: TextView = itemView.findViewById(R.id.textViewStationCode)
        val mrtStationName: TextView = itemView.findViewById(R.id.textViewStationName)
        val mrtLineName: TextView = itemView.findViewById(R.id.textViewLineName)
    }
}
