import { describe, expect, it } from "vitest";

describe("AI Caller voice backend policy", () => {
  it("keeps client-secret response fields explicit", () => {
    const response = { clientSecret: "ephemeral", expiresAt: 123, sessionId: "session" };
    expect(Object.keys(response)).toEqual(["clientSecret", "expiresAt", "sessionId"]);
    expect(JSON.stringify(response)).not.toContain("OPENAI_API_KEY");
  });

  it("uses transparent AI identity instructions", () => {
    const instructions = "Identify yourself as an AI assistant; never claim to be the user.";
    expect(instructions).toContain("AI assistant");
    expect(instructions).toContain("never claim to be the user");
  });
});
