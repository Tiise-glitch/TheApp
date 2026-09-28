package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_us)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvAboutTitle = findViewById<TextView>(R.id.tvAboutTitle)
        tvAboutTitle.text = Html.fromHtml(getString(R.string.about_title), Html.FROM_HTML_MODE_LEGACY)

        val btnMenu = findViewById<ImageButton>(R.id.btnMenu)
        btnMenu.setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }

        // Navigation listeners
        val navHome = findViewById<LinearLayout>(R.id.navHome)
        navHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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