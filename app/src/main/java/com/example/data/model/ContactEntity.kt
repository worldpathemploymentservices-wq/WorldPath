package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_messages")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val email: String,
    val subject: String,
    val inquiryType: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
