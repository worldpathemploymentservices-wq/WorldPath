package com.example.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.components.AppFooter
import com.example.ui.components.PrivacyPolicyDialog
import com.example.ui.components.TermsOfUseDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    viewModel: MainViewModel,
    isWideScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var inquiryType by remember { mutableStateOf("General Inquiry") }
    var message by remember { mutableStateOf("") }

    var typeDropdownExpanded by remember { mutableStateOf(false) }
    val inquiryTypes = listOf(
        "General Inquiry",
        "Opportunity & Requirements Clarification",
        "Document Pre-Screening Question",
        "Employer Verification Inquiry",
        "Website Feedback"
    )

    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    if (showPrivacyDialog) {
        PrivacyPolicyDialog(onDismiss = { showPrivacyDialog = false })
    }
    if (showTermsDialog) {
        TermsOfUseDialog(onDismiss = { showTermsDialog = false })
    }

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
                    text = "Message Sent Successfully",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Thank you for contacting WorldPath Employment Services. We have received your inquiry and our team will reply to $email within 1–2 business days.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = { showConfirmationDialog = false },
                    modifier = Modifier.testTag("contact_dialog_ok_button")
                ) {
                    Text("OK")
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
                .widthIn(max = 1140.dp)
                .testTag("contact_screen"),
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
                        modifier = Modifier.testTag("contact_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Contact Us",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (isWideScreen) {
                // Desktop / Tablet 2-Column Contact Layout
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Left: Business info
                        Box(modifier = Modifier.weight(1f)) {
                            ContactInfoCard()
                        }

                        // Right: Form
                        Box(modifier = Modifier.weight(1.1f)) {
                            ContactFormCard(
                                name = name,
                                onNameChange = { name = it },
                                email = email,
                                onEmailChange = { email = it },
                                subject = subject,
                                onSubjectChange = { subject = it },
                                inquiryType = inquiryType,
                                onInquiryTypeChange = { inquiryType = it },
                                message = message,
                                onMessageChange = { message = it },
                                typeDropdownExpanded = typeDropdownExpanded,
                                onTypeDropdownExpandedChange = { typeDropdownExpanded = it },
                                inquiryTypes = inquiryTypes,
                                onSubmit = {
                                    viewModel.submitContact(
                                        name = name,
                                        email = email,
                                        subject = subject,
                                        inquiryType = inquiryType,
                                        message = message,
                                        context = context,
                                        launchEmailClient = true
                                    )
                                    if (name.isNotBlank() && email.isNotBlank() && message.isNotBlank()) {
                                        showConfirmationDialog = true
                                        name = ""
                                        email = ""
                                        subject = ""
                                        message = ""
                                    }
                                }
                            )
                        }
                    }
                }
            } else {
                // Phone Single Column Stacked Layout
                item {
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ContactInfoCard()
                    }
                }

                item {
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ContactFormCard(
                            name = name,
                            onNameChange = { name = it },
                            email = email,
                            onEmailChange = { email = it },
                            subject = subject,
                            onSubjectChange = { subject = it },
                            inquiryType = inquiryType,
                            onInquiryTypeChange = { inquiryType = it },
                            message = message,
                            onMessageChange = { message = it },
                            typeDropdownExpanded = typeDropdownExpanded,
                            onTypeDropdownExpandedChange = { typeDropdownExpanded = it },
                            inquiryTypes = inquiryTypes,
                            onSubmit = {
                                viewModel.submitContact(
                                    name = name,
                                    email = email,
                                    subject = subject,
                                    inquiryType = inquiryType,
                                    message = message,
                                    context = context,
                                    launchEmailClient = true
                                )
                                if (name.isNotBlank() && email.isNotBlank() && message.isNotBlank()) {
                                    showConfirmationDialog = true
                                    name = ""
                                    email = ""
                                    subject = ""
                                    message = ""
                                }
                            }
                        )
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
private fun ContactInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "WorldPath Employment Services",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "We welcome inquiries regarding verified opportunities, employer requirements, and document advisory guidance.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Official Business Email",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "worldpathemploymentservices@gmail.com",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Business Hours & Response Time", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    Text(
                        "Monday – Friday: 9:00 AM – 5:00 PM (EST)\nEmail responses delivered within 1–2 business days.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Postal & Correspondence Address", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    Text(
                        "WorldPath Employment Services\n1200 Avenue of the Americas, Suite 800\nNew York, NY 10036, United States",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Website Notice: We do NOT provide live chat, instant social messaging, or unverified phone channels. All communications are conducted securely via email to preserve a verified written record.",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactFormCard(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    subject: String,
    onSubjectChange: (String) -> Unit,
    inquiryType: String,
    onInquiryTypeChange: (String) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    typeDropdownExpanded: Boolean,
    onTypeDropdownExpandedChange: (Boolean) -> Unit,
    inquiryTypes: List<String>,
    onSubmit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "Send Us a Message",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fill in the details below. Messages are delivered directly to worldpathemploymentservices@gmail.com.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Your Full Name *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                placeholder = { Text("e.g. Samuel Adebayo") },
                modifier = Modifier.fillMaxWidth().testTag("contact_name_input"),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Your Email Address *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = { Text("e.g. samuel@example.com") },
                modifier = Modifier.fillMaxWidth().testTag("contact_email_input"),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Inquiry Category *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))

            ExposedDropdownMenuBox(
                expanded = typeDropdownExpanded,
                onExpandedChange = onTypeDropdownExpandedChange,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = inquiryType,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeDropdownExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                        .fillMaxWidth()
                        .testTag("contact_inquiry_type_selector"),
                    shape = RoundedCornerShape(10.dp)
                )

                ExposedDropdownMenu(
                    expanded = typeDropdownExpanded,
                    onDismissRequest = { onTypeDropdownExpandedChange(false) }
                ) {
                    inquiryTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                onInquiryTypeChange(type)
                                onTypeDropdownExpandedChange(false)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Subject *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = subject,
                onValueChange = onSubjectChange,
                placeholder = { Text("e.g. Question on Logistics Requirements") },
                modifier = Modifier.fillMaxWidth().testTag("contact_subject_input"),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Message *", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = message,
                onValueChange = onMessageChange,
                placeholder = { Text("Type your message or inquiry here...") },
                modifier = Modifier.fillMaxWidth().testTag("contact_message_input"),
                minLines = 4,
                maxLines = 6,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("submit_contact_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Send Contact Message",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
