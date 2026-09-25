package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.model.JobCategory
import com.example.data.repository.EmploymentRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("WorldPath Employment", appName)
    }

    @Test
    fun `verify opportunities repository data`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val repository = EmploymentRepository(db)

        val opportunities = EmploymentRepository.VERIFIED_OPPORTUNITIES
        assertTrue(opportunities.isNotEmpty())

        val healthcareJobs = repository.searchOpportunities("", JobCategory.HEALTHCARE)
        assertTrue(healthcareJobs.all { it.category == JobCategory.HEALTHCARE })

        val searchResult = repository.searchOpportunities("Logistics", JobCategory.ALL)
        assertTrue(searchResult.isNotEmpty())
        assertEquals("wp-job-101", searchResult.first().id)
    }
}
