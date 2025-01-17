package com.vothanhtuyen.vivu_backend.sevices.hotel;

import java.util.List;

import org.json.JSONArray;

import com.vothanhtuyen.vivu_backend.dto.HotelResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Hotels;

public interface HotelService {
    List<Hotels> getAllHotelsByLocationId(Long locationId);
    List<HotelResponseDTO> getAllHotelsDTOByLocationId(Long locationId);
    List<HotelResponseDTO> saveHotelsByJSONArray(JSONArray hotels);
    List<HotelResponseDTO> convertHotelsByJSONArray(JSONArray hotels);
}
