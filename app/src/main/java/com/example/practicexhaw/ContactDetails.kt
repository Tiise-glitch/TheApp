package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ContactDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, ContactActivity::class.java))
        finish()
    }
}