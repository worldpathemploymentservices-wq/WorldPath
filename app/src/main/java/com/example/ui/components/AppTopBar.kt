package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentDestination: AppDestination,
    isWideScreen: Boolean,
    onNavigate: (AppDestination) -> Unit,
    onShowTerms: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp,
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isWideScreen) {
            // Desktop & Tablet Full Website Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .testTag("app_top_bar_desktop"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand logo & title (Clickable to Home)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onNavigate(AppDestination.HOME) }
                        .padding(4.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "WorldPath Emblem",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "WorldPath Employment Services",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Official Employment & Application Information Portal",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    }
                }

                // Desktop Navigation Links
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    WebNavLink(
                        text = "Home",
                        isSelected = currentDestination == AppDestination.HOME,
                        onClick = { onNavigate(AppDestination.HOME) }
                    )

                    WebNavLink(
                        text = "Opportunities",
                        isSelected = currentDestination == AppDestination.OPPORTUNITIES || currentDestination == AppDestination.OPPORTUNITY_DETAIL,
                        onClick = { onNavigate(AppDestination.OPPORTUNITIES) }
                    )

                    WebNavLink(
                        text = "About Us",
                        isSelected = currentDestination == AppDestination.ABOUT,
                        onClick = { onNavigate(AppDestination.ABOUT) }
                    )

                    WebNavLink(
                        text = "FAQ",
                        isSelected = currentDestination == AppDestination.FAQ,
                        onClick = { onNavigate(AppDestination.FAQ) }
                    )

                    WebNavLink(
                        text = "Contact",
                        isSelected = currentDestination == AppDestination.CONTACT,
                        onClick = { onNavigate(AppDestination.CONTACT) }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Primary Header CTA
                    Button(
                        onClick = { onNavigate(AppDestination.INTEREST_FORM) },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("top_bar_desktop_interest_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AssignmentTurnedIn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "I'm Interested",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Admin Moderation shortcut
                    IconButton(
                        onClick = { onNavigate(AppDestination.MODERATION) },
                        modifier = Modifier.testTag("top_bar_moderation_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Moderation Dashboard",
                            tint = if (currentDestination == AppDestination.MODERATION)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Legal notice shortcut
                    IconButton(
                        onClick = onShowTerms,
                        modifier = Modifier.testTag("top_bar_terms_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = "Legal Disclaimers",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            // Mobile Compact Header
            TopAppBar(
                modifier = Modifier.testTag("app_top_bar_mobile"),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onNavigate(AppDestination.HOME) }
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "WorldPath Emblem",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "WorldPath",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Employment Services",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onNavigate(AppDestination.FAQ) },
                        modifier = Modifier.testTag("top_bar_faq_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "FAQ",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = onShowTerms,
                        modifier = Modifier.testTag("top_bar_terms_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = "Legal Disclaimers",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { onNavigate(AppDestination.MODERATION) },
                        modifier = Modifier.testTag("top_bar_moderation_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Moderation",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Composable
private fun WebNavLink(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
