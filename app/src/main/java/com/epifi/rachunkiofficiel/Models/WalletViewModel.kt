package com.epifi.rachunkiofficiel.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epifi.rachunkiofficiel.domain.data.network.Repo

class WalletViewModel: ViewModel() {
    private val repo = Repo()
    fun fetchWalletData():LiveData<MutableList<WalletModel>>{
        val mutableData =  MutableLiveData<MutableList<WalletModel>>()
        repo.getWalletData().observeForever{ walletList->
            mutableData.value = walletList
        }

        return mutableData

    }
}
