package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.AboutScreen
import com.example.ui.AppDestination
import com.example.ui.ContactScreen
import com.example.ui.FaqScreen
import com.example.ui.HomeScreen
import com.example.ui.InterestFormScreen
import com.example.ui.MainViewModel
import com.example.ui.ModerationScreen
import com.example.ui.OpportunitiesScreen
import com.example.ui.OpportunityDetailScreen
import com.example.ui.components.AppBottomBar
import com.example.ui.components.AppTopBar
import com.example.ui.components.TermsOfUseDialog
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val currentDestination by viewModel.currentDestination.collectAsState()
                val selectedJob by viewModel.selectedJob.collectAsState()
                val notification by viewModel.notification.collectAsState()

                val snackbarHostState = remember { SnackbarHostState() }

                var showTermsDialog by remember { mutableStateOf(false) }

                if (showTermsDialog) {
                    TermsOfUseDialog(onDismiss = { showTermsDialog = false })
                }

                // Handle browser / system back navigation
                BackHandler(enabled = currentDestination != AppDestination.HOME) {
                    viewModel.navigateBack()
                }

                // Show notification messages (e.g. comment posted, inquiry received, etc.)
                LaunchedEffect(notification) {
                    notification?.let { note ->
                        snackbarHostState.showSnackbar(
                            message = "${note.title}: ${note.message}"
                        )
                        viewModel.clearNotification()
                    }
                }

                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val isWideScreen = maxWidth >= 768.dp

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            AppTopBar(
                                currentDestination = currentDestination,
                                isWideScreen = isWideScreen,
                                onNavigate = { viewModel.navigateTo(it) },
                                onShowTerms = { showTermsDialog = true }
                            )
                        },
                        bottomBar = {
                            // Only render bottom bar on mobile viewports; desktop and tablet browsers use top nav
                            if (!isWideScreen) {
                                AppBottomBar(
                                    currentDestination = currentDestination,
                                    onNavigate = { viewModel.navigateTo(it) }
                                )
                            }
                        },
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            AnimatedContent(
                                targetState = currentDestination,
                                transitionSpec = { fadeIn() togetherWith fadeOut() },
                                label = "MainScreenNavigation"
                            ) { destination ->
                                when (destination) {
                                    AppDestination.HOME -> {
                                        HomeScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                    AppDestination.OPPORTUNITIES -> {
                                        OpportunitiesScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                    AppDestination.OPPORTUNITY_DETAIL -> {
                                        selectedJob?.let { job ->
                                            OpportunityDetailScreen(
                                                opportunity = job,
                                                viewModel = viewModel,
                                                isWideScreen = isWideScreen
                                            )
                                        } ?: run {
                                            OpportunitiesScreen(
                                                viewModel = viewModel,
                                                isWideScreen = isWideScreen
                                            )
                                        }
                                    }
                                    AppDestination.INTEREST_FORM -> {
                                        InterestFormScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                    AppDestination.ABOUT -> {
                                        AboutScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                    AppDestination.FAQ -> {
                                        FaqScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                    AppDestination.CONTACT -> {
                                        ContactScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                    AppDestination.MODERATION -> {
                                        ModerationScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                    AppDestination.MY_INQUIRIES -> {
                                        InterestFormScreen(
                                            viewModel = viewModel,
                                            isWideScreen = isWideScreen
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
