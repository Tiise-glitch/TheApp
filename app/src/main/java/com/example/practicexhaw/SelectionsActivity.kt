package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SelectionsActivity : AppCompatActivity() {

    private lateinit var containerSelections: LinearLayout
    private lateinit var tvEmptyState: TextView
    private lateinit var tvDiscountHeader: TextView
    private lateinit var tvDiscountTierDesc: TextView
    private lateinit var tvSelectionsSubtotal: TextView
    private lateinit var btnVerifyInvoice: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_selections)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvSelectionsTitle = findViewById<TextView>(R.id.tvSelectionsTitle)
        tvSelectionsTitle.text = Html.fromHtml("Your <font color=\"#A855F7\">selections.</font>", Html.FROM_HTML_MODE_LEGACY)

        containerSelections = findViewById(R.id.containerSelections)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        tvDiscountHeader = findViewById(R.id.tvDiscountHeader)
        tvDiscountTierDesc = findViewById(R.id.tvDiscountTierDesc)
        tvSelectionsSubtotal = findViewById(R.id.tvSelectionsSubtotal)
        btnVerifyInvoice = findViewById(R.id.btnVerifyInvoice)

        btnVerifyInvoice.setOnClickListener {
            if (BookingManager.selectedItems.isEmpty()) {
                Toast.makeText(this, "Please select at least one package or course first!", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, InvoiceActivity::class.java))
            }
        }

        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }

        setupBottomNav()
        refreshSelectionList()
    }

    override fun onResume() {
        super.onResume()
        refreshSelectionList()
    }

    private fun refreshSelectionList() {
        containerSelections.removeAllViews()

        val items = BookingManager.selectedItems

        if (items.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
        } else {
            tvEmptyState.visibility = View.GONE
            for (item in items) {
                val card = RelativeLayout(this)
                card.setBackgroundResource(R.drawable.bg_card)
                card.setPadding(32, 32, 32, 32)

                val param = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                param.setMargins(0, 0, 0, 24)
                card.layoutParams = param

                // Left Title and Price
                val textContainer = LinearLayout(this)
                textContainer.orientation = LinearLayout.VERTICAL

                val tvTitle = TextView(this)
                tvTitle.text = item.title
                tvTitle.setTextColor(getColor(R.color.text_white))
                tvTitle.textSize = 16f
                tvTitle.setTypeface(null, android.graphics.Typeface.BOLD)

                val tvPrice = TextView(this)
                tvPrice.text = "R${item.price.toInt()}"
                tvPrice.setTextColor(getColor(R.color.cyan_accent))
                tvPrice.textSize = 14f
                tvPrice.setPadding(0, 8, 0, 0)

                textContainer.addView(tvTitle)
                textContainer.addView(tvPrice)

                val textParam = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                textParam.addRule(RelativeLayout.ALIGN_PARENT_START)
                textParam.addRule(RelativeLayout.CENTER_VERTICAL)
                textContainer.layoutParams = textParam

                // Remove Button
                val btnRemove = Button(this)
                btnRemove.text = "Remove ✕"
                btnRemove.setTextColor(getColor(R.color.text_white))
                btnRemove.textSize = 12f
                btnRemove.setBackgroundResource(R.drawable.bg_hamburger)
                btnRemove.backgroundTintList = null
                btnRemove.setPadding(24, 12, 24, 12)

                val btnParam = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                btnParam.addRule(RelativeLayout.ALIGN_PARENT_END)
                btnParam.addRule(RelativeLayout.CENTER_VERTICAL)
                btnRemove.layoutParams = btnParam

                btnRemove.setOnClickListener {
                    BookingManager.selectedItems.remove(item)
                    refreshSelectionList()
                    Toast.makeText(this, "Removed ${item.title}", Toast.LENGTH_SHORT).show()
                }

                card.addView(textContainer)
                card.addView(btnRemove)
                containerSelections.addView(card)
            }
        }

        val result = BookingManager.calculateTotals()
        tvDiscountTierDesc.text = String.format("%d bookings selected → %d%% discount tier applies.", result.count, (result.discountRate * 100).toInt())
        tvSelectionsSubtotal.text = String.format("R%.2f", result.subtotal)
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
            startActivity(Intent(this, ContactActivity::class.java))
        }
    }
}