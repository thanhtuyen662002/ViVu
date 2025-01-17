package com.vothanhtuyen.vivu_backend.sevices.place;

import java.util.List;

import org.json.JSONArray;

import com.vothanhtuyen.vivu_backend.dto.PlaceResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Places;

public interface PlaceService {
    List<Places> getAllPlacesByLocationId(Long locationID);
    List<PlaceResponseDTO> getAllPlacesDTOByLocationId(Long locationId);
    void savePlacesByJSONArray(JSONArray places);
    List<PlaceResponseDTO> convertPlacesByJSONArray(JSONArray places);
}
