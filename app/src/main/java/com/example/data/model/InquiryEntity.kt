package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inquiries")
data class InquiryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val jobId: String,
    val jobTitle: String,
    val fullName: String,
    val email: String,
    val country: String,
    val phone: String = "",
    val message: String,
    val consentGiven: Boolean = true,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Received"
)
