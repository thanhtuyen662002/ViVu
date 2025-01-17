package com.vothanhtuyen.vivu_backend.sevices.localFood;

import java.util.List;

import org.json.JSONArray;

import com.vothanhtuyen.vivu_backend.dto.LocalFoodResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.LocalFoods;

public interface LocalFoodService {
    List<LocalFoods> getAllLocalFoodsByLocationId(Long locationId);
    List<LocalFoodResponseDTO> getAllLocalFoodsDTOByLocationId(Long locationId);
    void saveLocalFoodByJSONArray(JSONArray localFoods);
    List<LocalFoodResponseDTO> convertLocalFoodsByJSONArray(JSONArray localFoods);
}
