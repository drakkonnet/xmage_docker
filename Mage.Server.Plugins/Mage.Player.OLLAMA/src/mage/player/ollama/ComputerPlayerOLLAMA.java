package mage.player.ollama;

import mage.abilities.Ability;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.player.ai.ComputerPlayer6;
import mage.player.ollama.util.OllamaClient;
import mage.player.ollama.util.OllamaDecisionRequest;
import mage.player.ollama.util.OllamaDecisionResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * AI: server side bot with OLLAMA integration for decision making
 *
 * @author GitHub Copilot
 */
public class ComputerPlayerOLLAMA extends ComputerPlayer6 {

    private static final Logger logger = Logger.getLogger(ComputerPlayerOLLAMA.class);
    
    private OllamaClient ollamaClient;
    private String bridgeUrl;

    public ComputerPlayerOLLAMA(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
        // Initialize OLLAMA client with bridge URL from environment or config
        this.bridgeUrl = System.getenv("XMAGE_OLLAMA_BRIDGE_URL");
        if (this.bridgeUrl == null || this.bridgeUrl.isEmpty()) {
            this.bridgeUrl = "http://localhost:8787"; // Default URL
        }
        this.ollamaClient = new OllamaClient(this.bridgeUrl);
    }

    public ComputerPlayerOLLAMA(final ComputerPlayerOLLAMA player) {
        super(player);
        this.bridgeUrl = player.bridgeUrl;
        this.ollamaClient = player.ollamaClient;
    }

    @Override
    public ComputerPlayerOLLAMA copy() {
        return new ComputerPlayerOLLAMA(this);
    }

    @Override
    public boolean priority(Game game) {
        game.resumeTimer(getTurnControlledBy());
        boolean result = priorityPlay(game);
        game.pauseTimer(getTurnControlledBy());
        return result;
    }

    private boolean priorityPlay(Game game) {
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        switch (game.getTurnStepType()) {
            case UPKEEP:
            case DRAW:
                pass(game);
                return false;
            case PRECOMBAT_MAIN:
                printBattlefieldScore(game, "OLLAMA PRIORITY on MAIN 1");
                if (actions.isEmpty()) {
                    calculateActions(game);
                }
                act(game);
                return true;
            case BEGIN_COMBAT:
                pass(game);
                return false;
            case DECLARE_ATTACKERS:
                printBattlefieldScore(game, "OLLAMA PRIORITY on DECLARE ATTACKERS");
                if (actions.isEmpty()) {
                    calculateActions(game);
                }
                act(game);
                return true;
            case DECLARE_BLOCKERS:
                printBattlefieldScore(game, "OLLAMA PRIORITY on DECLARE BLOCKERS");
                if (actions.isEmpty()) {
                    calculateActions(game);
                }
                act(game);
                return true;
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
            case END_COMBAT:
                pass(game);
                return false;
            case POSTCOMBAT_MAIN:
                printBattlefieldScore(game, "OLLAMA PRIORITY on MAIN 2");
                if (actions.isEmpty()) {
                    calculateActions(game);
                }
                act(game);
                return true;
            case END_TURN:
            case CLEANUP:
                actionCache.clear();
                pass(game);
                return false;
        }
        return false;
    }

    @Override
    public boolean chooseMulligan(Game game) {
        logger.info("OLLAMA choosing mulligan for player: " + getName());
        
        try {
            // Create mulligan request
            OllamaMulliganRequest request = createMulliganRequest(game);
            
            // Get decision from OLLAMA bridge
            OllamaMulliganResponse response = ollamaClient.requestMulligan(request);
            
            // Process the decision
            if ("MULLIGAN".equals(response.getDecision())) {
                logger.info("OLLAMA decided to mulligan: " + response.getReason());
                return true;
            } else {
                logger.info("OLLAMA decided to keep: " + response.getReason());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Error getting mulligan decision from OLLAMA: " + e.getMessage(), e);
            // Fall back to default mulligan logic
            return super.chooseMulligan(game);
        }
    }

    protected void calculateActions(Game game) {
        if (!getNextAction(game)) {
            try {
                // Create decision request
                OllamaDecisionRequest request = createDecisionRequest(game);
                
                // Get decision from OLLAMA bridge
                OllamaDecisionResponse response = ollamaClient.requestDecision(request);
                
                // Process the decision and populate actions
                processOLLAMADecision(response, game);
            } catch (IOException | InterruptedException e) {
                logger.error("Error getting decision from OLLAMA: " + e.getMessage(), e);
                // Fall back to default action selection
                super.calculateActions(game);
            }
        } else {
            logger.debug("Next Action exists!");
        }
    }

    /**
     * Create a decision request for the OLLAMA bridge
     */
    private OllamaDecisionRequest createDecisionRequest(Game game) {
        // TODO: Implement conversion from XMAGE game state to OLLAMA request format
        // This would extract the game state and convert it to the format expected by the bridge
        
        OllamaDecisionRequest request = new OllamaDecisionRequest();
        // Populate request with game state information
        // This is a placeholder implementation
        
        return request;
    }

    /**
     * Create a mulligan request for the OLLAMA bridge
     */
    private OllamaMulliganRequest createMulliganRequest(Game game) {
        // TODO: Implement conversion from XMAGE hand state to OLLAMA mulligan request format
        // This would extract the hand information and convert it to the format expected by the bridge
        
        OllamaMulliganRequest request = new OllamaMulliganRequest();
        // Populate request with hand information
        // This is a placeholder implementation
        
        return request;
    }

    /**
     * Process the decision received from OLLAMA and populate actions
     */
    private void processOLLAMADecision(OllamaDecisionResponse response, Game game) {
        // TODO: Implement decision processing logic
        // Parse the decision and convert it to XMAGE actions
        actions = new LinkedList<>();
        
        if (response.getActionId() != null) {
            logger.info("OLLAMA decision: " + response.getActionId() + " - " + response.getReason());
            // Find the corresponding ability/action in the game and add it to actions
        }
    }
}