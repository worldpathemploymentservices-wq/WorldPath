package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.CommentEntity
import com.example.data.model.CommentStatus
import com.example.data.model.ContactEntity
import com.example.data.model.InquiryEntity
import com.example.data.model.JobCategory
import com.example.data.model.JobOpportunity
import com.example.data.repository.EmploymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppDestination {
    HOME,
    OPPORTUNITIES,
    OPPORTUNITY_DETAIL,
    INTEREST_FORM,
    ABOUT,
    FAQ,
    CONTACT,
    MODERATION,
    MY_INQUIRIES
}

data class UiNotification(
    val title: String,
    val message: String,
    val isSuccess: Boolean = true
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EmploymentRepository(AppDatabase.getDatabase(application))

    // Navigation state
    private val _currentDestination = MutableStateFlow(AppDestination.HOME)
    val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

    private val _navigationStack = mutableListOf<AppDestination>()

    // Selected job for detail view or interest form
    private val _selectedJob = MutableStateFlow<JobOpportunity?>(null)
    val selectedJob: StateFlow<JobOpportunity?> = _selectedJob.asStateFlow()

    // Filter and search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(JobCategory.ALL)
    val selectedCategory: StateFlow<JobCategory> = _selectedCategory.asStateFlow()

    // Bookmarked jobs
    private val _savedJobIds = MutableStateFlow<Set<String>>(emptySet())
    val savedJobIds: StateFlow<Set<String>> = _savedJobIds.asStateFlow()

    // Active comments for currently selected job
    private val _activeJobComments = MutableStateFlow<List<CommentEntity>>(emptyList())
    val activeJobComments: StateFlow<List<CommentEntity>> = _activeJobComments.asStateFlow()

    // Inquiries list for user history
    val allInquiries: StateFlow<List<InquiryEntity>> = repository.getAllInquiries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All comments for moderation screen
    val allModerationComments: StateFlow<List<CommentEntity>> = repository.getAllCommentsForModeration()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Feedback notifications
    private val _notification = MutableStateFlow<UiNotification?>(null)
    val notification: StateFlow<UiNotification?> = _notification.asStateFlow()

    // Moderation mode toggle
    private val _isModeratorMode = MutableStateFlow(false)
    val isModeratorMode: StateFlow<Boolean> = _isModeratorMode.asStateFlow()

    fun navigateTo(destination: AppDestination) {
        if (_currentDestination.value != destination) {
            _navigationStack.add(_currentDestination.value)
            _currentDestination.value = destination
        }
    }

    fun navigateBack(): Boolean {
        return if (_navigationStack.isNotEmpty()) {
            val prev = _navigationStack.removeAt(_navigationStack.size - 1)
            _currentDestination.value = prev
            true
        } else if (_currentDestination.value != AppDestination.HOME) {
            _currentDestination.value = AppDestination.HOME
            true
        } else {
            false
        }
    }

    fun selectJob(job: JobOpportunity) {
        _selectedJob.value = job
        loadCommentsForJob(job.id)
        navigateTo(AppDestination.OPPORTUNITY_DETAIL)
    }

    fun openInterestForm(job: JobOpportunity? = null) {
        _selectedJob.value = job ?: repository.getOpportunityById("wp-job-101")
        navigateTo(AppDestination.INTEREST_FORM)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: JobCategory) {
        _selectedCategory.value = category
    }

    fun toggleSaveJob(jobId: String) {
        val current = _savedJobIds.value.toMutableSet()
        if (current.contains(jobId)) {
            current.remove(jobId)
        } else {
            current.add(jobId)
        }
        _savedJobIds.value = current
    }

    fun toggleModeratorMode() {
        _isModeratorMode.value = !_isModeratorMode.value
    }

    fun clearNotification() {
        _notification.value = null
    }

    private fun loadCommentsForJob(jobId: String) {
        viewModelScope.launch {
            repository.getApprovedComments(jobId).collect { comments ->
                _activeJobComments.value = comments
            }
        }
    }

    fun submitComment(jobId: String, author: String, country: String, text: String) {
        if (author.isBlank() || text.isBlank() || country.isBlank()) {
            _notification.value = UiNotification(
                title = "Incomplete Fields",
                message = "Please provide your name, country, and question/comment.",
                isSuccess = false
            )
            return
        }

        viewModelScope.launch {
            val id = repository.addComment(jobId, author, country, text)
            if (id > 0) {
                _notification.value = UiNotification(
                    title = "Question Submitted",
                    message = "Your submission has been received. To ensure a safe, legitimate forum, comments are reviewed for compliance before and after publication.",
                    isSuccess = true
                )
            }
        }
    }

    fun approveComment(commentId: Long) {
        viewModelScope.launch {
            repository.updateCommentStatus(commentId, CommentStatus.APPROVED)
            _notification.value = UiNotification(
                title = "Comment Approved",
                message = "The comment is now published on the opportunity page.",
                isSuccess = true
            )
        }
    }

    fun removeComment(commentId: Long) {
        viewModelScope.launch {
            repository.updateCommentStatus(commentId, CommentStatus.REMOVED)
            _notification.value = UiNotification(
                title = "Comment Removed",
                message = "The comment was flagged and removed from public view.",
                isSuccess = true
            )
        }
    }

    fun replyToComment(commentId: Long, reply: String) {
        if (reply.isBlank()) return
        viewModelScope.launch {
            repository.addAdminReply(commentId, reply)
            _notification.value = UiNotification(
                title = "Official Response Added",
                message = "WorldPath advisory response published.",
                isSuccess = true
            )
        }
    }

    fun deleteCommentPermanently(commentId: Long) {
        viewModelScope.launch {
            repository.deleteComment(commentId)
            _notification.value = UiNotification(
                title = "Comment Deleted",
                message = "Comment was permanently removed.",
                isSuccess = true
            )
        }
    }

    fun submitInquiry(
        jobId: String,
        jobTitle: String,
        fullName: String,
        email: String,
        country: String,
        phone: String,
        message: String,
        consentGiven: Boolean,
        context: Context,
        launchEmailClient: Boolean = true
    ) {
        if (fullName.isBlank() || email.isBlank() || country.isBlank()) {
            _notification.value = UiNotification(
                title = "Required Fields Missing",
                message = "Please fill in your name, email, and country of residence.",
                isSuccess = false
            )
            return
        }

        if (!email.contains("@") || !email.contains(".")) {
            _notification.value = UiNotification(
                title = "Invalid Email",
                message = "Please enter a valid email address.",
                isSuccess = false
            )
            return
        }

        if (!consentGiven) {
            _notification.value = UiNotification(
                title = "Consent Required",
                message = "Please accept the consent checkbox to permit contact regarding this inquiry.",
                isSuccess = false
            )
            return
        }

        val inquiry = InquiryEntity(
            jobId = jobId,
            jobTitle = jobTitle,
            fullName = fullName.trim(),
            email = email.trim(),
            country = country.trim(),
            phone = phone.trim(),
            message = message.trim(),
            consentGiven = consentGiven,
            timestamp = System.currentTimeMillis(),
            status = "Received"
        )

        viewModelScope.launch {
            repository.submitInquiry(inquiry)

            // Show explicit mandatory response message
            _notification.value = UiNotification(
                title = "Inquiry Received",
                message = "Thank you for your inquiry. We have received your information and will contact you by email if we need additional information.",
                isSuccess = true
            )

            if (launchEmailClient) {
                try {
                    val intent = repository.createInquiryEmailIntent(inquiry)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Email client might not be installed in emulator, but inquiry is saved locally in database
                }
            }
        }
    }

    fun submitContact(
        name: String,
        email: String,
        subject: String,
        inquiryType: String,
        message: String,
        context: Context,
        launchEmailClient: Boolean = true
    ) {
        if (name.isBlank() || email.isBlank() || message.isBlank()) {
            _notification.value = UiNotification(
                title = "Missing Information",
                message = "Please enter your name, email, and message.",
                isSuccess = false
            )
            return
        }

        val contact = ContactEntity(
            fullName = name.trim(),
            email = email.trim(),
            subject = subject.trim().ifBlank { "General Inquiry" },
            inquiryType = inquiryType,
            message = message.trim(),
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.submitContactMessage(contact)

            _notification.value = UiNotification(
                title = "Message Sent",
                message = "Thank you for reaching out. We have received your message and will respond via email to $email within 1–2 business days.",
                isSuccess = true
            )

            if (launchEmailClient) {
                try {
                    val intent = repository.createGeneralContactEmailIntent(name, email, subject, message)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Email client fallback
                }
            }
        }
    }

    fun getFilteredOpportunities(): List<JobOpportunity> {
        return repository.searchOpportunities(_searchQuery.value, _selectedCategory.value)
    }

    fun getAllOpportunities(): List<JobOpportunity> {
        return EmploymentRepository.VERIFIED_OPPORTUNITIES
    }
}
