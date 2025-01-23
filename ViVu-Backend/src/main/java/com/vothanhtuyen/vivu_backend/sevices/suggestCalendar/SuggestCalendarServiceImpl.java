package com.vothanhtuyen.vivu_backend.sevices.suggestCalendar;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.SuggestedCalendarResponseDTO;

@Service
public class SuggestCalendarServiceImpl implements SuggestCalendarService{
    @Override
    public SuggestedCalendarResponseDTO convertSuggestedCalendarByJSONObject(JSONObject suggestedCalendar) {
        try {
            String suggestVi = suggestedCalendar.getString("vi");
            String suggestEn = suggestedCalendar.getString("en");
            SuggestedCalendarResponseDTO suggestedCalendarResponseDTO = new SuggestedCalendarResponseDTO();
            suggestedCalendarResponseDTO.setSuggestVi(suggestVi);
            suggestedCalendarResponseDTO.setSuggestEn(suggestEn);
            return suggestedCalendarResponseDTO;
        } catch (Exception e) {
        }
        return null;
    }
}
