package mage.player.ollama.util;

/**
 * Response object for OLLAMA mulligan responses
 */
public class OllamaMulliganResponse {
    private String decision;
    private String source;
    private String reason;

    // Constructors
    public OllamaMulliganResponse() {}

    public OllamaMulliganResponse(String decision, String source, String reason) {
        this.decision = decision;
        this.source = source;
        this.reason = reason;
    }

    // Getters and setters
    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "OllamaMulliganResponse{" +
                "decision='" + decision + '\'' +
                ", source='" + source + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}