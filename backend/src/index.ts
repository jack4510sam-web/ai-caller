import express from "express";
import cors from "cors";
import { rateLimit } from "express-rate-limit";
import { initializeApp, applicationDefault, getApps } from "firebase-admin/app";
import { getAuth } from "firebase-admin/auth";

const app = express();
const port = Number(process.env.PORT ?? 8080);
const allowedOrigins = (process.env.ALLOWED_ORIGINS ?? "").split(",").map((s) => s.trim()).filter(Boolean);

if (getApps().length === 0 && process.env.FIREBASE_PROJECT_ID) {
  initializeApp({ credential: applicationDefault(), projectId: process.env.FIREBASE_PROJECT_ID });
}
app.disable("x-powered-by");
app.use(express.json({ limit: "16kb" }));
app.use(cors({
  origin(origin, callback) {
    if (!origin || allowedOrigins.includes(origin)) return callback(null, true);
    return callback(new Error("Origin not allowed"));
  },
  methods: ["GET", "POST"],
  allowedHeaders: ["Authorization", "Content-Type"]
}));

app.get("/health", (_req, res) => res.status(200).json({ status: "ok" }));
const tokenLimiter = rateLimit({ windowMs: 60_000, limit: 5, standardHeaders: "draft-7", legacyHeaders: false });

app.post("/v1/voice/session", tokenLimiter, async (req, res) => {
  const match = (req.header("authorization") ?? "").match(/^Bearer\s+(.+)$/i);
  if (!match) return res.status(401).json({ error: "Authentication required." });
  if (!process.env.FIREBASE_PROJECT_ID || getApps().length === 0) {
    return res.status(503).json({ error: "Authentication service is not configured." });
  }
  if (!process.env.OPENAI_API_KEY) return res.status(503).json({ error: "Voice service is not configured." });

  let uid: string;
  try {
    uid = (await getAuth().verifyIdToken(match[1], true)).uid;
  } catch {
    return res.status(401).json({ error: "Invalid or expired session." });
  }

  const session = {
    type: "realtime",
    model: "gpt-realtime",
    instructions: "You are AI Caller, a transparent AI assistant. Identify yourself as an AI assistant; never claim to be the user. Do not make commitments, purchases, appointments, or send messages without explicit confirmation. If an action is unsupported, say the feature is not connected. Speak naturally and concisely; understand Indian English, Hindi, and Hinglish when possible.",
    audio: {
      output: { voice: "marin" },
      input: {
        transcription: { model: "gpt-4o-mini-transcribe", language: "en" },
        turn_detection: { type: "semantic_vad", create_response: true, interrupt_response: true }
      }
    },
    max_output_tokens: 800
  };

  try {
    const response = await fetch("https://api.openai.com/v1/realtime/client_secrets", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${process.env.OPENAI_API_KEY}`,
        "Content-Type": "application/json",
        "OpenAI-Safety-Identifier": uid
      },
      body: JSON.stringify({ expires_after: { anchor: "created_at", seconds: 600 }, session }),
      signal: AbortSignal.timeout(12_000)
    });
    if (!response.ok) return res.status(502).json({ error: "Could not create a voice session." });
    const data = await response.json() as { value?: string; expires_at?: number; session?: { id?: string } };
    if (!data.value || !data.expires_at) return res.status(502).json({ error: "Voice provider returned an invalid session." });
    return res.status(200).json({ clientSecret: data.value, expiresAt: data.expires_at, sessionId: data.session?.id ?? null });
  } catch {
    return res.status(502).json({ error: "Voice service temporarily unavailable." });
  }
});

app.listen(port, () => console.log(`AI Caller session backend listening on port ${port}`));
