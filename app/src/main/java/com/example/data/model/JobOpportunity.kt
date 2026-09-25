package com.example.data.model

enum class JobCategory(val displayName: String) {
    ALL("All Categories"),
    LOGISTICS("Logistics & Warehousing"),
    HEALTHCARE("Healthcare Support"),
    SKILLED_TRADES("Skilled Trades & Mfg"),
    HOSPITALITY("Hospitality & Culinary"),
    AGRICULTURE("Agricultural Operations"),
    TECH("Technical & IT Support")
}

data class JobOpportunity(
    val id: String,
    val title: String,
    val employer: String,
    val isEmployerNameDisclosed: Boolean = true,
    val location: String,
    val category: JobCategory,
    val description: String,
    val requirements: List<String>,
    val schedule: String,
    val payInfo: String,
    val isPayVerified: Boolean = true,
    val applicationDeadline: String,
    val applicationInstructions: String,
    val status: String = "Active Recruitment",
    val postedDate: String = "September 2026",
    val openingsCount: Int = 4,
    val employerVerificationId: String
)
