package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvHeroTitle = findViewById<TextView>(R.id.tvHeroTitle)
        tvHeroTitle.text = Html.fromHtml(getString(R.string.home_hero_title), Html.FROM_HTML_MODE_LEGACY)

        // Menu button
        val btnMenu = findViewById<ImageButton>(R.id.btnMenu)
        btnMenu.setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }

        // Action Buttons
        val btnViewPackages = findViewById<Button>(R.id.btnViewPackages)
        btnViewPackages.setOnClickListener {
            startActivity(Intent(this, Summary::class.java))
        }

        val cardGamingPackages = findViewById<View>(R.id.cardGamingPackages)
        cardGamingPackages?.setOnClickListener {
            startActivity(Intent(this, Summary::class.java))
        }

        val cardIndividualExperiences = findViewById<View>(R.id.cardIndividualExperiences)
        cardIndividualExperiences?.setOnClickListener {
            startActivity(Intent(this, Summary::class.java))
        }

        val cardBookConsultant = findViewById<View>(R.id.cardBookConsultant)
        cardBookConsultant?.setOnClickListener {
            startActivity(Intent(this, FeesActivity::class.java))
        }

        // Navigation listeners
        val navAbout = findViewById<LinearLayout>(R.id.navAbout)
        navAbout.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }

        val navSummary = findViewById<LinearLayout>(R.id.navSummary)
        navSummary.setOnClickListener {
            startActivity(Intent(this, Summary::class.java))
        }

        val navFees = findViewById<LinearLayout>(R.id.navFees)
        navFees.setOnClickListener {
            startActivity(Intent(this, FeesActivity::class.java))
        }

        val navContact = findViewById<LinearLayout>(R.id.navContact)
        navContact.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }
    }
}