package com.epifi.rachunkiofficiel.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.epifi.rachunkiofficiel.R

class RecyclerViewAdapter (private val walletTitle:List<String>,private val walletAmount:List<String>):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val itemWalletTitle : TextView = itemView.findViewById(R.id.TvWalletTitle)
        val itemWalletAmount : TextView = itemView.findViewById(R.id.TvWalletAmount)

        init {

          }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_wallet,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.itemWalletTitle.text = walletTitle[position]
        holder.itemWalletAmount.text = walletAmount[position]

    }

    override fun getItemCount(): Int {
       return walletTitle.size
    }

}