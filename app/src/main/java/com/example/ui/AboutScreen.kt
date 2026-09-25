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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppFooter
import com.example.ui.components.PrivacyPolicyDialog
import com.example.ui.components.TermsOfUseDialog

@Composable
fun AboutScreen(
    viewModel: MainViewModel,
    isWideScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
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
                .widthIn(max = 1000.dp)
                .testTag("about_screen"),
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
                        modifier = Modifier.testTag("about_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "About WorldPath Employment Services",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // About Organization Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Business,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Who Operates This Service",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "WorldPath Employment Services",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "WorldPath Employment Services is an independent international employment information, career advisory, and candidate pre-screening portal headquartered in the United States.\n\nOur service coordinates with verified U.S. commercial employers across warehousing, logistics, health support, skilled manufacturing, hospitality, and agriculture to aggregate genuine job openings and publish transparent, verifiable qualification criteria.",
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // What The Service Provides
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "What The Service Provides",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        AboutServiceItem(
                            title = "Verified Opportunity Postings",
                            desc = "Direct listings from verified employers adhering to U.S. Department of Labor prevailing wage standards and bona fide job descriptions."
                        )
                        AboutServiceItem(
                            title = "Clear Qualification Criteria",
                            desc = "Objective, upfront requirements including education evaluations, language proficiency standards, and professional credentials."
                        )
                        AboutServiceItem(
                            title = "Email-Based Expression of Interest",
                            desc = "A structured, secure inquiry channel without private messaging or instant chatting rooms, ensuring professional recordkeeping."
                        )
                        AboutServiceItem(
                            title = "Public Moderated Q&A",
                            desc = "Transparent question-and-answer sections on each post so all candidates benefit from verified advisor clarifications."
                        )
                    }
                }
            }

            // MANDATORY STATUTORY & LEGAL DISCLAIMERS
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.25f)
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Legal Notice",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Mandatory Legal & Non-Guarantee Disclaimers",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "1. NOT A GOVERNMENT AGENCY:\nWorldPath Employment Services is a private entity. We are NOT a government agency (such as USCIS, the U.S. Department of State, or the U.S. Department of Labor) and we do not represent the United States Government.",
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 21.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "2. NO EMPLOYMENT OR VISA GUARANTEE:\nWorldPath Employment Services does NOT guarantee employment, interview selection, visa approval, work permits, or immigration outcomes. All hiring decisions remain strictly with prospective employers. All visa and immigration adjudications are made solely by U.S. Consular officers and U.S. Citizenship & Immigration Services.",
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 21.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "3. ZERO APPLICATION FEES:\nWe never charge candidates application fees or recruitment fees. Any demand for payment should be reported immediately.",
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 21.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Distinction Between Employment vs Legal Information
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "Clear Information Distinctions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        DistinctionRow(
                            category = "Employment Opportunities:",
                            detail = "Descriptions of open roles, verified wages, job locations, and employer requirements."
                        )
                        DistinctionRow(
                            category = "Employer Requirements:",
                            detail = "Education, language skills, certifications, and experience required directly by the company."
                        )
                        DistinctionRow(
                            category = "General Information:",
                            detail = "Educational guides on U.S. workplace culture, interview preparation, and document checklists."
                        )
                        DistinctionRow(
                            category = "Immigration & Legal Information:",
                            detail = "We provide only links to official U.S. government sources. We do not provide legal representation or immigration counsel."
                        )
                    }
                }
            }

            // Official Government Links Directory
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    )
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Gavel,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Official Government Source Directory",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "• U.S. Citizenship and Immigration Services:\n  www.uscis.gov\n\n• U.S. Department of State – Bureau of Consular Affairs:\n  travel.state.gov\n\n• U.S. Department of Labor – Foreign Labor Certification:\n  www.dol.gov/agencies/eta/foreign-labor",
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // CTA buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Button(
                        onClick = { viewModel.navigateTo(AppDestination.OPPORTUNITIES) },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("View Opportunities")
                    }

                    OutlinedButton(
                        onClick = { viewModel.navigateTo(AppDestination.CONTACT) },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Contact Us")
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
private fun AboutServiceItem(title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(20.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DistinctionRow(category: String, detail: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = detail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
