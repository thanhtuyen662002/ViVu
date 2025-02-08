package com.vothanhtuyen.vivu_backend.sevices.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestClientException;

@Service
public class OpenAiService {

    @Value("${spring.ai.openai.api-key}")
    private String OPENAI_API_KEY;

    @Value("${spring.ai.openai.chat.options.model}")
    private String OPENAI_API_MODEL;

    @Value("${openai.api.url}")
    private String OPENAI_API_URL;

    String callOpenAI(String userMessage, String systemPrompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Tạo JSON body theo định dạng của ChatCompletion
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", OPENAI_API_MODEL);

        // Định nghĩa cấu trúc messages
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject()
                .put("role", "system")
                .put("content", systemPrompt));
        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", userMessage));

        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 8000);
        requestBody.put("temperature", 0.7);

        // Tạo headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPENAI_API_KEY);

        // Tạo HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            // Gửi request đến OpenAI ChatCompletion API
            ResponseEntity<String> response = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Xử lý phản hồi
            JSONObject responseJson = new JSONObject(response.getBody());
            JSONArray choices = responseJson.getJSONArray("choices");
            
            // Trả về phần message của lựa chọn đầu tiên
            return choices.getJSONObject(0).getJSONObject("message").getString("content");

        } catch (JSONException | RestClientException e) {
            // Xử lý lỗi và trả về thông báo lỗi dưới dạng JSON
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Failed to call OpenAI API");
            errorResponse.put("details", e.getMessage());
            return errorResponse.toString();
        }
    }
}
