package mage.player.ollama.util;

/**
 * Response object for OLLAMA decision responses
 */
public class OllamaDecisionResponse {
    private String actionId;
    private String source;
    private String reason;

    // Constructors
    public OllamaDecisionResponse() {}

    public OllamaDecisionResponse(String actionId, String source, String reason) {
        this.actionId = actionId;
        this.source = source;
        this.reason = reason;
    }

    // Getters and setters
    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
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
        return "OllamaDecisionResponse{" +
                "actionId='" + actionId + '\'' +
                ", source='" + source + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}