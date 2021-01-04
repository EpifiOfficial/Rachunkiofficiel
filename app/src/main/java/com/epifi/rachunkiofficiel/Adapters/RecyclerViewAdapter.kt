package com.epifi.rachunkiofficiel.Adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.epifi.rachunkiofficiel.R


class RecyclerViewAdapter(
    private val walletTitle: List<String>,
    private val walletAmount: List<String>
):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    var selectedItemPos = -1
    var lastItemSelectedPos = -1
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemWalletTitle : TextView = itemView.findViewById(R.id.TvWalletTitle)
        val itemWalletAmount : TextView = itemView.findViewById(R.id.TvWalletAmount)
        val CVBackground: CardView = itemView.findViewById(R.id.CvWallet)
/*

        init {
            selectedItemPos = adapterPosition
            if(lastItemSelectedPos == -1)
                lastItemSelectedPos = selectedItemPos
            else {
                notifyItemChanged(lastItemSelectedPos)
                lastItemSelectedPos = selectedItemPos
            }
            notifyItemChanged(selectedItemPos)

          }*/

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_wallet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.itemWalletTitle.text = walletTitle[position]
        holder.itemWalletAmount.text = walletAmount[position]

       /* if(position == selectedItemPos){
            holder.CVBackground.setCardBackgroundColor(R.color.accentColorCreativeClicked)
        } else{
            holder.CVBackground.setCardBackgroundColor(R.color.accentColorCreative)

        }*/
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
       return walletTitle.size
    }

}