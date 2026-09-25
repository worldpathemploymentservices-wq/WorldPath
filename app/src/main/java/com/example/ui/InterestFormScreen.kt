package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.InquiryEntity
import com.example.ui.components.AppFooter
import com.example.ui.components.PrivacyPolicyDialog
import com.example.ui.components.TermsOfUseDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestFormScreen(
    viewModel: MainViewModel,
    isWideScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedJob by viewModel.selectedJob.collectAsState()
    val allJobs = viewModel.getAllOpportunities()
    val inquiries by viewModel.allInquiries.collectAsState()

    // Form inputs
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var consentGiven by remember { mutableStateOf(false) }

    // Dropdown for job selection
    var jobDropdownExpanded by remember { mutableStateOf(false) }
    var chosenJob by remember(selectedJob) {
        mutableStateOf(selectedJob ?: allJobs.firstOrNull())
    }

    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showInquiryHistory by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    if (showPrivacyDialog) {
        PrivacyPolicyDialog(onDismiss = { showPrivacyDialog = false })
    }
    if (showTermsDialog) {
        TermsOfUseDialog(onDismiss = { showTermsDialog = false })
    }

    // Official Required Confirmation Dialog
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(44.dp)
                )
            },
            title = {
                Text(
                    text = "Inquiry Received Successfully",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Thank you for your inquiry. We have received your information and will contact you by email if we need additional information.",
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmationDialog = false
                        viewModel.navigateTo(AppDestination.OPPORTUNITIES)
                    },
                    modifier = Modifier.testTag("inquiry_dialog_ok_button")
                ) {
                    Text("Return to Opportunities")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showConfirmationDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 960.dp)
                .testTag("interest_form_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("interest_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Expression of Interest",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (inquiries.isNotEmpty()) {
                        FilledTonalButton(
                            onClick = { showInquiryHistory = !showInquiryHistory },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (showInquiryHistory) "Hide History" else "My Inquiries (${inquiries.size})",
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Main Form Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(if (isWideScreen) 32.dp else 20.dp)) {
                        Text(
                            text = "Submit Expression of Interest",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Connected to WorldPath Employment Services (worldpathemploymentservices@gmail.com). Our advisory team reviews credentials and responds via official email.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Security notice
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Security Notice: We do NOT request passwords, bank PINs, credit card numbers, or upfront application fees. All communications are conducted strictly via email.",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Job selector dropdown
                        Text(
                            text = "Opportunity of Interest *",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        ExposedDropdownMenuBox(
                            expanded = jobDropdownExpanded,
                            onExpandedChange = { jobDropdownExpanded = !jobDropdownExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = chosenJob?.title ?: "Select an opportunity",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = jobDropdownExpanded)
                                },
                                modifier = Modifier
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                                    .fillMaxWidth()
                                    .testTag("interest_job_selector"),
                                shape = RoundedCornerShape(10.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = jobDropdownExpanded,
                                onDismissRequest = { jobDropdownExpanded = false }
                            ) {
                                allJobs.forEach { job ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(job.title, fontWeight = FontWeight.SemiBold)
                                                Text(
                                                    "${job.category.displayName} • ${job.location}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        },
                                        onClick = {
                                            chosenJob = job
                                            jobDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Row: Full Name & Email on wide screens
                        if (isWideScreen) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Full Name *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    OutlinedTextField(
                                        value = fullName,
                                        onValueChange = { fullName = it },
                                        placeholder = { Text("e.g. Maria Gonzalez") },
                                        modifier = Modifier.fillMaxWidth().testTag("interest_full_name_input"),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Email Address *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    OutlinedTextField(
                                        value = email,
                                        onValueChange = { email = it },
                                        placeholder = { Text("e.g. maria.gonzalez@example.com") },
                                        modifier = Modifier.fillMaxWidth().testTag("interest_email_input"),
                                        singleLine = true,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                }
                            }
                        } else {
                            Text("Full Name *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                placeholder = { Text("e.g. Maria Gonzalez") },
                                modifier = Modifier.fillMaxWidth().testTag("interest_full_name_input"),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text("Email Address *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                placeholder = { Text("e.g. maria.gonzalez@example.com") },
                                modifier = Modifier.fillMaxWidth().testTag("interest_email_input"),
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Country of Residence & Phone
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Country of Residence *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(6.dp))
                                OutlinedTextField(
                                    value = country,
                                    onValueChange = { country = it },
                                    placeholder = { Text("e.g. Philippines") },
                                    modifier = Modifier.fillMaxWidth().testTag("interest_country_input"),
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text("Phone Number (Optional)", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(6.dp))
                                OutlinedTextField(
                                    value = phone,
                                    onValueChange = { phone = it },
                                    placeholder = { Text("+1 (555) 000-0000") },
                                    modifier = Modifier.fillMaxWidth().testTag("interest_phone_input"),
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Message Field
                        Text("Message & Qualifications Summary *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            placeholder = { Text("Briefly summarize your relevant work experience, foreign credential evaluations, language skills, and qualifications...") },
                            modifier = Modifier.fillMaxWidth().testTag("interest_message_input"),
                            minLines = 4,
                            maxLines = 6,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Consent Checkbox
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { consentGiven = !consentGiven }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = consentGiven,
                                onCheckedChange = { consentGiven = it },
                                modifier = Modifier.testTag("interest_consent_checkbox")
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "I consent to be contacted by WorldPath Employment Services via email regarding this opportunity inquiry. I acknowledge that employment is subject to employer requirements and no job or visa is guaranteed.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Submit Button
                        Button(
                            onClick = {
                                val jobToSubmit = chosenJob ?: allJobs.first()
                                viewModel.submitInquiry(
                                    jobId = jobToSubmit.id,
                                    jobTitle = jobToSubmit.title,
                                    fullName = fullName,
                                    email = email,
                                    country = country,
                                    phone = phone,
                                    message = message,
                                    consentGiven = consentGiven,
                                    context = context,
                                    launchEmailClient = true
                                )
                                if (fullName.isNotBlank() && email.isNotBlank() && country.isNotBlank() && consentGiven) {
                                    showConfirmationDialog = true
                                    fullName = ""
                                    email = ""
                                    country = ""
                                    phone = ""
                                    message = ""
                                    consentGiven = false
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("submit_interest_button"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Submit Expression of Interest",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Submissions History (if toggled)
            if (showInquiryHistory && inquiries.isNotEmpty()) {
                item {
                    Column(modifier = Modifier.padding(horizontal = if (isWideScreen) 24.dp else 16.dp)) {
                        Text(
                            text = "Your Submitted Inquiries",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                items(inquiries) { inq ->
                    Box(modifier = Modifier.padding(horizontal = if (isWideScreen) 24.dp else 16.dp)) {
                        InquiryHistoryCard(inquiry = inq)
                    }
                }
            }

            // Footer
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AppFooter(
                    onNavigate = { viewModel.navigateTo(it) },
                    onShowPrivacy = { showPrivacyDialog = true },
                    onShowTerms = { showTermsDialog = true }
                )
            }
        }
    }
}

@Composable
private fun InquiryHistoryCard(inquiry: InquiryEntity) {
    val dateFormat = remember { SimpleDateFormat("MMM d, yyyy, h:mm a", Locale.getDefault()) }
    val dateString = remember(inquiry.timestamp) { dateFormat.format(Date(inquiry.timestamp)) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = inquiry.jobTitle,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = inquiry.status,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Submitted on $dateString",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Applicant: ${inquiry.fullName} (${inquiry.country}) • ${inquiry.email}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = inquiry.message,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
