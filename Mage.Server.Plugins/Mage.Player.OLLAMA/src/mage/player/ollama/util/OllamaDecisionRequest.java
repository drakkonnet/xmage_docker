package mage.player.ollama.util;

import java.util.List;
import java.util.Map;

/**
 * Request object for OLLAMA decision requests
 */
public class OllamaDecisionRequest {
    private Map<String, Object> game;
    private Map<String, Object> agent;
    private Map<String, Object> deckProfile;
    private Map<String, Object> decisionContext;
    private List<Map<String, Object>> players;
    private List<Map<String, Object>> stack;
    private List<Map<String, Object>> legalActions;

    // Constructors
    public OllamaDecisionRequest() {}

    public OllamaDecisionRequest(Map<String, Object> game, Map<String, Object> agent, 
                               Map<String, Object> deckProfile, Map<String, Object> decisionContext,
                               List<Map<String, Object>> players, List<Map<String, Object>> stack,
                               List<Map<String, Object>> legalActions) {
        this.game = game;
        this.agent = agent;
        this.deckProfile = deckProfile;
        this.decisionContext = decisionContext;
        this.players = players;
        this.stack = stack;
        this.legalActions = legalActions;
    }

    // Getters and setters
    public Map<String, Object> getGame() {
        return game;
    }

    public void setGame(Map<String, Object> game) {
        this.game = game;
    }

    public Map<String, Object> getAgent() {
        return agent;
    }

    public void setAgent(Map<String, Object> agent) {
        this.agent = agent;
    }

    public Map<String, Object> getDeckProfile() {
        return deckProfile;
    }

    public void setDeckProfile(Map<String, Object> deckProfile) {
        this.deckProfile = deckProfile;
    }

    public Map<String, Object> getDecisionContext() {
        return decisionContext;
    }

    public void setDecisionContext(Map<String, Object> decisionContext) {
        this.decisionContext = decisionContext;
    }

    public List<Map<String, Object>> getPlayers() {
        return players;
    }

    public void setPlayers(List<Map<String, Object>> players) {
        this.players = players;
    }

    public List<Map<String, Object>> getStack() {
        return stack;
    }

    public void setStack(List<Map<String, Object>> stack) {
        this.stack = stack;
    }

    public List<Map<String, Object>> getLegalActions() {
        return legalActions;
    }

    public void setLegalActions(List<Map<String, Object>> legalActions) {
        this.legalActions = legalActions;
    }
}