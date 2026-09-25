package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrivacyPolicyDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Privacy & Data Protection Policy",
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "WorldPath Employment Services is committed to transparency, applicant data privacy, and ethical recruitment practices.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "1. Information We Collect:",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "We only collect information voluntarily provided in 'I'm Interested' or Contact submissions (Name, Email, Country of Residence, Phone, and professional message). We NEVER request sensitive financial details, bank PINs, card numbers, or passwords.",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "2. How We Use Information:",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Information is used exclusively to evaluate job qualification matches and contact applicants via official email (worldpathemploymentservices@gmail.com). We never sell, rent, or trade your personal information to third-party marketers.",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "3. Communications:",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "All communications are conducted via secure, verifiable email. We do not use unmoderated instant private messaging or dating-style chat rooms.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun TermsOfUseDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Terms of Use & Legal Disclaimers",
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "IMPORTANT LEGAL NOTICE & NON-GUARANTEE:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "1. Non-Government Status: WorldPath Employment Services is a private advisory and employment information portal. We are NOT affiliated with, endorsed by, or an agency of the United States Government (including USCIS, Department of State, or Department of Labor).",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "2. No Guarantee: WorldPath Employment Services DOES NOT guarantee employment offers, interview selections, prevailing wage outcomes, work authorizations, visa approvals, or immigration outcomes. All hiring decisions are strictly at the sole discretion of verified prospective employers, and all visa decisions rest exclusively with U.S. Consular officers and U.S. Citizenship and Immigration Services (USCIS).",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "3. Zero Application Fees: Legitimate employment opportunities do not require upfront job-placement fees. If anyone demands money on behalf of WorldPath, report it immediately to worldpathemploymentservices@gmail.com.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("I Understand")
            }
        }
    )
}
