# Production readiness checklist

## Project-owner setup
- [ ] Create/configure Firebase project and enable authentication.
- [ ] Configure server-side OpenAI key and billing limits.
- [ ] Deploy backend with managed identity and HTTPS.
- [ ] Decide supported Android call flow and test carriers/devices.
- [ ] Review privacy policy, consent, retention, and deletion.
- [ ] Complete accessibility and physical-device QA.

## Backend
- [x] OpenAI key stays server-side.
- [x] Firebase ID-token verification gate.
- [x] Short-lived Realtime client-secret endpoint.
- [x] Rate limiting and generic upstream errors.
- [ ] Deploy and smoke-test with real credentials.
- [ ] Add App Check, quotas, monitoring, and per-user rate controls.

## Android and telephony
- [x] Provider-neutral voice and telephony interfaces.
- [ ] Android Realtime audio/WebRTC transport.
- [ ] Runtime microphone permission and rationale.
- [x] Explicit user-confirmed outbound dialer flow using Android ACTION_DIAL.
- [ ] Validate call screening/default-dialer APIs and Play policy.
- [ ] Test audio focus, Bluetooth, interruption, lifecycle, and supported devices.
- [ ] Validate emergency and unsupported-call scenarios.

## AI behavior and privacy
- [x] AI disclosure and confirmation-first instructions in backend session configuration.
- [ ] Human handoff/stop controls in live voice.
- [ ] Explicit consent before recording or storing transcripts.
- [ ] Hindi/Hinglish evaluation and failure-path tests.
- [ ] Retention, export, deletion, and security review.

## Release
- [ ] Current CI green (check latest run after each commit).
- [ ] Physical-device QA.
- [ ] Privacy/security review.
- [ ] Signed release and store disclosures.
