# AI Caller voice-session backend

Deployable starter, not a deployed service. It issues short-lived OpenAI Realtime client secrets only after Firebase ID-token verification. The OpenAI API key remains server-side.

## Endpoints
- `GET /health`: health check.
- `POST /v1/voice/session`: requires `Authorization: Bearer <Firebase ID token>`; returns a short-lived Realtime client secret. Rate limit: 5 requests/minute/IP.

## Configure
1. Create a Firebase project and enable an authentication provider for the Android app.
2. Configure Application Default Credentials on the hosting platform and set `FIREBASE_PROJECT_ID`.
3. Set `OPENAI_API_KEY` as a server-side hosting secret. Never add it to Android resources or Git.
4. Optionally set `ALLOWED_ORIGINS` for browser clients.
5. Use Node.js 20+.

Local commands:
```bash
cp .env.example .env
npm install
npm run build
npm test
npm start
```

The sample values are placeholders. Firebase auth and a valid OpenAI API key are required for session issuance.

## Security
- Deploy behind HTTPS.
- Add App Check or equivalent abuse protection before public launch.
- Configure billing alerts, quotas, monitoring, and per-user abuse controls.
- Never log secrets, audio, transcripts, or personal data.
- Review consent and applicable laws before enabling recording/transcription.
- The backend creates voice sessions only. It does not bridge carrier phone audio or implement incoming-call answering.
