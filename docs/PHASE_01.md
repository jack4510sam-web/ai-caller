# Phase 01 — Foundation

## Included
- Kotlin + Jetpack Compose Android app scaffold
- Material 3 dark theme with charcoal surfaces and a lime accent
- Home, Contacts, History, AI Assistant, and Settings navigation
- Demo-only sample data and clear status messaging
- Unit tests for ViewModel state and destination metadata
- No call/SMS permissions and no embedded credentials

## Not included yet
- Carrier call interception or automatic answering
- Real outbound calls from an AI agent
- Realtime voice API connection
- Cloud backend, authentication, or stored transcripts

## Acceptance checklist
- [ ] Open in Android Studio with JDK 17
- [ ] Gradle sync succeeds
- [ ] Run testDebugUnitTest
- [ ] Run assembleDebug
- [ ] Verify layout on small and large phones
- [ ] Validate Android Telecom/default-dialer feasibility before Phase 02
