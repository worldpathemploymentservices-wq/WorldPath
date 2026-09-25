package com.example.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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

data class FaqItem(
    val id: Int,
    val question: String,
    val answer: String,
    val category: String = "General"
)

@Composable
fun FaqScreen(
    viewModel: MainViewModel,
    isWideScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var expandedId by remember { mutableStateOf<Int?>(1) }

    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    if (showPrivacyDialog) {
        PrivacyPolicyDialog(onDismiss = { showPrivacyDialog = false })
    }
    if (showTermsDialog) {
        TermsOfUseDialog(onDismiss = { showTermsDialog = false })
    }

    val allFaqs = remember {
        listOf(
            FaqItem(
                id = 1,
                question = "How do I find an opportunity?",
                answer = "Navigate to the 'Opportunities' page to browse verified active openings. You can filter by industry category (Logistics, Healthcare, Skilled Trades, Hospitality, Agriculture, Technology) or search by job title, location, and keywords. Each post details the verified job scope, requirements, schedule, and wage rate."
            ),
            FaqItem(
                id = 2,
                question = "How do I express interest in a post?",
                answer = "When reviewing an opportunity, click the 'I'm Interested' button to open the inquiry form. Enter your full name, email address, country of residence, optional phone number, and a brief summary of your qualifications. Once submitted, your inquiry is securely recorded and delivered to our advisory team."
            ),
            FaqItem(
                id = 3,
                question = "How will I be contacted after expressing interest?",
                answer = "All communication is conducted exclusively via official, verifiable email from worldpathemploymentservices@gmail.com. We do not use WhatsApp, Telegram, or instant social messenger chat. Please monitor your inbox and spam/junk folders for messages from our team."
            ),
            FaqItem(
                id = 4,
                question = "What documents may an employer require?",
                answer = "Employers typically require:\n• A detailed professional resume / CV\n• Government-issued passport or national identity proof\n• Educational diplomas or degree certificates (with NACES-approved foreign credential evaluations when applicable)\n• Professional licenses or certifications (e.g. CNA, BLS, CNC certificates, commercial licenses)\n• Verifiable reference letters from previous employers or supervisors\n• Police clearance or criminal background check certificates."
            ),
            FaqItem(
                id = 5,
                question = "Is employment guaranteed?",
                answer = "NO. Employment is never guaranteed. Hiring decisions are made strictly and solely by prospective U.S. employers based on competitive interview performance, document verification, reference checks, and organizational staffing needs. WorldPath provides objective information and candidate pre-screening advisory services."
            ),
            FaqItem(
                id = 6,
                question = "Does the service guarantee a U.S. visa or work authorization?",
                answer = "NO. Neither WorldPath Employment Services nor any employer can guarantee a U.S. visa, work permit, or immigration outcome. Under U.S. law, visa issuance rests solely in the jurisdiction of Consular officers of the U.S. Department of State and adjudicators at U.S. Citizenship and Immigration Services (USCIS). Any claim of a 'guaranteed visa' is fraudulent."
            ),
            FaqItem(
                id = 7,
                question = "How can I identify legitimate employment opportunities and avoid scams?",
                answer = "Hallmarks of legitimate U.S. employment opportunities include:\n• Zero upfront fees: Legitimate employers and reputable agencies never demand payment for application, interview, or placement.\n• Verifiable employer details: Verifiable U.S. Department of Labor ETA filing numbers and verified corporate addresses.\n• Prevailing wage compliance: Wages matching official U.S. Department of Labor local wage databases.\n• Official communications: Formal correspondence through official corporate/service email rather than anonymous chat apps."
            ),
            FaqItem(
                id = 8,
                question = "Are there any fees charged to job seekers?",
                answer = "NO. WorldPath Employment Services charges ZERO fees to job seekers to view opportunities, ask questions, or submit expressions of interest. Under ethical recruitment standards and U.S. Department of Labor regulations, recruitment costs are borne by employers, not job applicants."
            ),
            FaqItem(
                id = 9,
                question = "Why is there no live chat or private messaging?",
                answer = "WorldPath operates strictly as an employment information and verified application inquiry service. We intentionally do NOT provide instant private messaging or dating-style chat rooms to prevent fraud, protect applicant data privacy, and maintain a verifiable, professional email paper trail for all inquiries."
            ),
            FaqItem(
                id = 10,
                question = "How are comments and questions on job posts handled?",
                answer = "Questions submitted on individual job posts are published in the public Q&A section so that all interested candidates can read clarifications. All comments are actively moderated by the site administrator to remove abusive language, fraudulent spam links, or personal contact numbers before or after publication."
            )
        )
    }

    val filteredFaqs = remember(searchQuery) {
        if (searchQuery.isBlank()) allFaqs
        else allFaqs.filter {
            it.question.contains(searchQuery, ignoreCase = true) ||
                    it.answer.contains(searchQuery, ignoreCase = true)
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 1000.dp)
                .testTag("faq_screen"),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Back Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 24.dp else 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("faq_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Frequently Asked Questions",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        // Header Description
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Application & Guidance FAQ",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Clear answers regarding application procedures, vetting requirements, communications, and anti-fraud protections.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Search Bar
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("faq_search_input"),
                    placeholder = { Text("Search FAQ topics...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        // FAQ Items Accordion
        items(filteredFaqs) { faq ->
            val isExpanded = expandedId == faq.id
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("faq_item_${faq.id}"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedId = if (isExpanded) null else faq.id
                            }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = faq.question,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = if (isExpanded) "Collapse" else "Expand",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column {
                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = faq.answer,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Still have questions card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Still have a question?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Reach out to our team directly via our Contact page or write to worldpathemploymentservices@gmail.com. We respond within 1–2 business days.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
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
