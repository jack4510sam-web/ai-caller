# AI Caller — Android

An Android-first starter project for an AI-powered calling assistant. Phase 01 focuses on the app foundation, navigation, design system, screen scaffolding, and testable demo behavior.

> **Status:** UI starter / demo mode. Voice calling, automatic call answering, telephony integration, and AI services are not connected yet.

## Screens
- **Home** — assistant status, quick actions, recent activity.
- **Contacts** — starter contact list and call-intent entry point.
- **History** — call activity and summary placeholders.
- **AI Assistant** — assistant capabilities and demo status.
- **Settings** — privacy and app preference placeholders.

## Tech stack
- Kotlin, Jetpack Compose, Material 3
- MVVM with ViewModel + StateFlow
- Lightweight in-app navigation
- JUnit and kotlinx-coroutines-test

## Run
1. Open this repository in the latest stable Android Studio.
2. Use JDK 17.
3. Allow Gradle sync to complete.
4. Run the app on an API 26+ emulator or device.

Windows commands: gradlew.bat testDebugUnitTest and gradlew.bat assembleDebug
macOS/Linux commands: ./gradlew testDebugUnitTest and ./gradlew assembleDebug

## Project layout
- app/src/main/java/com/aicaller/app/
- app/src/main/java/com/aicaller/app/core/design/
- app/src/main/java/com/aicaller/app/navigation/
- app/src/main/java/com/aicaller/app/features/
- app/src/test/java/com/aicaller/app/

## Phase 01 scope
- [x] Android project and Compose foundation
- [x] Shared colors, typography, and theme
- [x] Home, Contacts, History, AI, and Settings screens
- [x] Honest demo-mode status
- [x] Unit tests for initial state, demo action, and destinations
- [ ] Device/emulator build verification (requires Android SDK/Gradle environment)
- [ ] Real telephony and AI integration (future phases)

## Important notes
- No API keys or secrets are stored in the Android app.
- No call/SMS permissions are requested in this starter.
- Android carrier-call behavior must be validated on real target devices before implementing call screening or automatic answering.
- Future voice services should use secure server-side session/token endpoints, not long-lived credentials embedded in the client.

## Roadmap
1. Foundation and UI/UX (this phase)
2. Android telephony feasibility spike
3. Low-latency AI voice prototype
4. Command-to-call flow
5. Incoming-call assistant where supported
6. Natural conversation and interruption handling
7. Call summaries and searchable history
8. Tasks and reminders
9. Privacy, consent, and security hardening
10. Release readiness
