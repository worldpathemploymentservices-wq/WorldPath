package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE jobId = :jobId AND status = 'APPROVED' ORDER BY timestamp DESC")
    fun getApprovedCommentsForJob(jobId: String): Flow<List<CommentEntity>>

    @Query("SELECT * FROM comments WHERE jobId = :jobId ORDER BY timestamp DESC")
    fun getAllCommentsForJob(jobId: String): Flow<List<CommentEntity>>

    @Query("SELECT * FROM comments ORDER BY timestamp DESC")
    fun getAllCommentsForModeration(): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity>)

    @Query("UPDATE comments SET status = :status WHERE id = :id")
    suspend fun updateCommentStatus(id: Long, status: String)

    @Query("UPDATE comments SET adminReply = :reply WHERE id = :id")
    suspend fun updateAdminReply(id: Long, reply: String)

    @Query("DELETE FROM comments WHERE id = :id")
    suspend fun deleteComment(id: Long)

    @Query("SELECT COUNT(*) FROM comments")
    suspend fun getCommentCount(): Int
}
