# AI Caller — Full Product Roadmap and Implementation Status

This roadmap is intentionally explicit about the difference between shipped starter scaffolding and integrations that require provider credentials, device testing, legal review, and backend deployment.

## Current repository status
- Phase 01: Android UI foundation and hybrid visual direction implemented; CI workflow configured.
- Cross-phase foundation: provider-neutral interfaces for telephony and voice, call/agent models, and consent policy tests added.
- Real carrier calls, autonomous answering, real-time AI voice, cloud persistence, and production release are **not implemented** by these interfaces alone.

## Phase 01 — Foundation and UI
- [x] Compose app shell and navigation
- [x] Light dashboard / cinematic call preview
- [x] Contacts, Agents, Calls, Settings screens with honest demo labels
- [x] Unit tests and CI workflow
- [ ] Verify latest commit on a physical Android device

## Phase 02 — Android telephony feasibility
- [x] Provider/platform boundary defined
- [ ] Validate default-dialer role and InCallService requirements on target Android versions
- [ ] Test carrier/OEM behavior on multiple physical devices
- [ ] Add permissions only after a supported, user-approved flow is chosen
- [ ] Document emergency-call and unsupported-device behavior

## Phase 03 — Low-latency AI voice prototype
- [x] Provider-neutral voice interface
- [ ] Choose voice provider and region/data-retention policy
- [ ] Deploy secure backend session-token endpoint
- [ ] Add real streaming audio and interruption/barge-in handling
- [ ] Measure end-to-end latency, packet loss, and failure recovery

## Phase 04 — User-commanded outbound calls
- [x] Call record and call request contracts
- [ ] Add explicit confirmation before placing a carrier call
- [ ] Resolve contact and phone number with user permission
- [ ] Connect supported Android Telecom intent / dialer flow
- [ ] Add call state, cancel, retry and failure handling

## Phase 05 — Incoming call assistance
- [ ] Determine feasible role: default dialer, call screening, or supported telecom integration
- [ ] Implement only within Android APIs, carrier behavior, and Play policy
- [ ] Require visible opt-in and a manual disable control
- [ ] Test unanswered, rejected, private, emergency and dual-SIM scenarios

## Phase 06 — Natural AI agent
- [x] Agent configuration contract
- [ ] Define permitted tasks and escalation / human handoff rules
- [ ] Add language selection and Hindi/Hinglish evaluation set
- [ ] Add interruption handling, silence detection, timeouts and fallback scripts
- [ ] Clearly disclose AI identity when required and avoid impersonation

## Phase 07 — Call intelligence
- [x] Call record and transcript event contracts
- [ ] Add opt-in transcription and summary generation
- [ ] Add encrypted local/remote storage and deletion/export controls
- [ ] Add searchable history and retention controls
- [ ] Test consent, redaction and access boundaries

## Phase 08 — Tasks and reminders
- [ ] Add typed task model and confirmation-before-action policy
- [ ] Integrate Android notifications / calendar only with explicit permission
- [ ] Add duplicate prevention, timezone handling and retry-safe scheduling
- [ ] Test cancelled and rescheduled tasks

## Phase 09 — Privacy and security
- [x] Consent policy unit test baseline
- [ ] Threat model and data-flow diagram
- [ ] Backend auth, rate limits, abuse monitoring, short-lived credentials
- [ ] Encryption, secure logging, data retention and account deletion
- [ ] Dependency scanning, secret scanning and security review

## Phase 10 — Release readiness
- [x] CI workflow configured
- [ ] CI green on the latest UI + contracts commits
- [ ] Accessibility, contrast, screen-reader and small-screen review
- [ ] Privacy policy, terms, support contact, store listing and disclosure review
- [ ] Staged rollout, crash monitoring and rollback plan

## Required production inputs
Before real calling can be completed, the project owner must choose and provide access to the required voice provider/backend, define supported countries/carriers, agree on recording and retention behavior, and test the target devices. Do not put provider API keys in the Android app.
