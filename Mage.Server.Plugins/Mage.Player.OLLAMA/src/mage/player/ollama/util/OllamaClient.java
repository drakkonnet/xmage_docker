package mage.player.ollama.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mage.game.Game;
import mage.player.ollama.ComputerPlayerOLLAMA;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Client for communicating with the OLLAMA bridge service
 */
public class OllamaClient {
    private static final Logger logger = Logger.getLogger(OllamaClient.class);

    private final HttpClient httpClient;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public OllamaClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    /**
     * Request a decision from the OLLAMA service
     */
    public OllamaDecisionResponse requestDecision(OllamaDecisionRequest request) throws IOException, InterruptedException {
        String endpoint = baseUrl + "/v1/decide";
        
        // Convert request to JSON
        String requestBody = objectMapper.writeValueAsString(request);
        
        // Send request
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
                
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String actionId = jsonResponse.has("actionId") ? jsonResponse.get("actionId").asText() : null;
            String source = jsonResponse.has("source") ? jsonResponse.get("source").asText() : null;
            String reason = jsonResponse.has("reason") ? jsonResponse.get("reason").asText() : null;
            
            return new OllamaDecisionResponse(actionId, source, reason);
        } else {
            logger.error("OLLAMA request failed with status: " + response.statusCode() + ", body: " + response.body());
            throw new IOException("OLLAMA request failed with status: " + response.statusCode());
        }
    }
    
    /**
     * Request a mulligan decision from the OLLAMA service
     */
    public OllamaMulliganResponse requestMulligan(OllamaMulliganRequest request) throws IOException, InterruptedException {
        String endpoint = baseUrl + "/v1/mulligan";
        
        // Convert request to JSON
        String requestBody = objectMapper.writeValueAsString(request);
        
        // Send request
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
                
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String decision = jsonResponse.has("decision") ? jsonResponse.get("decision").asText() : null;
            String source = jsonResponse.has("source") ? jsonResponse.get("source").asText() : null;
            String reason = jsonResponse.has("reason") ? jsonResponse.get("reason").asText() : null;
            
            return new OllamaMulliganResponse(decision, source, reason);
        } else {
            logger.error("OLLAMA mulligan request failed with status: " + response.statusCode() + ", body: " + response.body());
            throw new IOException("OLLAMA mulligan request failed with status: " + response.statusCode());
        }
    }
}