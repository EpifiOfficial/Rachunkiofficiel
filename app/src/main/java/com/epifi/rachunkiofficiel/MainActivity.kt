package com.epifi.rachunkiofficiel

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epifi.rachunkiofficiel.Adapters.RecyclerViewAdapter
import com.epifi.rachunkiofficiel.Models.WalletViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.takusemba.multisnaprecyclerview.MultiSnapHelper
import es.dmoral.toasty.Toasty
import java.util.*
import kotlin.concurrent.schedule


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
    lateinit var btnOutcome:Button
    lateinit var btnIncome:Button
    lateinit var btnClose:TextView


    //adapter
    private lateinit var adapter: RecyclerViewAdapter
    //view model
    private val viewModel by lazy { ViewModelProviders.of(this).get(WalletViewModel::class.java) }

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        updateUI()

        FirebaseApp.initializeApp(this);
        val db = FirebaseFirestore.getInstance()
        numberinterface()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        observeData()
        //Adding multisnap to the recyclerview
        val multiSnapHelper = MultiSnapHelper(MultiSnapHelper.DEFAULT_GRAVITY, 1, 200F)
        multiSnapHelper.attachToRecyclerView(recyclerView)

        //Finish button
        btnFinish.setOnClickListener {
            if (amount.isEmpty()||EtNote.text.isEmpty()){
                Toasty.info(this, getString(R.string.fill_all_the_gaps), Toast.LENGTH_SHORT, true).show()
            }else{
                postToList()
                uploadToFirestore()
                //set layout invisible
                cellNewWallet.visibility = View.INVISIBLE
                amount="0"
                textAmount.text = amount
                EtNote.text.clear()
                observeData()

            }
        }
        //Close new income cell button
        btnClose.setOnClickListener {
            //set layout invisible
            cellNewWallet.visibility = View.INVISIBLE
            amount="0"
            textAmount.text = amount
            EtNote.text.clear()
            observeData()
        }

        Timer("SettingUp", false).schedule(5000) {
            if (adapter.itemCount==0){
                //set layout visible
                cellNewWallet.visibility = View.VISIBLE
                newWallet()
            }
            //Button add wallet action
            btnAddWallet.setOnClickListener {
                //set layout visible
                cellNewWallet.visibility = View.VISIBLE
                newWallet()
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
        // Initialize Firebase Auth
        auth = Firebase.auth


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
        btnOutcome = findViewById(R.id.BtnOutcome)
        btnIncome = findViewById(R.id.BtnIncome)
        btnClose = findViewById(R.id.BtnClose)



    }


    private fun addToList(title: String, amountWallet: String){
        titleList.add(title)
        amountList.add(amountWallet)
    }



    private fun postToList(){
            addToList(EtNote.text.toString(), amount)


    }
    private fun uploadToFirestore(){
        // Access a Cloud Firestore instance from your Activity
        val uID = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        val wallet = hashMapOf(
                "WalletTitle" to EtNote.text.toString(),
                "WalletAmount" to amount,
                "WalletId" to uID + EtNote.text.toString()
        )

        // Add a new document with a generated ID

        db.collection("Wallets").document(uID+EtNote.text.toString()).set(wallet).
        addOnSuccessListener { documentReference ->
            Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();

        }.addOnFailureListener { e ->
            Toasty.error(this, e.message.toString(), Toast.LENGTH_SHORT, true).show();

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

    private fun updateUI(){
        val user = auth.currentUser

        if(user==null){
            auth.signInAnonymously()
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                             val usuario = auth.currentUser

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }

                        // ...
                    }
        }
    }






}


