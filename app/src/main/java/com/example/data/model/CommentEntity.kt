package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CommentStatus {
    APPROVED,
    PENDING_REVIEW,
    REMOVED
}

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val jobId: String,
    val authorName: String,
    val country: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = CommentStatus.APPROVED.name, // "APPROVED", "PENDING_REVIEW", "REMOVED"
    val adminReply: String? = null
)
