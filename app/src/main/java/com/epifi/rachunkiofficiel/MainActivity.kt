package com.epifi.rachunkiofficiel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.epifi.rachunkiofficiel.Adapters.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private var titleList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        postToList()
        val viewPager = findViewById<ViewPager2>(R.id.VPWallets)
        viewPager.adapter = ViewPagerAdapter(titleList,amountList)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

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