package com.epifi.rachunkiofficiel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.epifi.rachunkiofficiel.Adapters.RecyclerViewAdapter
import com.epifi.rachunkiofficiel.Adapters.ViewPagerAdapter
import com.epifi.rachunkiofficiel.Models.WalletModel

class MainActivity : AppCompatActivity() {

    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null

    //Arrays
    private var titleList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        postToList()


        layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.RVWallets);
        adapter = RecyclerViewAdapter(titleList,amountList)
        recyclerView.adapter = adapter










    }
    private fun addToList(title:String,amountWallet:String){
        titleList.add(title)
        amountList.add(amountWallet)
    }
    private fun postToList(){
        for (i in 1..5){
            addToList("Title$i","amount$i")
        }
    }
}