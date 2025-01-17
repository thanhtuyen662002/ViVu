package com.vothanhtuyen.vivu_backend.sevices.ai;

import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;

public interface AIService {
    String getAIResponse(GetDataRequestDTO request);
}
