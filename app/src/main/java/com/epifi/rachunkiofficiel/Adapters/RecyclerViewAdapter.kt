package com.epifi.rachunkiofficiel.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.epifi.rachunkiofficiel.Models.WalletModel
import com.epifi.rachunkiofficiel.R


class RecyclerViewAdapter(private val context:Context):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    var selectedItemPos = -1
    var lastItemSelectedPos = -1
    private var walletsList = mutableListOf<WalletModel>()

    fun setListWallets(wallets:MutableList<WalletModel>){
        walletsList = wallets
    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemWalletTitle : TextView = itemView.findViewById(R.id.TvWalletTitle)
        val itemWalletAmount : TextView = itemView.findViewById(R.id.TvWalletAmount)
        val CVBackground: CardView = itemView.findViewById(R.id.CvWallet)

        fun bindView(wallet:WalletModel){
            itemWalletTitle.text = wallet.WalletTitle
            itemWalletAmount.text = wallet.WalletAmount


}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_wallet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val wallet = walletsList[position]
        holder.bindView(wallet)


        if (selectedItemPos == position) {
            holder.CVBackground.setCardBackgroundColor(Color.parseColor("#9900BCD4"))
        } else{
            holder.CVBackground.setCardBackgroundColor(Color.parseColor("#00BCD4"))

        }



        holder.itemView.setOnClickListener {
            selectedItemPos = position
            notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        if (walletsList.size>0){
            return walletsList.size
        }else{
           return 0
        }
    }
}