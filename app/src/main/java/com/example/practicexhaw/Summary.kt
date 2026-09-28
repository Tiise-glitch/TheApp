package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Summary : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_summary)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvSummaryTitle = findViewById<TextView>(R.id.tvSummaryTitle)
        tvSummaryTitle.text = Html.fromHtml(getString(R.string.summary_title), Html.FROM_HTML_MODE_LEGACY)

        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }

        // Package Cards -> Open specific detail activity
        setupCardClick(R.id.cardPkg1, UltimateGamingPassDetails::class.java)
        setupCardClick(R.id.cardPkg2, VIPGamingExperienceDetails::class.java)
        setupCardClick(R.id.cardPkg3, EsportsGamingPackageDetails::class.java)
        setupCardClick(R.id.cardPkg4, BirthdayPartyPackageDetails::class.java)

        // Individual Experience Cards -> Open specific detail activity
        setupCardClick(R.id.cardExp1, VirtualRealityExperienceDetails::class.java)
        setupCardClick(R.id.cardExp2, RacingSimulatorChallengeDetails::class.java)
        setupCardClick(R.id.cardExp3, EscapeRoomChallengeDetail::class.java)

        setupBottomNav()
    }

    private fun setupCardClick(cardId: Int, targetActivity: Class<*>) {
        val card = findViewById<View>(cardId)
        card?.setOnClickListener {
            startActivity(Intent(this, targetActivity))
        }
    }

    private fun setupBottomNav() {
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navAbout).setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }
        findViewById<LinearLayout>(R.id.navSummary).setOnClickListener {
            // Already on Summary screen
        }
        findViewById<LinearLayout>(R.id.navFees).setOnClickListener {
            startActivity(Intent(this, FeesActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navContact).setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }
    }
}