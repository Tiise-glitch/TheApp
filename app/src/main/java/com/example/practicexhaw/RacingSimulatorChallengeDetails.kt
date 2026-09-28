package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RacingSimulatorChallengeDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_racing_simulator_challenge_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val item = BookingManager.allItems[5] // Racing Simulator Challenge

        val btnAddToBooking = findViewById<Button>(R.id.btnAddToBooking)
        btnAddToBooking?.setOnClickListener {
            if (!BookingManager.selectedItems.any { bookingItem -> bookingItem.id == item.id }) {
                BookingManager.selectedItems.add(item)
                Toast.makeText(this, "${item.title} added to booking!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "${item.title} is already in your booking!", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this, SelectionsActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnMenu)?.setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        findViewById<LinearLayout>(R.id.navHome)?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navAbout)?.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }
        findViewById<LinearLayout>(R.id.navSummary)?.setOnClickListener {
            startActivity(Intent(this, Summary::class.java))
        }
        findViewById<LinearLayout>(R.id.navFees)?.setOnClickListener {
            startActivity(Intent(this, FeesActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navContact)?.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }
    }
}