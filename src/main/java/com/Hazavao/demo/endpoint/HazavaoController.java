package com.Hazavao.demo.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class HazavaoController {

    @Value("${openai.api-key}") // cle tanjona yml
    private String openaiApiKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/hazavao")
    public String getDefinition(@RequestParam String teny) {
        String prompt = "Donne la définition du mot \"" + teny + "\" en malgache, en 1 phrase.";
        String requestBody = String.format(
            "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}]}",
            prompt
        );

        Request request = new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(RequestBody.create(
                requestBody,
                MediaType.parse("application/json")
            ))
            .addHeader("Authorization", "Bearer " + openaiApiKey)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Erreur OpenAI: " + response.code());
            }

            
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            return jsonNode.path("choices").get(0).path("message").path("content").asText();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'appel à l'API OpenAI", e);
        }
    }
}