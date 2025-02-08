package com.vothanhtuyen.vivu_backend.sevices.hotel;

import java.util.List;

import org.json.JSONArray;

import com.vothanhtuyen.vivu_backend.dto.HotelResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Hotels;
import com.vothanhtuyen.vivu_backend.entities.Locations;

public interface HotelService {
    List<Hotels> getAllHotelsByLocationId(Long locationId);
    List<HotelResponseDTO> getAllHotelsDTOByLocationId(Long locationId);
    List<HotelResponseDTO> convertHotelsByJSONArray(JSONArray hotels, Locations location);
}
