package com.vothanhtuyen.vivu_backend.sevices.location;

import java.util.List;

import org.json.JSONObject;

import com.vothanhtuyen.vivu_backend.dto.LocationResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Locations;

public interface LocationService {
    List<LocationResponseDTO> getAllLocations();
    List<String> getAllNameLocations();
    Locations getLocationByName(String name);
    LocationResponseDTO convertLocation (Locations location);
    public void saveLocationByJSONObject(JSONObject location);
    LocationResponseDTO convertLocationDTOByJSONObject(JSONObject location);
}
