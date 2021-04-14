package com.epifi.rachunkiofficiel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.transition.Visibility
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutProgressView
import com.epifi.rachunkiofficiel.Adapters.RecyclerViewAdapter
import com.epifi.rachunkiofficiel.Models.WalletViewModel
import com.github.ybq.android.spinkit.SpinKitView
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
    lateinit var rlCellNewWallet:RelativeLayout
    lateinit var cellWallets:RelativeLayout
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
    lateinit var donutChart:DonutProgressView
    lateinit var loading:SpinKitView
    lateinit var loading2:SpinKitView
    lateinit var pref: SharedPreferences
    var n:Int = 0


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
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        FirebaseApp.initializeApp(this);
        val db = FirebaseFirestore.getInstance()
        numberinterface()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        //Adding multisnap to the recyclerview
        val multiSnapHelper = MultiSnapHelper(MultiSnapHelper.DEFAULT_GRAVITY, 1, 200F)
        multiSnapHelper.attachToRecyclerView(recyclerView)

        //Finish button
        btnFinish.setOnClickListener {
            //Depending of the number indicate from which action is coming
            if(n==1){
                //for create an account
                if (amount.isEmpty()||EtNote.text.isEmpty()){
                    Toasty.info(this, getString(R.string.fill_all_the_gaps), Toast.LENGTH_SHORT, true).show()
                }else{
                    postToList()
                    loading.visibility= View.VISIBLE
                    uploadToFirestore()
                    //set layout invisible

                    cellNewWallet.visibility = View.INVISIBLE
                    rlCellNewWallet.visibility = View.INVISIBLE
                    amount="0"
                    textAmount.text = amount
                    EtNote.text.clear()
                    observeData()

                }

            }else if (n==2){
                //for add an icnome

             if (amount.isEmpty()||EtNote.text.isEmpty()){

                Toasty.info(this, getString(R.string.fill_all_the_gaps), Toast.LENGTH_SHORT, true).show()
             }
             else{


             }

            }else if(n==3){
                //for add an outcome




            }


        }
        //Close new income cell button
        btnClose.setOnClickListener {
            //set layout invisible
            cellNewWallet.visibility = View.INVISIBLE
            rlCellNewWallet.visibility = View.INVISIBLE
            amount="0"
            textAmount.text = amount
            EtNote.text.clear()
            observeData()
        }

        Handler().postDelayed({
            if (adapter.itemCount==0){
                //set layout visible
                cellNewWallet.visibility = View.VISIBLE
                rlCellNewWallet.visibility = View.VISIBLE

                newWallet()
                n=1;

            }
            //Button add wallet action
            btnAddWallet.setOnClickListener {
                //set layout visible
                cellNewWallet.visibility = View.VISIBLE
                rlCellNewWallet.visibility = View.VISIBLE
                newWallet()
                n=1;
            }
        }, 5000)
        /* Timer().schedule(5000){
         }*/


        val walletIncome = pref.getFloat("walletIncome",0f)

        //Donut chart
        donutChart.addAmount(
            sectionName = "income",
            amount = walletIncome.toFloat(),
            color = Color.parseColor("#00BCD4") // Optional, pass color if you want to create new section

        )
        val walletOutcome = pref.getInt("walletOutcome",0)
        donutChart.addAmount(
            sectionName = "outcome",
            amount = walletOutcome.toFloat(),
            color = Color.parseColor("#FF4081")
        )


        btnIncome.setOnClickListener {
            //set layout visible
            cellNewWallet.visibility = View.VISIBLE
            rlCellNewWallet.visibility = View.VISIBLE

            newIncome()
            n=3


        }
        btnOutcome.setOnClickListener {
            //set layout visible
            cellNewWallet.visibility = View.VISIBLE
            rlCellNewWallet.visibility = View.VISIBLE
            n=2
            newOutcome()
        }





    }

    fun observeData(){


        viewModel.fetchWalletData().observe(this, androidx.lifecycle.Observer {
            cellWallets.visibility = View.VISIBLE
            //loading.visibility = View.INVISIBLE
            loading2.visibility = View.INVISIBLE

            adapter.setListWallets(it)
            adapter.notifyDataSetChanged()
        })


    }









    private fun init(){
        // Initialize Firebase Auth
        auth = Firebase.auth


        loading = findViewById<SpinKitView>(R.id.loadingSKV)
        loading2 = findViewById<SpinKitView>(R.id.loading2SKV)
        donutChart = findViewById(R.id.DonutChart)
        adapter = RecyclerViewAdapter(this)
        amount = ""
        recyclerView = findViewById<RecyclerView>(R.id.RVWallets)
        btnAddWallet = findViewById<Button>(R.id.BtnAddWallet)
        cellNewWallet = findViewById<RelativeLayout>(R.id.RlNewWallet)
        cellWallets = findViewById<RelativeLayout>(R.id.RlWalletsCell)
        rlCellNewWallet = findViewById<RelativeLayout>(R.id.RlNewWallet1)
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
    private  fun uploadOutcomeToFirestore(){
        val db= FirebaseFirestore.getInstance()
        val outcome = hashMapOf(
                "outcomeNote" to EtNote.text.toString(),
                "outcomeAmount" to amount,
                "outcomeId" to EtNote.text.toString()
        )
        pref.edit().putFloat("WallOutcome",amount.toFloat()).apply()
        db.collection("Outcomes").document(EtNote.text.toString()+amount).set(outcome).
                addOnSuccessListener { documentReference->
                    Toasty.success(this,"Success!",Toast.LENGTH_SHORT,true).show()
                    n=0
                    pref.edit().putFloat("walletOutcome",amount.toFloat()).apply()
                    loading.visibility = View.INVISIBLE
                }.addOnFailureListener { e->
            Toasty.error(this,e.message.toString(),Toast.LENGTH_SHORT,true).show()

        }



    }
    private fun uploadIncomeToFirestore(){
        val uID = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        val income = hashMapOf(
                "incomeNote" to EtNote.text.toString(),
                "incomeAmount" to amount,
                "incomeId" to EtNote.text.toString()

        )
        pref.edit().putFloat("WalletIncome",amount.toFloat()).apply()


        db.collection("Incomes").document(EtNote.text.toString()+amount).set(income).
        addOnSuccessListener { documentReference ->
            Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
            n=0;
            pref.edit().putFloat("walletIncome",amount.toFloat()).apply()
            loading.visibility = View.INVISIBLE
        }.addOnFailureListener { e ->
            Toasty.error(this, e.message.toString(), Toast.LENGTH_SHORT, true).show();
        }

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
            n=0;
            pref.edit().putString("walletId",EtNote.text.toString()).apply()
            loading.visibility = View.INVISIBLE
        }.addOnFailureListener { e ->
            Toasty.error(this, e.message.toString(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private fun newIncome(){
        textTitle.text = getString(R.string.new_income)
        EtNote.hint = getString(R.string.income_note)
    }
    @SuppressLint("ResourceAsColor")
    private fun newOutcome(){
        textTitle.text = getString(R.string.new_outcome)
        EtNote.hint = getString(R.string.outcome_note)
        textAmount.setTextColor(R.color.accentColorDestructive)
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
        }else{
            observeData()

        }
    }






}

