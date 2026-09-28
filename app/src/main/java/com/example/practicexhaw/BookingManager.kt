package com.example.practicexhaw

data class BookingItem(
    val id: String,
    val title: String,
    val category: String,
    val price: Double,
    val description: String,
    val purpose: String,
    val imageRes: Int,
    val includesTable: List<Pair<String, String>>
)

data class CalculationResult(
    val subtotal: Double,
    val count: Int,
    val discountRate: Double,
    val discountAmount: Double,
    val vat: Double,
    val totalDue: Double
)

object BookingManager {
    val allItems = listOf(
        BookingItem(
            id = "pkg1",
            title = "Ultimate Gamer Pass",
            category = "GAMING PACKAGES",
            price = 1500.0,
            description = "Our ultimate, unrestricted high-spec gaming access.",
            purpose = "Unlimited gaming access for a full day.",
            imageRes = R.drawable.img_gaming_venue,
            includesTable = listOf(
                "PC gaming" to "Full-spec gaming PCs",
                "Console gaming" to "Latest-gen consoles",
                "High-speed internet" to "Low-latency network",
                "Snack voucher" to "1 voucher per pass",
                "Tournament entry" to "Entry to daily tournament"
            )
        ),
        BookingItem(
            id = "pkg2",
            title = "VIP Gaming Experience",
            category = "GAMING PACKAGES",
            price = 1500.0,
            description = "Private lounge, lounge seating, free drinks.",
            purpose = "Premium gaming experience with exclusive facilities.",
            imageRes = R.drawable.img_gaming_venue,
            includesTable = listOf(
                "Private gaming booth" to "Exclusive booth access",
                "Premium gaming equipment" to "Top-tier peripherals",
                "Food and drinks" to "Complimentary refreshments",
                "Priority booking" to "Skip the queue",
                "Personal gaming assistant" to "Dedicated staff support"
            )
        ),
        BookingItem(
            id = "pkg3",
            title = "Esports Training Package",
            category = "GAMING PACKAGES",
            price = 1500.0,
            description = "Guided session with coach, strategy breakdown.",
            purpose = "Improve competitive gaming skills with professional coaching.",
            imageRes = R.drawable.img_gamers,
            includesTable = listOf(
                "Strategy coaching" to "Pro-level game strategy",
                "Team communication" to "Callout drills",
                "Match analysis" to "VOD review with coach",
                "Practice sessions" to "Structured practice",
                "Performance feedback" to "Post-session report"
            )
        ),
        BookingItem(
            id = "pkg4",
            title = "Birthday Party Package",
            category = "GAMING PACKAGES",
            price = 1500.0,
            description = "Reserved party zone, snacks, tournament setup.",
            purpose = "Host a gaming-themed birthday celebration.",
            imageRes = R.drawable.img_gamers,
            includesTable = listOf(
                "Reserved gaming area" to "Private party zone",
                "Multiplayer competitions" to "Group tournaments",
                "Party decorations" to "Gaming-themed décor",
                "Catering" to "Food & drinks package",
                "Tournament prizes" to "Winner trophies"
            )
        ),
        BookingItem(
            id = "exp1",
            title = "Virtual Reality Experience",
            category = "INDIVIDUAL EXPERIENCES",
            price = 750.0,
            description = "Explore immersive virtual reality games.",
            purpose = "Explore immersive virtual reality games.",
            imageRes = R.drawable.img_gamers,
            includesTable = listOf(
                "VR headset" to "Premium VR headset",
                "Choice of games" to "20+ VR titles available",
                "Staff assistance" to "Guided setup & support"
            )
        ),
        BookingItem(
            id = "exp2",
            title = "Racing Simulator Challenge",
            category = "INDIVIDUAL EXPERIENCES",
            price = 750.0,
            description = "Race on professional driving simulators.",
            purpose = "Race on professional driving simulators.",
            imageRes = R.drawable.img_gaming_venue,
            includesTable = listOf(
                "Racing simulator" to "Full motion racing rig",
                "Choice of tracks" to "15+ world circuits",
                "Leaderboard competition" to "Compete for top times"
            )
        ),
        BookingItem(
            id = "exp3",
            title = "Escape Room Challenge",
            category = "INDIVIDUAL EXPERIENCES",
            price = 750.0,
            description = "Solve puzzles and escape before time runs out.",
            purpose = "Solve puzzles and escape before time runs out.",
            imageRes = R.drawable.img_gamers,
            includesTable = listOf(
                "Team challenge" to "2-6 player teams",
                "Themed escape room" to "Multiple themed rooms",
                "Digital scorecard" to "Track your escape time"
            )
        )
    )

    // Selection Array
    val selectedItems = ArrayList<BookingItem>()

    // Customer Contact Details
    var customerName: String = ""
    var customerPhone: String = ""
    var customerEmail: String = ""

    fun getItemById(id: String): BookingItem? {
        return allItems.find { it.id == id }
    }

    fun calculateTotals(): CalculationResult {
        val subtotal = selectedItems.sumOf { it.price }
        val count = selectedItems.size
        val discountRate = when {
            count >= 3 -> 0.10
            count == 2 -> 0.05
            else -> 0.0
        }
        val discountAmount = subtotal * discountRate
        val amountAfterDiscount = subtotal - discountAmount
        val vat = amountAfterDiscount * 0.15
        val totalDue = amountAfterDiscount + vat

        return CalculationResult(
            subtotal = subtotal,
            count = count,
            discountRate = discountRate,
            discountAmount = discountAmount,
            vat = vat,
            totalDue = totalDue
        )
    }
}