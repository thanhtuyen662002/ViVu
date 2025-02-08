package com.vothanhtuyen.vivu_backend.sevices.eatery;

import java.util.List;

import org.json.JSONArray;

import com.vothanhtuyen.vivu_backend.dto.EateryResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Eatery;
import com.vothanhtuyen.vivu_backend.entities.Locations;

public interface EateryService {
    List<Eatery> getAllEaterysByLocationId(Long locationId);
    List<EateryResponseDTO> getAllEaterysDTOByLocationId(Long locationId);
    List<EateryResponseDTO> convertEaterysByJSONArray(JSONArray eaterys, Locations location);
}
