package com.Hazavao.demo.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class HazavaoController {

    private static final String OPENAI_API_KEY = "sk-proj-TwUbI6Fdjpgh..."; // cle Tanjona

    @GetMapping("/hazavao")
    public String getDefinition(@RequestParam String teny) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String prompt = "Donne la définition du mot \"" + teny + "\" en malgache, en 1 phrase.";

        Request request = new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(okhttp3.RequestBody.create(
                String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}]}", prompt),
                okhttp3.MediaType.parse("application/json")
            ))
            .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string(); 
        }
    }
}