package com.example.scooterpooling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scooterpooling.databinding.ActivityRiderBinding

class RiderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRiderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val from = binding.from.text.toString()
        val to = binding.to.text.toString()

        binding.mapSearch.setOnClickListener {
            val intent = Intent(this,MapSearchActivity::class.java)
            intent.putExtra("FROM_LOCATION",from)
            intent.putExtra("TO_LOCATION",to)
            startActivity(intent)
        }

        binding.pickUp.setOnClickListener {
            val intent = Intent(this,RiderPickupActivity::class.java)
            intent.putExtra("FROM_LOCATION",from)
            intent.putExtra("TO_LOCATION",to)
            startActivity(intent)
        }
    }
}