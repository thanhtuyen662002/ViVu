package com.vothanhtuyen.vivu_backend.sevices.ai;

import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.config.AIPromptConfig;
import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;

@Service
public class AIServiceImpl implements AIService {

    private final OpenAiService openAiService;
    private final String aiPrompt;

    public AIServiceImpl(OpenAiService openAiService, AIPromptConfig aiPromptConfig) {
        this.openAiService = openAiService;
        this.aiPrompt = aiPromptConfig.getAiPrompt();
    }

    @Override
    public String getAIResponse(GetDataRequestDTO request) {
        String location = "Tên địa điểm: " + request.getLocation();
        String budget = "Ngân sách: " + request.getBudget();
        String needs = "Nhu cầu: " + request.getNeeds();
        String details = "Yêu cầu chi tiết: " + request.getDetails();
        String finalRequest = location + ", " + budget + ", " + needs + ", " + details;

        // Gửi yêu cầu đến OpenAI API và nhận phản hồi
        String result = openAiService.callOpenAI(finalRequest, aiPrompt);

        // Trả về nội dung phản hồi
        return result;
    }
}
