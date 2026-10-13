package com.aicaller.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aicaller.app.core.design.*
import com.aicaller.app.features.home.HomeViewModel

private val Canvas = Color(0xFFF5F8FF)
private val Blue = Color(0xFF299BFF)
private val Violet = Color(0xFF8051FF)
private val Navy = Color(0xFF142451)
private val Muted = Color(0xFF7182A6)
private val Line = Color(0xFFE1E9FA)
private val Mint = Color(0xFF19C991)

@Composable
fun AppNavigation() {
    var selected by remember { mutableStateOf(AppDestination.Home) }
    var showCallDemo by remember { mutableStateOf(false) }
    if (showCallDemo) {
        DemoCallScreen(onEnd = { showCallDemo = false })
        return
    }
    Scaffold(
        containerColor = Canvas,
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                AppDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = selected == destination,
                        onClick = { selected = destination },
                        icon = {
                            Icon(
                                imageVector = when (destination) {
                                    AppDestination.Home -> Icons.Rounded.Home
                                    AppDestination.Contacts -> Icons.Rounded.PeopleAlt
                                    AppDestination.History -> Icons.Rounded.Call
                                    AppDestination.Assistant -> Icons.Rounded.AutoAwesome
                                    AppDestination.Settings -> Icons.Rounded.Person
                                },
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(if (destination == AppDestination.Assistant) "Agents" else if (destination == AppDestination.Settings) "Profile" else if (destination == AppDestination.History) "Calls" else destination.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Violet,
                            selectedTextColor = Violet,
                            indicatorColor = Color(0xFFEDE7FF),
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (selected) {
                AppDestination.Home -> HomeScreen(
                    onNavigate = { selected = it },
                    onDemoCall = { showCallDemo = true }
                )
                AppDestination.Contacts -> ContactsScreen(onDemoCall = { showCallDemo = true })
                AppDestination.History -> HistoryScreen()
                AppDestination.Assistant -> AssistantScreen(onDemoCall = { showCallDemo = true })
                AppDestination.Settings -> SettingsScreen()
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onNavigate: (AppDestination) -> Unit,
    onDemoCall: () -> Unit,
    vm: HomeViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFFF0F5FF), Color.White, Color(0xFFF8F5FF)))),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Hello, Jack 👋", color = Navy, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("Your AI calling assistant", color = Muted)
                }
                Box(Modifier.size(44.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Blue, Violet))), contentAlignment = Alignment.Center) {
                    Icon(Icons.Rounded.Person, null, tint = Color.White, modifier = Modifier.size(27.dp))
                }
            }
        }
        item {
            Surface(
                modifier = Modifier.fillMaxWidth().clickable { onNavigate(AppDestination.Assistant) },
                shape = RoundedCornerShape(26.dp),
                color = Color.Transparent
            ) {
                Box(Modifier.background(Brush.linearGradient(listOf(Color(0xFF37B9FF), Color(0xFF5B6CFF), Color(0xFF9B54FF)))).padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                            Text("Meet your AI", color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Text("Let your assistant help with the conversation.", color = Color.White.copy(alpha = .9f))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Explore agents", color = Color.White, fontWeight = FontWeight.SemiBold)
                                Spacer(Modifier.width(7.dp))
                                Icon(Icons.Rounded.ArrowForward, null, tint = Color.White)
                            }
                        }
                        BotOrb(size = 84.dp, cinematic = false)
                    }
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FeatureTile(Modifier.weight(1f), Icons.Rounded.AutoAwesome, "AI Agents", "Personal calling team", Violet) { onNavigate(AppDestination.Assistant) }
                FeatureTile(Modifier.weight(1f), Icons.Rounded.PeopleAlt, "Contacts", "Your saved people", Blue) { onNavigate(AppDestination.Contacts) }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FeatureTile(Modifier.weight(1f), Icons.Rounded.Call, "Call history", "Activity & summaries", Blue) { onNavigate(AppDestination.History) }
                FeatureTile(Modifier.weight(1f), Icons.Rounded.Description, "Templates", "Quick call scenarios", Violet) { onNavigate(AppDestination.Assistant) }
            }
        }
        item { LightSectionTitle("Popular use cases", "See all") }
        item {
            UseCaseRow(Icons.Rounded.BusinessCenter, "Business follow-ups", "Calls, reminders, updates", Blue) { onDemoCall() }
        }
        item {
            UseCaseRow(Icons.Rounded.CalendarMonth, "Appointment scheduling", "A demo of your future call assistant", Violet) { onDemoCall() }
        }
        item {
            Surface(color = Color.White, shape = RoundedCornerShape(18.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Line), modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Info, null, tint = Blue)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Demo mode", color = Navy, fontWeight = FontWeight.SemiBold)
                        Text(state.statusMessage, color = Muted, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
private fun LightSectionTitle(title: String, action: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = Navy, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(action, color = Violet, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun FeatureTile(modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, tint: Color, onClick: () -> Unit) {
    Surface(modifier = modifier.clickable(onClick = onClick), shape = RoundedCornerShape(22.dp), color = Color.White, border = androidx.compose.foundation.BorderStroke(1.dp, Line)) {
        Column(Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(Modifier.size(38.dp).clip(RoundedCornerShape(13.dp)).background(tint.copy(alpha = .12f)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = tint)
            }
            Text(title, color = Navy, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Muted, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun UseCaseRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, tint: Color, onClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), shape = RoundedCornerShape(18.dp), color = Color.White, border = androidx.compose.foundation.BorderStroke(1.dp, Line)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(42.dp).clip(CircleShape).background(tint.copy(alpha = .12f)), contentAlignment = Alignment.Center) { Icon(icon, null, tint = tint) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(title, color = Navy, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = Muted, style = MaterialTheme.typography.bodySmall)
            }
            Icon(Icons.Rounded.ChevronRight, null, tint = Muted)
        }
    }
}

@Composable
private fun ContactsScreen(onDemoCall: () -> Unit) {
    val contacts = listOf("Aarav Sharma" to "AS", "Priya Mehta" to "PM", "Rahul Verma" to "RV", "Neha Kapoor" to "NK")
    LazyColumn(Modifier.fillMaxSize().background(Canvas), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { PageHeader("PEOPLE", "Your contacts", "Choose someone to prepare a demo call.") }
        items(contacts.size) { index ->
            val contact = contacts[index]
            Surface(color = Color.White, shape = RoundedCornerShape(19.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Line)) {
                Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(46.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFFB9E9FF), Color(0xFFE1D5FF)))), contentAlignment = Alignment.Center) {
                        Text(contact.second, color = Navy, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(contact.first, color = Navy, fontWeight = FontWeight.SemiBold)
                        Text("Sample contact · no number stored", color = Muted, style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(onClick = onDemoCall) { Icon(Icons.Rounded.Call, "Preview call screen", tint = Blue) }
                }
            }
        }
        item { Text("Sample contacts only. No real call is placed.", color = Muted, style = MaterialTheme.typography.bodySmall) }
    }
}

@Composable
private fun HistoryScreen() {
    Column(Modifier.fillMaxSize().background(Canvas).padding(22.dp)) {
        PageHeader("CALL INTELLIGENCE", "Call history", "Your call summaries and transcripts will live here.")
        Spacer(Modifier.height(24.dp))
        Surface(color = Color.White, shape = RoundedCornerShape(24.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Line)) {
            Column(Modifier.fillMaxWidth().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                BotOrb(82.dp, false)
                Text("Nothing to review yet", color = Navy, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Real call history is not connected. This screen is a preview of where summaries will appear.", color = Muted)
            }
        }
    }
}

@Composable
private fun AssistantScreen(onDemoCall: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize().background(Canvas), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { PageHeader("YOUR AI TEAM", "AI Agents", "Design your future calling assistant.") }
        item {
            Surface(color = Color.White, shape = RoundedCornerShape(24.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Line)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BotOrb(72.dp, false)
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text("AI Assistant", color = Navy, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("Voice service not connected", color = Muted, style = MaterialTheme.typography.bodySmall)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(7.dp).clip(CircleShape).background(Color(0xFFF2AA43)))
                                Spacer(Modifier.width(6.dp))
                                Text("Preview only", color = Color(0xFF9B6A18), style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                    Text("Natural conversations, interruption handling, and Hindi/Hinglish support are planned for future phases.", color = Muted)
                    Button(onClick = onDemoCall, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Violet)) {
                        Icon(Icons.Rounded.PlayArrow, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Preview calling screen")
                    }
                    Text("This preview will not dial a number or connect to a voice model.", color = Muted, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        item { LightSectionTitle("Planned capabilities", "") }
        item { SettingRow(Icons.Rounded.RecordVoiceOver, "Voice personality", "Choose a voice and speaking style") }
        item { SettingRow(Icons.Rounded.Language, "Languages", "English, Hindi and Hinglish") }
        item { SettingRow(Icons.Rounded.PrivacyTip, "Privacy controls", "Permissions and consent before activation") }
    }
}

@Composable
private fun SettingsScreen() {
    LazyColumn(Modifier.fillMaxSize().background(Canvas), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { PageHeader("PREFERENCES", "Settings", "You stay in control of your calls.") }
        item {
            Surface(color = Color.White, shape = RoundedCornerShape(22.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Line)) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(48.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFFB8E8FF), Color(0xFFE0D5FF)))), contentAlignment = Alignment.Center) {
                            Icon(Icons.Rounded.Person, null, tint = Navy, modifier = Modifier.size(28.dp))
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text("AI Caller profile", color = Navy, fontWeight = FontWeight.Bold)
                            Text("Local demo profile", color = Muted, style = MaterialTheme.typography.bodySmall)
                        }
                        Icon(Icons.Rounded.ChevronRight, null, tint = Muted)
                    }
                }
            }
        }
        item { SettingRow(Icons.Rounded.Lock, "Account & security", "No account service connected") }
        item { SettingRow(Icons.Rounded.RecordVoiceOver, "Voice & language", "Voice selection planned") }
        item { SettingRow(Icons.Rounded.Notifications, "Notifications", "Call alerts planned") }
        item { SettingRow(Icons.Rounded.CreditCard, "Subscription", "Plans not available in demo") }
        item { SettingRow(Icons.Rounded.Help, "Help & support", "Setup information") }
        item { SettingRow(Icons.Rounded.Info, "About AI Caller", "Version 0.2.0 · UI preview") }
    }
}

@Composable
private fun SettingRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Surface(color = Color.White, shape = RoundedCornerShape(18.dp), border = androidx.compose.foundation.BorderStroke(1.dp, Line)) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(40.dp).clip(RoundedCornerShape(13.dp)).background(Color(0xFFF0EDFF)), contentAlignment = Alignment.Center) { Icon(icon, null, tint = Violet) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(title, color = Navy, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = Muted, style = MaterialTheme.typography.bodySmall)
            }
            Icon(Icons.Rounded.ChevronRight, null, tint = Muted)
        }
    }
}

@Composable
private fun DemoCallScreen(onEnd: () -> Unit) {
    var muted by remember { mutableStateOf(false) }
    var speaker by remember { mutableStateOf(true) }
    var showNotes by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF101A49), Color(0xFF142E70), Color(0xFF35206C))))) {
        Column(
            Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onEnd) { Icon(Icons.Rounded.Close, "Close preview", tint = Color.White) }
                Spacer(Modifier.weight(1f))
                Surface(color = Color.White.copy(alpha = .12f), shape = RoundedCornerShape(50)) {
                    Text("●  DEMO PREVIEW", color = Color(0xFFBCEBFF), modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(Modifier.weight(1f))
            Text("AI ASSISTANT", color = Color(0xFFB8C9FF), letterSpacing = 2.sp, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(10.dp))
            Text("Calling you…", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("No real call is active", color = Color(0xFFB8C9FF), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(28.dp))
            BotOrb(210.dp, true)
            Spacer(Modifier.height(28.dp))
            Text("“Hello! This is your AI assistant. I’m previewing how a future call might sound. Do you have a moment to talk?”", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(20.dp))
            Surface(color = Color.White.copy(alpha = .10f), shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.GraphicEq, null, tint = Color(0xFF8CDFFF))
                        Spacer(Modifier.width(8.dp))
                        Text("Sample transcript", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Text(if (muted) "Microphone muted in this visual preview." else "“Hello! This is your AI assistant. I’m calling about your upcoming meeting. Do you have a moment to talk?”", color = Color(0xFFDCE5FF), style = MaterialTheme.typography.bodySmall)
                    if (showNotes) Text("Notes preview: no call was recorded and no transcript was saved.", color = Color(0xFFB8C9FF), style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(Modifier.weight(1f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CallControl(Icons.Rounded.MicOff, if (muted) "Unmute" else "Mute", muted) { muted = !muted }
                CallControl(Icons.Rounded.Dialpad, "Keypad", false) { showNotes = !showNotes }
                CallControl(Icons.Rounded.VolumeUp, "Speaker", speaker) { speaker = !speaker }
            }
            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onEnd, shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F67)), modifier = Modifier.size(70.dp), contentPadding = PaddingValues(0.dp)) {
                    Icon(Icons.Rounded.CallEnd, "End preview", tint = Color.White, modifier = Modifier.size(30.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("Preview only · no carrier call · no AI connection", color = Color(0xFFB8C9FF), style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun CallControl(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, active: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.clickable(onClick = onClick)) {
        Box(Modifier.size(56.dp).clip(CircleShape).background(if (active) Color(0xFFB5DFFF) else Color.White.copy(alpha = .12f)), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = if (active) Navy else Color.White, modifier = Modifier.size(24.dp))
        }
        Text(label, color = Color.White, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun BotOrb(size: androidx.compose.ui.unit.Dp, cinematic: Boolean) {
    Box(
        Modifier.size(size).background(
            Brush.radialGradient(
                listOf(Color(0xFFB5F1FF).copy(alpha = if (cinematic) .35f else .9f), Color(0xFF7D79FF).copy(alpha = .65f), Color.Transparent)
            ),
            CircleShape
        ),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.fillMaxSize(.78f).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFF48D7FF), Color(0xFF7168FF), Color(0xFFCE83FF)))), contentAlignment = Alignment.Center) {
            Box(Modifier.fillMaxSize(.76f).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFF172F77), Color(0xFF071A4B)))), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(size * .07f)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(size * .13f)) {
                        Box(Modifier.size(size * .11f).clip(CircleShape).background(Color(0xFF8FE8FF)))
                        Box(Modifier.size(size * .11f).clip(CircleShape).background(Color(0xFF8FE8FF)))
                    }
                    Box(Modifier.width(size * .22f).height(size * .055f).clip(RoundedCornerShape(50)).background(Color(0xFF8FE8FF)))
                }
            }
        }
    }
}

@Composable
private fun PageHeader(eyebrow: String, title: String, description: String) {
    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(eyebrow, color = Violet, style = MaterialTheme.typography.labelLarge, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
        Text(title, color = Navy, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text(description, color = Muted, style = MaterialTheme.typography.bodyLarge)
    }
}
