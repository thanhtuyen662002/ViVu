package com.vothanhtuyen.vivu_backend.controller;

import com.vothanhtuyen.vivu_backend.sevices.ai.AIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/get-response")
    public String getAIResponse(@RequestBody GetDataRequestDTO request) {
        return aiService.getAIResponse(request);
    }
}
