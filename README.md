# AI Caller — Android

An Android-first starter for an AI-powered calling assistant. Phase 01 includes the app foundation, navigation, design system, and honest demo behavior.

## UI direction
The current UI uses a **hybrid theme**: a light blue/purple dashboard for daily use and a cinematic deep-blue/purple call-preview screen. The AI orb is drawn in Compose, so the interface does not depend on remote images.

## Screens
- **Home** — assistant overview, quick actions, use-case cards, and demo status.
- **Contacts** — sample contacts with a call-screen preview action.
- **AI Agents** — planned assistant capabilities and a preview-call entry point.
- **Calls** — empty call-history state, ready for later integration.
- **Profile / Settings** — privacy, voice, notifications, subscription, and about placeholders.
- **Cinematic call preview** — illustrative call controls and sample transcript; does not place a real call or connect to an AI voice service.

## Tech stack
- Kotlin, Jetpack Compose, Material 3
- MVVM with ViewModel + StateFlow
- JUnit and kotlinx-coroutines-test

## Run
Open the repository in Android Studio with JDK 17, sync Gradle, and run on an API 26+ emulator or device.

Windows:
```bat
gradlew.bat testDebugUnitTest
gradlew.bat assembleDebug
```

macOS/Linux:
```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

## Phase 01 status
- [x] Compose app foundation and navigation
- [x] Hybrid light dashboard and cinematic dark call-preview UI
- [x] Home, Contacts, AI Agents, Calls, and Settings screens
- [x] Explicit demo-only labeling and sample transcript
- [x] Unit tests for home state and navigation metadata
- [ ] Verify Android build and tests in CI/device
- [ ] Real carrier telephony and AI voice integration

## Safety and implementation notes
- The preview does not place calls, answer incoming calls, record audio, or save transcripts.
- No call/SMS permissions are requested in this starter.
- No API keys or secrets are embedded in the Android app.
- Android carrier-call behavior must be validated on real target devices before implementing call screening or automatic answering.
- Future voice services should use a secure server-side session/token endpoint rather than long-lived credentials in the client.

## Roadmap
1. Foundation and UI/UX
2. Android telephony feasibility spike
3. Low-latency AI voice prototype
4. Command-to-call flow
5. Incoming-call assistant where supported
6. Natural conversation and interruption handling
7. Call summaries and searchable history
8. Tasks and reminders
9. Privacy, consent, and security hardening
10. Release readiness
