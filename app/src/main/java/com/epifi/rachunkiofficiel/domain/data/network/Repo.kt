package com.epifi.rachunkiofficiel.domain.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.epifi.rachunkiofficiel.Models.WalletModel
import com.google.firebase.firestore.FirebaseFirestore

class Repo {
    fun getWalletData():LiveData<MutableList<WalletModel>>{
        val mutableData = MutableLiveData<MutableList<WalletModel>>()
        FirebaseFirestore.getInstance().collection("Wallets").get().addOnSuccessListener {
            val listData = mutableListOf<WalletModel>()
            for (document in it){
                val amount = document.getString("WalletAmount")
                val title = document.getString("WalletTitle")
                val wallet = WalletModel(amount.toString(), title.toString())
                listData.add(wallet)
            }
            mutableData.value = listData
        }
        return mutableData
    }
}