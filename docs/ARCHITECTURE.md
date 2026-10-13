# Architecture Notes

## Boundaries
- UI: Jetpack Compose screens in navigation package
- State: ViewModel and StateFlow
- Telephony: `TelephonyGateway` isolates Android Telecom behavior
- Voice: `VoiceAgentGateway` isolates provider-specific streaming/audio
- Models: `CallRecord`, `AssistantProfile`
- Privacy: `ConsentPolicy` provides a small, testable baseline for recording gates

## Production rules
1. A button preview is not a real call. Keep demo labels visible until a real backend reports connected state.
2. Do not store long-lived voice-provider secrets in the app. Mint short-lived session credentials server-side.
3. Do not record or transmit audio/transcripts without informed, explicit consent and jurisdiction review.
4. Never make emergency-call support claims until verified end-to-end with carriers and devices.
5. Confirm consequential tasks such as appointments, purchases, or messages before execution.
6. Make human handoff, stop, delete, and opt-out controls easy to find.

## Suggested service deployment
Android app -> authenticated backend -> provider session/token endpoint -> real-time voice provider.
The backend should own credentials, policy enforcement, rate limits, audit events, and retention/deletion workflows. No production backend is included yet.
