package com.example.practicexhaw

import android.content.Intent
import android.net.Uri
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

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvContactTitle = findViewById<TextView>(R.id.tvContactTitle)
        tvContactTitle.text = Html.fromHtml("Find us / <font color=\"#A855F7\">reach us.</font>", Html.FROM_HTML_MODE_LEGACY)

        // Sandton Venue Interactions
        findViewById<View>(R.id.cardMapSandton).setOnClickListener {
            openMap("Sandton Arena Johannesburg")
        }
        findViewById<View>(R.id.btnCallSandton).setOnClickListener {
            dialPhone("0110000000")
        }
        findViewById<View>(R.id.btnEmailSandton).setOnClickListener {
            sendEmail("sandton@nextlevel.co.za")
        }

        // Randburg Venue Interactions
        findViewById<View>(R.id.cardMapRandburg).setOnClickListener {
            openMap("Randburg Arena Johannesburg")
        }
        findViewById<View>(R.id.btnCallRandburg).setOnClickListener {
            dialPhone("0111111111")
        }
        findViewById<View>(R.id.btnEmailRandburg).setOnClickListener {
            sendEmail("randburg@nextlevel.co.za")
        }

        // Social Media Buttons
        findViewById<Button>(R.id.btnInstagram).setOnClickListener {
            openUrl("https://instagram.com")
        }
        findViewById<Button>(R.id.btnTwitter).setOnClickListener {
            openUrl("https://x.com")
        }
        findViewById<Button>(R.id.btnFacebook).setOnClickListener {
            openUrl("https://facebook.com")
        }

        // Menu & Bottom Nav
        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }
        setupBottomNav()
    }

    private fun openMap(location: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${Uri.encode(location)}"))
        startActivity(intent)
    }

    private fun dialPhone(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(intent)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setupBottomNav() {
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navAbout).setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }
        findViewById<LinearLayout>(R.id.navSummary).setOnClickListener {
            startActivity(Intent(this, Summary::class.java))
        }
        findViewById<LinearLayout>(R.id.navFees).setOnClickListener {
            startActivity(Intent(this, FeesActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navContact).setOnClickListener {
            // Already on Contact screen
        }
    }
}