package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.CommentEntity
import com.example.data.model.ContactEntity
import com.example.data.model.InquiryEntity

@Database(
    entities = [CommentEntity::class, InquiryEntity::class, ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao
    abstract fun inquiryDao(): InquiryDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "worldpath_employment.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
