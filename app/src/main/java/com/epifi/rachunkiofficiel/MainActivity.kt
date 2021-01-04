package com.epifi.rachunkiofficiel

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epifi.rachunkiofficiel.Adapters.RecyclerViewAdapter
import com.takusemba.multisnaprecyclerview.MultiSnapHelper
import java.security.AccessController.getContext
import java.text.FieldPosition
import java.time.OffsetTime


class MainActivity : AppCompatActivity() {


    //Arrays
    private var titleList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        postToList()


        recyclerView = findViewById<RecyclerView>(R.id.RVWallets);
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter =RecyclerViewAdapter(titleList, amountList)




        //Adding multisnap to the recyclerview
        val multiSnapHelper = MultiSnapHelper(MultiSnapHelper.DEFAULT_GRAVITY, 1, 200F)
        multiSnapHelper.attachToRecyclerView(recyclerView)

    }


    private fun addToList(title: String, amountWallet: String){
        titleList.add(title)
        amountList.add(amountWallet)
    }



    private fun postToList(){
            addToList("Title$", "amount$")

    }







}