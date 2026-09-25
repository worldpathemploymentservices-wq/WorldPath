package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.InquiryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InquiryDao {
    @Query("SELECT * FROM inquiries ORDER BY timestamp DESC")
    fun getAllInquiries(): Flow<List<InquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: InquiryEntity): Long

    @Query("SELECT COUNT(*) FROM inquiries")
    suspend fun getCount(): Int

    @Query("DELETE FROM inquiries WHERE id = :id")
    suspend fun deleteInquiry(id: Long)
}
