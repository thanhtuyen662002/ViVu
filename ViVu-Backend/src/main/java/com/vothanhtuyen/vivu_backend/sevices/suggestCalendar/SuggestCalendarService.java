package com.vothanhtuyen.vivu_backend.sevices.suggestCalendar;

import org.json.JSONObject;

import com.vothanhtuyen.vivu_backend.dto.SuggestedCalendarResponseDTO;

public interface SuggestCalendarService {
    SuggestedCalendarResponseDTO convertSuggestedCalendarByJSONObject(JSONObject suggestedCalendar);
}
