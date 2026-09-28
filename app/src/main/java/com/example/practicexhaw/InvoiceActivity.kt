package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InvoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_invoice)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvInvoiceTitle = findViewById<TextView>(R.id.tvInvoiceTitle)
        tvInvoiceTitle.text = Html.fromHtml("Booking <font color=\"#A855F7\">confirmed.</font>", Html.FROM_HTML_MODE_LEGACY)

        val refNumber = "NL-2026-${(10000..99999).random()}"
        val emailText = if (BookingManager.customerEmail.isNotEmpty()) BookingManager.customerEmail else "your email"
        findViewById<TextView>(R.id.tvInvoiceRef).text = "$refNumber · Sent to $emailText"

        val tableInvoice = findViewById<TableLayout>(R.id.tableInvoice)
        tableInvoice.removeAllViews()

        // Table Header Row
        val headerRow = TableRow(this)
        headerRow.setPadding(12, 16, 12, 16)

        val tvHeadDesc = TextView(this)
        tvHeadDesc.text = "Description"
        tvHeadDesc.setTextColor(getColor(R.color.text_white))
        tvHeadDesc.textSize = 14f
        tvHeadDesc.setTypeface(null, android.graphics.Typeface.BOLD)

        val tvHeadAmount = TextView(this)
        tvHeadAmount.text = "Amount"
        tvHeadAmount.setTextColor(getColor(R.color.text_white))
        tvHeadAmount.textSize = 14f
        tvHeadAmount.setTypeface(null, android.graphics.Typeface.BOLD)
        tvHeadAmount.gravity = android.view.Gravity.END

        headerRow.addView(tvHeadDesc)
        headerRow.addView(tvHeadAmount)
        tableInvoice.addView(headerRow)

        val headerDivider = View(this)
        headerDivider.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 1)
        headerDivider.setBackgroundColor(getColor(R.color.nav_border))
        tableInvoice.addView(headerDivider)

        // Selected Items Rows
        for (item in BookingManager.selectedItems) {
            val row = TableRow(this)
            row.setPadding(12, 14, 12, 14)

            val tvTitle = TextView(this)
            tvTitle.text = item.title
            tvTitle.setTextColor(getColor(R.color.text_gray))
            tvTitle.textSize = 14f

            val tvPrice = TextView(this)
            tvPrice.text = String.format("R%.2f", item.price)
            tvPrice.setTextColor(getColor(R.color.text_white))
            tvPrice.textSize = 14f
            tvPrice.gravity = android.view.Gravity.END

            row.addView(tvTitle)
            row.addView(tvPrice)
            tableInvoice.addView(row)
        }

        val result = BookingManager.calculateTotals()

        // Discount Row
        if (result.discountAmount > 0) {
            val discRow = TableRow(this)
            discRow.setPadding(12, 14, 12, 14)

            val tvDiscTitle = TextView(this)
            tvDiscTitle.text = String.format("Discount (%d bookings, %d%%)", result.count, (result.discountRate * 100).toInt())
            tvDiscTitle.setTextColor(getColor(R.color.text_gray))
            tvDiscTitle.textSize = 14f

            val tvDiscVal = TextView(this)
            tvDiscVal.text = String.format("-R%.2f", result.discountAmount)
            tvDiscVal.setTextColor(getColor(R.color.text_white))
            tvDiscVal.textSize = 14f
            tvDiscVal.gravity = android.view.Gravity.END

            discRow.addView(tvDiscTitle)
            discRow.addView(tvDiscVal)
            tableInvoice.addView(discRow)
        }

        // VAT Row
        val vatRow = TableRow(this)
        vatRow.setPadding(12, 14, 12, 14)

        val tvVatTitle = TextView(this)
        tvVatTitle.text = "VAT (15%)"
        tvVatTitle.setTextColor(getColor(R.color.text_gray))
        tvVatTitle.textSize = 14f

        val tvVatVal = TextView(this)
        tvVatVal.text = String.format("R%.2f", result.vat)
        tvVatVal.setTextColor(getColor(R.color.text_white))
        tvVatVal.textSize = 14f
        tvVatVal.gravity = android.view.Gravity.END

        vatRow.addView(tvVatTitle)
        vatRow.addView(tvVatVal)
        tableInvoice.addView(vatRow)

        // Total Due Display
        findViewById<TextView>(R.id.tvTotalDue).text = String.format("R%.2f", result.totalDue)

        findViewById<Button>(R.id.btnDownloadInvoice).setOnClickListener {
            Toast.makeText(this, "Invoice $refNumber saved to downloads and emailed!", Toast.LENGTH_LONG).show()
        }

        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }

        setupBottomNav()
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