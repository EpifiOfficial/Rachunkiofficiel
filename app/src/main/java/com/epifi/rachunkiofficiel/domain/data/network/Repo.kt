package com.epifi.rachunkiofficiel.domain.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.epifi.rachunkiofficiel.Models.WalletModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Repo {
    private lateinit var auth: FirebaseAuth

    fun getWalletData():LiveData<MutableList<WalletModel>>{
        auth = Firebase.auth
        val uID = FirebaseAuth.getInstance().currentUser!!.uid




        val mutableData = MutableLiveData<MutableList<WalletModel>>()
        FirebaseFirestore.getInstance().collection("Wallets").orderBy("WalletId").startAt(uID).endAt("$uID\uf8ff").get().addOnSuccessListener {
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