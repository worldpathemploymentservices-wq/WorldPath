package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.JobOpportunity
import com.example.ui.components.AppFooter
import com.example.ui.components.OpportunityCard
import com.example.ui.components.PrivacyPolicyDialog
import com.example.ui.components.TermsOfUseDialog

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    isWideScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    val savedIds by viewModel.savedJobIds.collectAsState()
    val allJobs = viewModel.getAllOpportunities().take(4)

    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    if (showPrivacyDialog) {
        PrivacyPolicyDialog(onDismiss = { showPrivacyDialog = false })
    }
    if (showTermsDialog) {
        TermsOfUseDialog(onDismiss = { showTermsDialog = false })
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 1140.dp)
                .testTag("home_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Brand & Banner Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    if (isWideScreen) {
                        // Desktop & Tablet 2-Column Hero
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1.1f)
                                    .padding(32.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
                                ) {
                                    Text(
                                        text = "Official Employment & Application Information Portal",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = "WorldPath Employment Services",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Connecting qualified international candidates with verified U.S. employment opportunities, transparent wage data, employer requirements, and compliant application guidance.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 24.sp
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Button(
                                        onClick = { viewModel.navigateTo(AppDestination.OPPORTUNITIES) },
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .height(48.dp)
                                            .testTag("home_view_opportunities_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Work,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("View Opportunities", fontWeight = FontWeight.Bold)
                                    }

                                    FilledTonalButton(
                                        onClick = { viewModel.openInterestForm(null) },
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .height(48.dp)
                                            .testTag("home_im_interested_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AssignmentTurnedIn,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("I'm Interested", fontWeight = FontWeight.SemiBold)
                                    }

                                    OutlinedButton(
                                        onClick = { viewModel.navigateTo(AppDestination.CONTACT) },
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .height(48.dp)
                                            .testTag("home_contact_us_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContactMail,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Contact Us", fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .weight(0.9f)
                                    .height(300.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_hero_employment_1790338083190),
                                    contentDescription = "WorldPath International Employment Banner",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    } else {
                        // Phone Layout Hero
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_hero_employment_1790338083190),
                                    contentDescription = "WorldPath International Employment Banner",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(12.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Shield,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Verified U.S. Employment Information Portal",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "WorldPath Employment Services",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Welcome to WorldPath. We share legitimate employment opportunities, job qualification criteria, application guidance, and general verified information for candidates interested in working in the United States.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 21.sp
                                )

                                Spacer(modifier = Modifier.height(18.dp))

                                Button(
                                    onClick = { viewModel.navigateTo(AppDestination.OPPORTUNITIES) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .testTag("home_view_opportunities_button"),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Work,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "View Opportunities",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    FilledTonalButton(
                                        onClick = { viewModel.openInterestForm(null) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .testTag("home_im_interested_button"),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AssignmentTurnedIn,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("I'm Interested", fontWeight = FontWeight.SemiBold)
                                    }

                                    OutlinedButton(
                                        onClick = { viewModel.navigateTo(AppDestination.CONTACT) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .testTag("home_contact_us_button"),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContactMail,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Contact Us", fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // MANDATORY TRANSPARENCY NOTICE
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp)
                        .testTag("home_transparency_notice"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.35f)
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = "Legal Notice",
                                    tint = MaterialTheme.colorScheme.onTertiary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Mandatory Disclosure & Non-Guarantee Notice",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "• Employment opportunities are strictly subject to employer requirements, interviews, and qualification vetting.\n• No job, visa, work permit, or immigration outcome is guaranteed.\n• WorldPath Employment Services is a private advisory service, not a government agency.\n• We do not charge application fees and never ask for bank PINs, card numbers, or passwords.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                lineHeight = 21.sp
                            )
                        }
                    }
                }
            }

            // How the Website Works
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "How Our Service Operates",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A simple, transparent 4-step workflow without live chat or private social messaging:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        if (isWideScreen) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    ProcessStepItem("1", "Read Employment Posts", "Review verified duties, employer names, prevailing pay, and required credentials.")
                                    ProcessStepItem("2", "Ask Public Questions", "Leave moderated questions on posts to clarify requirements without private messaging.")
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    ProcessStepItem("3", "Submit 'I'm Interested' Inquiry", "Provide your background and consent to contact via official email.")
                                    ProcessStepItem("4", "Receive Evaluation by Email", "Our advisory team follows up directly by email (worldpathemploymentservices@gmail.com).")
                                }
                            }
                        } else {
                            ProcessStepItem("1", "Read Employment Posts", "Review verified duties, employer names, prevailing pay, and required credentials.")
                            ProcessStepItem("2", "Ask Public Questions", "Leave moderated questions on posts to clarify requirements without private messaging.")
                            ProcessStepItem("3", "Submit 'I'm Interested' Inquiry", "Provide your background and consent to contact via official email.")
                            ProcessStepItem("4", "Receive Evaluation by Email", "Our advisory team follows up directly by email (worldpathemploymentservices@gmail.com).")
                        }
                    }
                }
            }

            // Featured Opportunities Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 20.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Featured Opportunities",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Actively recruiting verified positions",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    FilledTonalButton(
                        onClick = { viewModel.navigateTo(AppDestination.OPPORTUNITIES) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("See All Opportunities")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "See All",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Responsive Opportunities Layout (2 columns on wide screens, 1 column on phone)
            if (isWideScreen) {
                val chunks = allJobs.chunked(2)
                items(chunks) { rowJobs ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowJobs.forEach { job ->
                            Box(modifier = Modifier.weight(1f)) {
                                OpportunityCard(
                                    opportunity = job,
                                    isSaved = savedIds.contains(job.id),
                                    onSaveToggle = { viewModel.toggleSaveJob(it) },
                                    onViewDetails = { viewModel.selectJob(it) },
                                    onExpressInterest = { viewModel.openInterestForm(it) }
                                )
                            }
                        }
                        if (rowJobs.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            } else {
                items(allJobs) { job ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        OpportunityCard(
                            opportunity = job,
                            isSaved = savedIds.contains(job.id),
                            onSaveToggle = { viewModel.toggleSaveJob(it) },
                            onViewDetails = { viewModel.selectJob(it) },
                            onExpressInterest = { viewModel.openInterestForm(it) }
                        )
                    }
                }
            }

            // Informational Cards (About & FAQ shortcuts)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_about_service_card"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        onClick = { viewModel.navigateTo(AppDestination.ABOUT) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "About Our Service",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Who operates WorldPath, verification rules, and legal distinctions.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_faq_card"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        onClick = { viewModel.navigateTo(AppDestination.FAQ) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Frequently Asked Questions",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Answers on vetting, documents, communication, and scam prevention.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Official U.S. Resources Quick Bar
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Gavel,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Official Government Source Directory",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "For official visa requirements, statutory regulations, and prevailing wage standards, consult:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• USCIS (U.S. Citizenship & Immigration Services): uscis.gov\n• U.S. Department of State (Visa Services): travel.state.gov\n• U.S. Department of Labor (Foreign Labor Certification): dol.gov",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Footer
            item {
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
private fun ProcessStepItem(number: String, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
