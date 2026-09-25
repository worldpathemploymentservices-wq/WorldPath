package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.ContactMail
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppDestination

data class NavItem(
    val destination: AppDestination,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun AppBottomBar(
    currentDestination: AppDestination,
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(
            destination = AppDestination.HOME,
            label = "Home",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavItem(
            destination = AppDestination.OPPORTUNITIES,
            label = "Opportunities",
            selectedIcon = Icons.Default.Work,
            unselectedIcon = Icons.Outlined.WorkOutline
        ),
        NavItem(
            destination = AppDestination.INTEREST_FORM,
            label = "Interested",
            selectedIcon = Icons.Default.AssignmentTurnedIn,
            unselectedIcon = Icons.Outlined.AssignmentTurnedIn
        ),
        NavItem(
            destination = AppDestination.ABOUT,
            label = "About Us",
            selectedIcon = Icons.Default.Info,
            unselectedIcon = Icons.Outlined.Info
        ),
        NavItem(
            destination = AppDestination.FAQ,
            label = "FAQ",
            selectedIcon = Icons.Default.HelpOutline,
            unselectedIcon = Icons.Outlined.HelpOutline
        ),
        NavItem(
            destination = AppDestination.CONTACT,
            label = "Contact",
            selectedIcon = Icons.Default.ContactMail,
            unselectedIcon = Icons.Outlined.ContactMail
        )
    )

    NavigationBar(
        modifier = modifier.testTag("app_bottom_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = when (item.destination) {
                AppDestination.OPPORTUNITIES -> currentDestination == AppDestination.OPPORTUNITIES || currentDestination == AppDestination.OPPORTUNITY_DETAIL
                else -> currentDestination == item.destination
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.destination) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.testTag("nav_item_${item.label.lowercase().replace(" ", "_")}")
            )
        }
    }
}
