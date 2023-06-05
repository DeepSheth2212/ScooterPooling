package com.example.scooterpooling

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scooterpooling.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rider.setOnClickListener {
            startActivity(Intent(this,RiderActivity::class.java))
        }

        binding.passenger.setOnClickListener {
            startActivity(Intent(this,PassengerActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.logout_btn -> {
                signOut()
                true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
    }

    private fun signOut(){
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        if (auth.currentUser==null){
            Toast.makeText(this,"Signed out successfully!",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }
        else{
            Toast.makeText(this,"Error while signing out!",Toast.LENGTH_SHORT).show()

        }
    }


}