package com.epifi.rachunkiofficiel

import android.os.Bundle
import android.transition.Visibility
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epifi.rachunkiofficiel.Adapters.RecyclerViewAdapter
import com.epifi.rachunkiofficiel.Models.WalletViewModel
import com.google.android.gms.common.api.internal.ActivityLifecycleObserver.of
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.takusemba.multisnaprecyclerview.MultiSnapHelper
import es.dmoral.toasty.Toasty
import java.security.AccessController.getContext
import java.text.FieldPosition
import java.time.OffsetTime
import java.util.*


class MainActivity : AppCompatActivity() {


    //Arrays
    private var titleList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()

    //widgets
    lateinit var recyclerView:RecyclerView
    lateinit var btnAddWallet:Button
    lateinit var cellNewWallet:RelativeLayout
    lateinit var btn1 :Button
    lateinit var btn2 :Button
    lateinit var btn3 :Button
    lateinit var btn4 :Button
    lateinit var btn5 :Button
    lateinit var btn6 :Button
    lateinit var btn7 :Button
    lateinit var btn8 :Button
    lateinit var btn9 :Button
    lateinit var btn0 :Button
    lateinit var btnDelete :Button
    lateinit var btnPoint :Button
    lateinit var textTitle : TextView
    lateinit var textAmount: TextView
    lateinit var EtNote :EditText
    lateinit var amount:String
    lateinit var btnFinish:Button

    //adapter
    private lateinit var adapter: RecyclerViewAdapter
    //view model
    private val viewModel by lazy { ViewModelProviders.of(this).get(WalletViewModel::class.java) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        numberinterface()
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapter
        //Adding multisnap to the recyclerview
        val multiSnapHelper = MultiSnapHelper(MultiSnapHelper.DEFAULT_GRAVITY, 1, 200F)
        multiSnapHelper.attachToRecyclerView(recyclerView)
        //Button add wallet action
        btnAddWallet.setOnClickListener {
            //set layout visible
            cellNewWallet.visibility = View.VISIBLE
            newWallet()
        }
        //Finish button
        btnFinish.setOnClickListener {
            if (amount.isEmpty()&&EtNote.text.isEmpty()){
                Toasty.info(this, getString(R.string.fill_all_the_gaps), Toast.LENGTH_SHORT, true).show()
            }else{
                postToList()
                //set layout invisible
                cellNewWallet.visibility = View.INVISIBLE


            }
        }








    }

    fun observeData(){
        viewModel.fetchWalletData().observe(this, androidx.lifecycle.Observer {
            adapter.setListWallets(it)
            adapter.notifyDataSetChanged()
        })
    }









    private fun init(){
        adapter = RecyclerViewAdapter(this)
        amount = ""
        recyclerView = findViewById<RecyclerView>(R.id.RVWallets)
        btnAddWallet = findViewById<Button>(R.id.BtnAddWallet)
        cellNewWallet = findViewById<RelativeLayout>(R.id.RlNewWallet)
        btn1 = findViewById(R.id.Btn1)
        btn2 = findViewById(R.id.Btn2)
        btn3 = findViewById(R.id.Btn3)
        btn4 = findViewById(R.id.Btn4)
        btn5 = findViewById(R.id.Btn5)
        btn6 = findViewById(R.id.Btn6)
        btn7 = findViewById(R.id.Btn7)
        btn8 = findViewById(R.id.Btn8)
        btn9 = findViewById(R.id.Btn9)
        btn0 = findViewById(R.id.Btn0)
        btnDelete = findViewById(R.id.BtnDelete)
        btnPoint = findViewById(R.id.BtnPoint)
        btnFinish = findViewById(R.id.BtnFinish)
        EtNote = findViewById(R.id.EtNote)
        textTitle = findViewById(R.id.TvTitle)
        textAmount = findViewById(R.id.TvAmount)


    }


    private fun addToList(title: String, amountWallet: String){
        titleList.add(title)
        amountList.add(amountWallet)
    }



    private fun postToList(){
            addToList(EtNote.text.toString(),amount)


    }
    private fun uploadToFirestore(){
        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore
        val wallet = hashMapOf(
                "WalletAmount" to amount,
                "WalletName" to EtNote.text.toString()
        )


        // Add a new document with a generated ID

        db.collection("Wallets").add(wallet).
        addOnSuccessListener { documentReference ->


        }.addOnFailureListener { e ->


        }




    }

    private fun newIncome(){
        textTitle.text = getString(R.string.new_income)
        EtNote.hint = getString(R.string.income_note)
    }
    private fun newWallet(){
        textTitle.text = getString(R.string.new_account)
        EtNote.hint = getString(R.string.account_name)

    }

    private fun numberinterface(){

        btn0.setOnClickListener {
            amount = amount+"0"
            textAmount.text = amount
        }
        btn1.setOnClickListener {
            amount = amount+"1"
            textAmount.text = amount

        }
        btn2.setOnClickListener {
            amount = amount+"2"
            textAmount.text = amount

        }
        btn3.setOnClickListener {
            amount = amount+"3"
            textAmount.text = amount

        }
        btn4.setOnClickListener {
            amount = amount+"4"
            textAmount.text = amount

        }
        btn5.setOnClickListener {
            amount = amount+"5"
            textAmount.text = amount

        }
        btn6.setOnClickListener {
            amount = amount+"6"
            textAmount.text = amount

        }
        btn7.setOnClickListener {
            amount = amount+"7"
            textAmount.text = amount

        }
        btn8.setOnClickListener {
            amount = amount+"8"
            textAmount.text = amount

        }
        btn9.setOnClickListener {
            amount = amount+"9"
            textAmount.text = amount

        }
        btnDelete.setOnClickListener {
            if (amount.isEmpty()){

            }else{
                amount = amount.dropLast(1)
                textAmount.text = amount

            }

        }
        btnPoint.setOnClickListener {
            amount = amount+"."
            textAmount.text = amount

        }
    }








}