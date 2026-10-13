package com.aicaller.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aicaller.app.core.design.*
import com.aicaller.app.features.home.HomeViewModel

@Composable
fun AppNavigation() {
    var selected by remember { mutableStateOf(AppDestination.Home) }
    Scaffold(
        containerColor = Ink,
        bottomBar = {
            NavigationBar(containerColor = Surface, tonalElevation = 0.dp) {
                AppDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = selected == destination,
                        onClick = { selected = destination },
                        icon = {
                            Icon(
                                imageVector = when (destination) {
                                    AppDestination.Home -> Icons.Rounded.Home
                                    AppDestination.Contacts -> Icons.Rounded.PeopleAlt
                                    AppDestination.History -> Icons.Rounded.History
                                    AppDestination.Assistant -> Icons.Rounded.AutoAwesome
                                    AppDestination.Settings -> Icons.Rounded.Settings
                                },
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Ink, selectedTextColor = Accent, indicatorColor = Accent,
                            unselectedIconColor = TextSecondary, unselectedTextColor = TextSecondary
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (selected) {
                AppDestination.Home -> HomeScreen(onNavigate = { selected = it })
                AppDestination.Contacts -> ContactsScreen()
                AppDestination.History -> HistoryScreen()
                AppDestination.Assistant -> AssistantScreen()
                AppDestination.Settings -> SettingsScreen()
            }
        }
    }
}

@Composable
private fun HomeScreen(onNavigate: (AppDestination) -> Unit, vm: HomeViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 22.dp, end = 22.dp, top = 24.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("AI CALLER", color = Accent, style = MaterialTheme.typography.labelLarge, letterSpacing = 2.sp)
                    Spacer(Modifier.height(5.dp))
                    Text("Your calls,\nhandled smarter.", style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
                }
                Box(Modifier.size(52.dp).clip(CircleShape).background(SurfaceElevated), contentAlignment = Alignment.Center) {
                    Icon(Icons.Rounded.GraphicEq, null, tint = Accent, modifier = Modifier.size(28.dp))
                }
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Accent), shape = RoundedCornerShape(28.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(9.dp).clip(CircleShape).background(Ink))
                        Spacer(Modifier.width(8.dp))
                        Text("ASSISTANT STATUS", color = Ink.copy(alpha = .7f), style = MaterialTheme.typography.labelLarge, letterSpacing = 1.2.sp)
                    }
                    Text("Ready when\nyou are.", color = Ink, style = MaterialTheme.typography.headlineMedium)
                    Text(state.statusMessage, color = Ink.copy(alpha = .78f), style = MaterialTheme.typography.bodyMedium)
                    Button(
                        onClick = vm::onTryAssistant,
                        colors = ButtonDefaults.buttonColors(containerColor = Ink, contentColor = TextPrimary),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        Icon(Icons.Rounded.Mic, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Try AI assistant")
                    }
                    Text("DEMO MODE · NO CALLS WILL BE PLACED", color = Ink.copy(alpha = .68f), style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        item { SectionHeading("Quick actions", "SHORTCUTS") }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionTile(Modifier.weight(1f), { Icon(Icons.Rounded.Call, null, tint = Accent) }, "Make a call", "Command-to-call") { onNavigate(AppDestination.Contacts) }
                ActionTile(Modifier.weight(1f), { Icon(Icons.Rounded.Shield, null, tint = Accent) }, "AI pickup", "Screen callers") { onNavigate(AppDestination.Assistant) }
            }
        }
        item { SectionHeading("Recent activity", "LATEST") }
        item {
            Surface(color = Surface, shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(44.dp).clip(CircleShape).background(SurfaceElevated), contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.CallReceived, null, tint = TextSecondary)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("No calls yet", color = TextPrimary, fontWeight = FontWeight.SemiBold)
                        Text("Your call summaries will appear here", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                    }
                    Icon(Icons.Rounded.ChevronRight, null, tint = TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun SectionHeading(title: String, eyebrow: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        Text(title, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        Text(eyebrow, style = MaterialTheme.typography.labelSmall, color = TextSecondary, letterSpacing = 1.2.sp)
    }
}

@Composable
private fun ActionTile(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(modifier = modifier.clickable(onClick = onClick), color = Surface, shape = RoundedCornerShape(22.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(42.dp).clip(RoundedCornerShape(14.dp)).background(SurfaceElevated), contentAlignment = Alignment.Center) { icon() }
            Text(title, color = TextPrimary, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, color = TextSecondary, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun PageHeader(eyebrow: String, title: String, description: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(eyebrow.uppercase(), color = Accent, style = MaterialTheme.typography.labelLarge, letterSpacing = 1.6.sp)
        Text(title, color = TextPrimary, style = MaterialTheme.typography.headlineLarge)
        Text(description, color = TextSecondary, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun ContactsScreen() {
    val contacts = listOf("Aarav Sharma" to "AS", "Priya Mehta" to "PM", "Rahul Verma" to "RV", "Neha Kapoor" to "NK")
    Column(Modifier.fillMaxSize().padding(horizontal = 22.dp, vertical = 26.dp)) {
        PageHeader("People", "Your contacts", "Choose someone to prepare a call.")
        Spacer(Modifier.height(24.dp))
        contacts.forEachIndexed { index, contact ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 7.dp).clip(RoundedCornerShape(18.dp)).background(Surface).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(46.dp).clip(CircleShape).background(SurfaceElevated), contentAlignment = Alignment.Center) {
                    Text(contact.second, color = Accent, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(contact.first, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    Text("Demo contact · " + (index + 1), color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                }
                IconButton(onClick = {}) { Icon(Icons.Rounded.Call, "Prepare call", tint = Accent) }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text("Demo contacts only — calling is not connected.", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun HistoryScreen() {
    Column(Modifier.fillMaxSize().padding(22.dp)) {
        PageHeader("Call intelligence", "Call history", "Summaries and transcripts will live here.")
        Spacer(Modifier.height(24.dp))
        EmptyState(Icons.Rounded.History, "Nothing to review", "When call services are connected, your activity and summaries will show up here.")
    }
}

@Composable
private fun AssistantScreen() {
    Column(Modifier.fillMaxSize().padding(22.dp)) {
        PageHeader("Your AI", "Assistant setup", "Build your call style before connecting a voice service.")
        Spacer(Modifier.height(24.dp))
        Surface(color = Surface, shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Icon(Icons.Rounded.AutoAwesome, null, tint = Accent, modifier = Modifier.size(30.dp))
                Text("Natural, helpful, in control.", color = TextPrimary, style = MaterialTheme.typography.titleLarge)
                Text("Future phases will add voice conversations, interruption handling, Hindi/Hinglish support, and caller screening where Android and carrier capabilities allow.", color = TextSecondary)
                HorizontalDivider(color = DividerColor)
                FeatureRow(Icons.Rounded.RecordVoiceOver, "Voice personality", "Choose how your assistant sounds")
                FeatureRow(Icons.Rounded.Language, "Language", "English, Hindi and Hinglish")
                FeatureRow(Icons.Rounded.PrivacyTip, "Privacy controls", "Review permissions before enabling")
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Not connected yet. This screen does not answer or place calls.", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun SettingsScreen() {
    Column(Modifier.fillMaxSize().padding(22.dp)) {
        PageHeader("Preferences", "Settings", "You stay in control of your calls.")
        Spacer(Modifier.height(24.dp))
        FeatureRow(Icons.Rounded.Lock, "Privacy & permissions", "No call permissions requested in Phase 01")
        HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 14.dp))
        FeatureRow(Icons.Rounded.Notifications, "Notifications", "Call activity alerts — planned")
        HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 14.dp))
        FeatureRow(Icons.Rounded.Security, "Security", "Voice credentials will stay server-side")
        HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 14.dp))
        FeatureRow(Icons.Rounded.Info, "About AI Caller", "Version 0.1.0 · Phase 01 starter")
    }
}

@Composable
private fun FeatureRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(Modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(SurfaceElevated), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = Accent)
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = TextSecondary, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun EmptyState(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, description: String) {
    Surface(color = Surface, shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
        Column(
            Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(Modifier.size(62.dp).clip(CircleShape).background(SurfaceElevated), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = Accent, modifier = Modifier.size(28.dp))
            }
            Text(title, color = TextPrimary, style = MaterialTheme.typography.titleLarge)
            Text(description, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
