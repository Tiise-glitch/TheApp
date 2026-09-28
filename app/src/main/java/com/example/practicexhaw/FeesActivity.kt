package com.example.practicexhaw

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FeesActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var tvPhoneError: TextView
    private lateinit var spSort: Spinner
    private lateinit var containerCheckboxes: LinearLayout

    private lateinit var tvSubtotal: TextView
    private lateinit var tvDiscountLabel: TextView
    private lateinit var tvDiscountValue: TextView
    private lateinit var tvVat: TextView
    private lateinit var tvEstimatedTotal: TextView

    private val currentItemsList = ArrayList<BookingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fees)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvFeesTitle = findViewById<TextView>(R.id.tvFeesTitle)
        tvFeesTitle.text = Html.fromHtml("Get an instant <font color=\"#A855F7\">quote.</font>", Html.FROM_HTML_MODE_LEGACY)

        etFullName = findViewById(R.id.etFullName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        tvPhoneError = findViewById(R.id.tvPhoneError)
        spSort = findViewById(R.id.spSort)
        containerCheckboxes = findViewById(R.id.containerCheckboxes)

        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvDiscountLabel = findViewById(R.id.tvDiscountLabel)
        tvDiscountValue = findViewById(R.id.tvDiscountValue)
        tvVat = findViewById(R.id.tvVat)
        tvEstimatedTotal = findViewById(R.id.tvEstimatedTotal)

        // Pre-fill existing customer info
        etFullName.setText(BookingManager.customerName)
        etPhone.setText(BookingManager.customerPhone)
        etEmail.setText(BookingManager.customerEmail)

        // Phone Number Input Error Handling
        etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePhone(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Setup Dropdown Spinner
        val sortOptions = arrayOf(
            "SORT BY: Price: Low to High",
            "SORT BY: Price: High to Low",
            "SORT BY: Name: A to Z"
        )
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortOptions)
        spSort.adapter = spinnerAdapter

        currentItemsList.clear()
        currentItemsList.addAll(BookingManager.allItems)

        spSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> currentItemsList.sortWith(Comparator { a, b -> a.price.compareTo(b.price) })
                    1 -> currentItemsList.sortWith(Comparator { a, b -> b.price.compareTo(a.price) })
                    2 -> currentItemsList.sortWith(Comparator { a, b -> a.title.compareTo(b.title) })
                }
                populateCheckboxes()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Calculate Button
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        btnCalculate.setOnClickListener {
            val phoneText = etPhone.text.toString().trim()
            if (!validatePhone(phoneText)) {
                Toast.makeText(this, "Please enter a valid 10-digit phone number!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            updateSummaryDisplay()
            Toast.makeText(this, "Quote calculated!", Toast.LENGTH_SHORT).show()
        }

        // Proceed Button
        val btnProceed = findViewById<Button>(R.id.btnProceed)
        btnProceed.setOnClickListener {
            val phoneText = etPhone.text.toString().trim()
            if (!validatePhone(phoneText)) {
                Toast.makeText(this, "Please fix input errors before proceeding!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            BookingManager.customerName = etFullName.text.toString().trim()
            BookingManager.customerPhone = phoneText
            BookingManager.customerEmail = etEmail.text.toString().trim()

            startActivity(Intent(this, SelectionsActivity::class.java))
        }

        // Menu & Navigation
        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            Toast.makeText(this, "Menu opened", Toast.LENGTH_SHORT).show()
        }
        setupBottomNav()
        populateCheckboxes()
        updateSummaryDisplay()
    }

    private fun validatePhone(phone: String): Boolean {
        val digitsOnly = phone.replace(Regex("[^0-9]"), "")
        val isValid = digitsOnly.length == 10
        if (!isValid && phone.isNotEmpty()) {
            tvPhoneError.visibility = View.VISIBLE
            etPhone.setBackgroundResource(R.drawable.bg_edittext_error)
        } else {
            tvPhoneError.visibility = View.GONE
            etPhone.setBackgroundResource(R.drawable.bg_edittext)
        }
        return isValid || phone.isEmpty()
    }

    private fun populateCheckboxes() {
        containerCheckboxes.removeAllViews()
        for (item in currentItemsList) {
            val checkBox = CheckBox(this)
            checkBox.text = "${item.title} — R${item.price.toInt()}"
            checkBox.setTextColor(getColor(R.color.text_white))
            checkBox.textSize = 14f
            checkBox.setPadding(12, 16, 12, 16)
            checkBox.isChecked = BookingManager.selectedItems.any { it.id == item.id }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!BookingManager.selectedItems.any { it.id == item.id }) {
                        BookingManager.selectedItems.add(item)
                    }
                } else {
                    BookingManager.selectedItems.removeAll { it.id == item.id }
                }
                updateSummaryDisplay()
            }

            containerCheckboxes.addView(checkBox)
        }
    }

    private fun updateSummaryDisplay() {
        val result = BookingManager.calculateTotals()
        tvSubtotal.text = String.format("R%.2f", result.subtotal)
        tvDiscountLabel.text = String.format("Discount (%d bookings, %d%%)", result.count, (result.discountRate * 100).toInt())
        tvDiscountValue.text = String.format("-R%.2f", result.discountAmount)
        tvVat.text = String.format("R%.2f", result.vat)
        tvEstimatedTotal.text = String.format("R%.2f", result.totalDue)
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
            // Already on Fees screen
        }
        findViewById<LinearLayout>(R.id.navContact).setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }
    }
}