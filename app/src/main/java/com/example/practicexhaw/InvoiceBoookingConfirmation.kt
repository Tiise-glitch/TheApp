package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class InvoiceBoookingConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, InvoiceActivity::class.java))
        finish()
    }
}