package com.vothanhtuyen.vivu_backend.sevices.ai;

import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    private final OpenAiService openAiService;

    public AIServiceImpl(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @Override
    public String getAIResponse(String locationName, String prompt) {
        String location = "Tên địa điểm chính: " + locationName;

        // Gửi yêu cầu đến OpenAI API và nhận phản hồi
        String result = openAiService.callOpenAI(location, prompt);

        // Trả về nội dung phản hồi
        return result;
    }
}
