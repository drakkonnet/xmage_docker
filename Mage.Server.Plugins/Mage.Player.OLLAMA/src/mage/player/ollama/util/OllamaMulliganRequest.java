package mage.player.ollama.util;

import java.util.List;
import java.util.Map;

/**
 * Request object for OLLAMA mulligan requests
 */
public class OllamaMulliganRequest {
    private Map<String, Object> agent;
    private List<String> hand;
    private int handSize;
    private int mulliganCount;

    // Constructors
    public OllamaMulliganRequest() {}

    public OllamaMulliganRequest(Map<String, Object> agent, List<String> hand, int handSize, int mulliganCount) {
        this.agent = agent;
        this.hand = hand;
        this.handSize = handSize;
        this.mulliganCount = mulliganCount;
    }

    // Getters and setters
    public Map<String, Object> getAgent() {
        return agent;
    }

    public void setAgent(Map<String, Object> agent) {
        this.agent = agent;
    }

    public List<String> getHand() {
        return hand;
    }

    public void setHand(List<String> hand) {
        this.hand = hand;
    }

    public int getHandSize() {
        return handSize;
    }

    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    public int getMulliganCount() {
        return mulliganCount;
    }

    public void setMulliganCount(int mulliganCount) {
        this.mulliganCount = mulliganCount;
    }
}