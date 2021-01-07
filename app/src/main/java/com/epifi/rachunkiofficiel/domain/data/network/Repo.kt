package com.epifi.rachunkiofficiel.domain.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.epifi.rachunkiofficiel.Models.WalletModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repo {
    fun getWalletData():LiveData<MutableList<WalletModel>>{
        val mutableData = MutableLiveData<MutableList<WalletModel>>()
        FirebaseFirestore.getInstance().collection("Wallets").get().addOnSuccessListener {
            val listData = mutableListOf<WalletModel>()
            for (document in it){
                val title = document.getString("walletTitle")
                val amount = document.getString("walletAmount")
                val wallet = WalletModel(title!!,amount!!)
                listData.add(wallet)
            }
            mutableData.value = listData
        }
        return mutableData
    }
}