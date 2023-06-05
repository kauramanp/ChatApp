package com.aman.chatapp

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.AccessController
import java.text.SimpleDateFormat
import java.util.*

class ChatMainActivity : AppCompatActivity(), DialogInterface {
    lateinit var optionsDialog: OptionsDialog
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = Navigation.findNavController(
            this,
            R.id.fragment
        )
        optionsDialog = OptionsDialog(this, this)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
           optionsDialog.show()
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.SecondFragment ->{
                    findViewById<FloatingActionButton>(R.id.fab).visibility = View.GONE
                } R.id.FirstFragment ->{
                findViewById<FloatingActionButton>(R.id.fab).visibility = View.VISIBLE
                }
            }
        }
    }

    override fun dialogOk(userType: Int, name: String, refId: String?) {
        optionsDialog.dismiss()
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd-HH:mm:ss"
        )
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val currentDate = dateFormat.format(Date())
        val bundle = Bundle()
        bundle.putInt("userType", userType)
        bundle.putString("refId", currentDate)
        bundle.putString("name", name)
        navController.navigate(R.id.SecondFragment, bundle)
    }

    override fun dialogCancel() {
        optionsDialog.dismiss()
    }
}