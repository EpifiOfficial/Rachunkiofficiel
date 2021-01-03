package com.epifi.rachunkiofficiel.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.epifi.rachunkiofficiel.Models.WalletModel
import com.epifi.rachunkiofficiel.R

class ViewPagerAdapter(private val walletTitle:List<String>,private  val walletAmount:List<String>)
    :RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>(){


    inner class ViewPagerHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val itemWalletTitle : TextView = itemView.findViewById(R.id.TvWalletTitle)
        val itemWalletAmount : TextView = itemView.findViewById(R.id.TvWalletAmount)
        init {

        }





    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewPagerHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_wallet,parent,false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewPagerHolder, position: Int) {
        holder.itemWalletTitle.text = walletTitle[position]
        holder.itemWalletAmount.text = walletAmount[position]
    }

    override fun getItemCount(): Int {
        return walletTitle.size
    }
}